package feign;

import static feign.ExceptionPropagationPolicy.UNWRAP;
import static feign.FeignException.errorExecuting;
import static feign.Util.checkNotNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import feign.codec.Decoder;
import feign.codec.ErrorDecoder;

/**
 * @author fangjiaxiaobai
 * @date 2021-02-20 11:05
 * @since 1.0.0
 */
public final class MockerSynchronousMethodHandler implements InvocationHandlerFactory.MethodHandler {

    private static final long MAX_RESPONSE_BUFFER_SIZE = 8192L;

    private final MethodMetadata metadata;

    private final Target<?> target;

    private final Client client;

    private final Retryer retryer;

    private final List<RequestInterceptor> requestInterceptors;

    private final Logger logger;

    private final Logger.Level logLevel;

    private final RequestTemplate.Factory buildTemplateFromArgs;

    private final Request.Options options;

    private final ExceptionPropagationPolicy propagationPolicy;

    // only one of decoder and asyncResponseHandler will be non-null
    private final Decoder decoder;

    private final AsyncResponseHandler asyncResponseHandler;


    private MockerSynchronousMethodHandler(Target<?> target, Client client, Retryer retryer,
            List<RequestInterceptor> requestInterceptors, Logger logger, Logger.Level logLevel, MethodMetadata metadata,
            RequestTemplate.Factory buildTemplateFromArgs, Request.Options options, Decoder decoder,
            ErrorDecoder errorDecoder, boolean decode404, boolean closeAfterDecode,
            ExceptionPropagationPolicy propagationPolicy, boolean forceDecoding) {

        this.target = checkNotNull(target, "target");
        this.client = checkNotNull(client, "client for %s", target);
        this.retryer = checkNotNull(retryer, "retryer for %s", target);
        this.requestInterceptors = checkNotNull(requestInterceptors, "requestInterceptors for %s", target);
        this.logger = checkNotNull(logger, "logger for %s", target);
        this.logLevel = checkNotNull(logLevel, "logLevel for %s", target);
        this.metadata = checkNotNull(metadata, "metadata for %s", target);
        this.buildTemplateFromArgs = checkNotNull(buildTemplateFromArgs, "metadata for %s", target);
        this.options = checkNotNull(options, "options for %s", target);
        this.propagationPolicy = propagationPolicy;

        if (forceDecoding) {
            // internal only: usual handling will be short-circuited, and all responses will be passed to
            // decoder directly!
            this.decoder = decoder;
            this.asyncResponseHandler = null;
        } else {
            this.decoder = null;
            this.asyncResponseHandler = new AsyncResponseHandler(logLevel, logger, decoder, errorDecoder, decode404,
                                                                 closeAfterDecode);
        }
    }

    @Override
    public Object invoke(Object[] argv) throws Throwable {
        // 解析返回参数
        return null;
    }

    Object executeAndDecode(RequestTemplate template, Request.Options options) throws Throwable {
        Request request = targetRequest(template);

        if (logLevel != Logger.Level.NONE) {
            logger.logRequest(metadata.configKey(), logLevel, request);
        }

        Response response;
        long start = System.nanoTime();
        try {
            response = client.execute(request, options);
            // ensure the request is set. TODO: remove in Feign 12
            response = response.toBuilder().request(request).requestTemplate(template).build();
        } catch (IOException e) {
            if (logLevel != Logger.Level.NONE) {
                logger.logIOException(metadata.configKey(), logLevel, e, elapsedTime(start));
            }
            throw errorExecuting(request, e);
        }
        long elapsedTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);


        if (decoder != null) {
            return decoder.decode(response, metadata.returnType());
        }

        CompletableFuture<Object> resultFuture = new CompletableFuture<>();
        asyncResponseHandler.handleResponse(resultFuture, metadata.configKey(), response, metadata.returnType(),
                                            elapsedTime);

        try {
            if (!resultFuture.isDone()) {
                throw new IllegalStateException("Response handling not done");
            }

            return resultFuture.join();
        } catch (CompletionException e) {
            Throwable cause = e.getCause();
            if (cause != null) {
                throw cause;
            }
            throw e;
        }
    }

    long elapsedTime(long start) {
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
    }

    Request targetRequest(RequestTemplate template) {
        for (RequestInterceptor interceptor : requestInterceptors) {
            interceptor.apply(template);
        }
        return target.apply(template);
    }

    Request.Options findOptions(Object[] argv) {
        if (argv == null || argv.length == 0) {
            return this.options;
        }
        return Stream.of(argv).filter(Request.Options.class::isInstance).map(
                Request.Options.class::cast).findFirst().orElse(this.options);
    }

    static class Factory {

        private final Client client;

        private final Retryer retryer;

        private final List<RequestInterceptor> requestInterceptors;

        private final Logger logger;

        private final Logger.Level logLevel;

        private final boolean decode404;

        private final boolean closeAfterDecode;

        private final ExceptionPropagationPolicy propagationPolicy;

        private final boolean forceDecoding;

        Factory(Client client, Retryer retryer, List<RequestInterceptor> requestInterceptors, Logger logger,
                Logger.Level logLevel, boolean decode404, boolean closeAfterDecode,
                ExceptionPropagationPolicy propagationPolicy, boolean forceDecoding) {
            this.client = checkNotNull(client, "client");
            this.retryer = checkNotNull(retryer, "retryer");
            this.requestInterceptors = checkNotNull(requestInterceptors, "requestInterceptors");
            this.logger = checkNotNull(logger, "logger");
            this.logLevel = checkNotNull(logLevel, "logLevel");
            this.decode404 = decode404;
            this.closeAfterDecode = closeAfterDecode;
            this.propagationPolicy = propagationPolicy;
            this.forceDecoding = forceDecoding;
        }

        public InvocationHandlerFactory.MethodHandler create(Target<?> target, MethodMetadata md,
                RequestTemplate.Factory buildTemplateFromArgs, Request.Options options, Decoder decoder,
                ErrorDecoder errorDecoder) {
            return new MockerSynchronousMethodHandler(target, client, retryer, requestInterceptors, logger, logLevel,
                                                      md, buildTemplateFromArgs, options, decoder, errorDecoder,
                                                      decode404, closeAfterDecode, propagationPolicy, forceDecoding);
        }
    }
}
