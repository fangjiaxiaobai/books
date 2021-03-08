package com.fxb.simpleapp;

import java.util.ArrayList;
import java.util.List;

import com.fxb.mocker.utils.bean.NewBeanUtil;

/**
 * 测试 NewBeanUtil 工具类
 *
 * @author fangjiaxiaobai
 * @date 2021-02-20 14:09
 * @since 1.0.0
 */
public class TestNewBeanUtil {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
//
//       testSimpleOb();
//
//       testArrayOb();

       testListOb();
    }

    private static void testListOb() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        List<String> newList = new ArrayList<String>();
        List<String> simpleObject = NewBeanUtil.newInstance(newList.getClass());
        System.out.println(simpleObject);
//        simpleObject = NewBeanUtil.newInstance(List.class);
//        System.out.println(simpleObject);
    }

    private static void testSimpleOb() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        SimpleObject simpleObject = NewBeanUtil.newInstance(SimpleObject.class);
        System.out.println(simpleObject);
    }

    private static void testArrayOb() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        String[] strings = NewBeanUtil.newInstance(String[].class);
        for (int i = 0; i < strings.length; i++) {
            System.out.print(strings[i]+",");
        }
        System.out.println();
    }

}
