package org.ormtest.step020.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: gengwei
 * @Date: 2019-12-22 13:42
 * @Description: 数据表列注解
 */
@Target(ElementType.FIELD) // 作用在哪里 作用在字段上
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    /**
     * 列名称
     * @return
     */
    String name();
}
