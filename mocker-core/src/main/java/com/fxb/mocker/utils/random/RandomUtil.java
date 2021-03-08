package com.fxb.mocker.utils.random;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.apache.commons.lang3.RandomUtils;

import com.apifan.common.random.source.DateTimeSource;
import com.apifan.common.random.source.NumberSource;
import com.apifan.common.random.source.OtherSource;

/**
 * 随机 工具类
 *
 * @author fangjiaxiaobai
 * @date 2021-02-20 12:21
 * @since 1.0.0
 */
public class RandomUtil {

    /**
     * 生成随机字符串
     *
     * @return Str
     */
    public static String randomString() {
        int i = NumberSource.getInstance().randomInt(1, 20);
        return OtherSource.getInstance().randomChinese(i);
    }

    /**
     * 生成1-100之间的数字
     *
     * @return 数字
     */
    public static int randomInt() {
        return NumberSource.getInstance().randomInt(1, 100);
    }

    /**
     * 生成1-100之间的数字
     *
     * @return 数字
     */
    public static int randomIntInTen() {
        return NumberSource.getInstance().randomInt(1, 10);
    }

    /**
     * 生成1-3之间的数字
     *
     * @return 数字
     */
    public static int randomIntInThree() {
        return NumberSource.getInstance().randomInt(1, 3);
    }

    public static double randomDouble() {
        return NumberSource.getInstance().randomDouble(1D, 100D);
    }

    public static long randomLong() {
        return NumberSource.getInstance().randomLong(1L, 10000000000L);
    }

    public static float randomFloat() {
        return (float) NumberSource.getInstance().randomDouble(1D, 100D);
    }

    public static byte randomByte() {
        return RandomUtils.nextBytes(1)[0];
    }

    public static short randomShort() {
        return 0;
    }


    public static boolean randomBoolean() {
        return RandomUtils.nextBoolean();
    }

    public static char randomCharacter() {
        return (char) RandomUtils.nextInt(65, 105);
    }

    public static Date randomDate() {
        long l = DateTimeSource.getInstance().randomTimestamp(LocalDate.now());
        return new Date(l);
    }


    public static Date random() {
        long l = DateTimeSource.getInstance().randomTimestamp(LocalDate.now());
        return new Date(l);
    }

    public static LocalDate randomLocalDate() {
        LocalDateTime dateTime = DateTimeSource.getInstance().randomFutureTime(1000);
        return dateTime.toLocalDate();
    }

    public static LocalDateTime randomLocalDateTime() {
        return DateTimeSource.getInstance().randomFutureTime(1000);
    }
}
