package org.ormtest.step010.entity;

import java.lang.reflect.Field;
import java.sql.ResultSet;

/**
 * @Auther: gengwei
 * @Date: 2019-12-22 13:35
 * @Description:
 */
public class XxxEntity_Helper {
    public <TEntity> TEntity create(Class<TEntity> entityClazz, ResultSet rs) throws Exception{
//        UserEntity ue = new UserEntity();
//        ue._userId = rs.getInt("user_id");
//        ue._userName = rs.getString("user_name");
//        ue._password = rs.getString("password");
//        return ue;
        if (null == rs) {
            return null;
        }

        Object newEntity = entityClazz.newInstance();
        // 获取实体类的所有字段
        Field[] fieldArray = newEntity.getClass().getFields();

        for (Field field : fieldArray) {
            // 获取自定义注解
            Column annoColumn = field.getAnnotation(Column.class);

            if (null == annoColumn) {
                continue;
            }

            // 获取数据表字段名称
            String columnName = annoColumn.name();
            // 获取数据表中的值
            Object columnValue = rs.getObject(columnName);

            if (null == columnValue) {
                continue;
            }
            // 将值赋值给字段
            field.set(newEntity, columnValue);
        }
        return (TEntity) newEntity;
    }
}
