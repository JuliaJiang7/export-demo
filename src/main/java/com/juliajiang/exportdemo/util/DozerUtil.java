package com.juliajiang.exportdemo.util;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangfan.julia@gmail.com
 * @description
 * @since 2021/2/18 3:44 下午
 */
public class DozerUtil {
    public static <T, S> List<T> mapList(final Mapper mapper, List<S> sourceList, Class<T> targetObjectClass) {
        List<T> targetList = new ArrayList<T>();
        for (S s : sourceList) {
            targetList.add(mapper.map(s, targetObjectClass));
        }

        return targetList;
    }

    public static <T, S> List<T> mapList(List<S> sourceList, Class<T> targetObjectClass) {
        Mapper mapper = DozerBeanMapperBuilder.create().build();
        List<T> targetList = new ArrayList<T>();
        for (S s : sourceList) {
            targetList.add(mapper.map(s, targetObjectClass));
        }

        return targetList;
    }

    public static <T, S> T map(S sourceObj, Class<T> targetObjectClass) {
        Mapper mapper = DozerBeanMapperBuilder.create().build();
        return mapper.map(sourceObj, targetObjectClass);
    }
}
