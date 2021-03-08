package com.fxb.mocker.utils.model;

import lombok.Data;

/**
 * @author fangjiaxiaobai
 * @date 2021-02-26 16:52
 * @since 1.0.0
 */
@Data
public class ClassFileModel {

    /**
     * 集合类型
     */
    private String collectType;

    /**
     * 泛型类型
     */
    private Class<?> genericType;

}
