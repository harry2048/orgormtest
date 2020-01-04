package org.ormtest.step020.entity;

import javassist.*;

import java.lang.reflect.Field;
import java.sql.ResultSet;

/**
 * @Auther: gengwei
 * @Date: 2019-12-22 14:54
 * @Description:
 */
public final class EntityHelperFactory {
    /**
     * 私有化类默认构造器
     */
    private EntityHelperFactory (){}

    /**
     * 获取实体助手
     * @param entityClazz
     * @return
     */
    public static AbstractEntityHelper getEntityHelper(Class<?> entityClazz) throws Exception {
        if (null == entityClazz) {
            return null;
        }

        ClassPool pool = ClassPool.getDefault();
        pool.appendSystemPath();

        pool.importPackage(ResultSet.class.getName());
        pool.importPackage(AbstractEntityHelper.class.getName());

        // 拿到 AbstractEntityHelper
        CtClass clazzHtlper = pool.getCtClass(AbstractEntityHelper.class.getName());
        // 要创建的助手类名称
        String helperClazzName = entityClazz.getName() + "_Helper";

        // 创建XxxEntity_Helper extents AbstractEntiryHelper
        CtClass cc = pool.makeClass(helperClazzName, clazzHtlper);

        // 默认构造器
        // 生成如下代码：public XxxEntity_Helper() {}
        CtConstructor ctConstructor = new CtConstructor(new CtClass[0], cc);
        ctConstructor.setBody("{}");

        cc.addConstructor(ctConstructor);

        StringBuffer sb = new StringBuffer();

        sb.append("public Object create(java.sql.ResultSet rs) throws Exception {\n");
        sb.append(entityClazz.getName())
          .append(" obj = new ")
          .append(entityClazz.getName())
          .append("(); \n");

        Field[] fieldArray = entityClazz.getFields();
        for (Field field : fieldArray) {
            Column annoColumn = field.getAnnotation(Column.class);
            if (null == annoColumn) {
                continue;
            }

            String columnName = annoColumn.name();
            if (field.getType() == Integer.TYPE) {
                sb.append("obj.")
                  .append(field.getName())
                  .append(" = rs.getInt(\"")
                  .append(columnName)
                  .append("\");\n");
            } else if (field.getType().equals(String.class)) {
                sb.append("obj.")
                  .append(field.getName())
                  .append(" = rs.getString(\"")
                  .append(columnName)
                  .append("\");\n");
            } else {
                // 不支持的类型
            }
        }


        sb.append("return obj;")
          .append("}");

        // 创建方法
        CtMethod cm = CtNewMethod.make(sb.toString(), cc);
        // 将方法添加到类中
        cc.addMethod(cm);

        // cc.writeFile("D:/Data/debug-java");

        Class javaClazz = cc.toClass();

        // 创建对象实例
        Object helperImpl = javaClazz.newInstance();
        return (AbstractEntityHelper) helperImpl;
    }
}
