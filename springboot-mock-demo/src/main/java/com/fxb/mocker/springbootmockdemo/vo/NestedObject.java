package com.fxb.mocker.springbootmockdemo.vo;

import java.util.List;
import java.util.Set;

import lombok.Data;

/**
 * @author fangjiaxiaobai
 * @date 2021-02-26 18:04
 * @since 1.0.0
 */
@Data
public class NestedObject {

    String testNestObject;

    int age;

    SimpleObject object1;

    SimpleObject object2;

    List<SimpleObject> simpleObjectList;

    Set<SimpleObject> simpleObjectSet;

//    NestedObject nestedObject2;

}
