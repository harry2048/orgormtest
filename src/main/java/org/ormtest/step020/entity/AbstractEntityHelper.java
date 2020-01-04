package org.ormtest.step020.entity;

import java.sql.ResultSet;

/**
 * @Auther: gengwei
 * @Date: 2019-12-22 14:52
 * @Description:抽象的实体助手
 */
public abstract class AbstractEntityHelper {
    /**
     * 创建实体
     * @param resultSet
     * @return
     * @throws Exception
     */
    public abstract Object create(ResultSet resultSet) throws Exception;
}
