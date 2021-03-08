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
public class FinalFieldObject<T> implements Serializable {

    private static final long serialVersionUID = 605544666575940834L;
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final FinalFieldObject<Object> RES_SUCCESS = success((Object)null);
    private final int code;
    private final T data;
    private final String msg;

    private FinalFieldObject(int code, T result, String msg, Pager pager) {
        this.code = code;
        this.data = result;
        this.msg = msg;
    }


    public static <T> FinalFieldObject<T> of(int code, T result,String msg) {
        return new FinalFieldObject(code, result, msg,Pager.defaultPager());
    }
    public static <T> FinalFieldObject<T> success(T result) {
        return new FinalFieldObject(0, result, (String)null,Pager.defaultPager());
    }

}
