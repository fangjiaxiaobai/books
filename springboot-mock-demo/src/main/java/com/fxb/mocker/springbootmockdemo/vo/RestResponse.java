package com.fxb.mocker.springbootmockdemo.vo;

import java.io.Serializable;

import com.fxb.mocker.springbootmockdemo.vo.speficed.Pager;

import lombok.Data;

/**
 * @author fangjiaxiaobai
 * @date 2021-02-26 19:24
 * @since 1.0.0
 */
@Data
public class RestResponse<T> implements Serializable {

    private static final long serialVersionUID = 605544666575940834L;
    public static  int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final RestResponse<Object> RES_SUCCESS = success((Object)null);
    private  int code;
    private  T data;
    private  String msg;

    private RestResponse(int code, T result, String msg, Pager pager) {
        this.code = code;
        this.data = result;
        this.msg = msg;
    }

    private RestResponse() {
    }


    public static <T> RestResponse<T> of(int code, T result,String msg) {
        return new RestResponse(code, result, msg, Pager.defaultPager());
    }
    public static <T> RestResponse<T> success(T result) {
        return new RestResponse(0, result, (String)null, Pager.defaultPager());
    }

}
