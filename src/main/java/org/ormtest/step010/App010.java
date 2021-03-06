package org.ormtest.step010;

import org.ormtest.step010.entity.UserEntity;
import org.ormtest.step010.entity.XxxEntity_Helper;

import java.sql.*;

/**
 * @Auther: gengwei
 * @Date: 2019-12-22 12:43
 * @Description:
 */
public class App010 {
    /**
     * 应用程序主函数
     *
     * @param argvArray 参数数组
     * @throws Exception
     */
    static public void main(String[] argvArray) throws Exception {
        (new App010()).start();
    }

    public static void main2(String[] args) throws Exception{
        // 加载 Mysql 驱动
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        // 数据库连接地址
        String dbConnStr = "jdbc:mysql://localhost:3306/ormtest?user=root&password=root&serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true";
        // 创建数据库连接
        Connection conn = DriverManager.getConnection(dbConnStr);
        conn.setAutoCommit(false);
        // 简历陈述对象
        String sql = "insert into t_user values(?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int x = 0; x < 200000; x++) {
            ps.setInt(1, x);
            ps.setString(2, "张三" + x);
            ps.setString(3, "password" + x);
            ps.addBatch();
        }
        int[] ints = ps.executeBatch();
        conn.commit();
        System.out.println("插入条数：" + ints.length);
        ps.close();
        conn.close();
    }

    /**
     * 测试开始
     */
    private void start() throws Exception {
        // 加载 Mysql 驱动
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        // 数据库连接地址
        String dbConnStr = "jdbc:mysql://localhost:3306/ormtest?user=root&password=root&serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true";
        // 创建数据库连接
        Connection conn = DriverManager.getConnection(dbConnStr);
        // 简历陈述对象
        Statement stmt = conn.createStatement();

        // 创建 SQL 查询
        // ormtest 数据库中有个 t_user 数据表,
        // t_user 数据表包括三个字段: user_id、user_name、password,
        // t_user 数据表有 20 万条数据
        String sql = "select * from t_user limit 200000";

        // 执行查询
        ResultSet rs = stmt.executeQuery(sql);
        XxxEntity_Helper helper = new XxxEntity_Helper();

        // 获取开始时间
        long t0 = System.currentTimeMillis();

        while (rs.next()) {
            // 创建新的实体对象
            UserEntity ue = helper.create(UserEntity.class, rs);
        }

        // 获取结束时间
        long t1 = System.currentTimeMillis();

        // 关闭数据库连接
        stmt.close();
        conn.close();

        // 打印实例化花费时间
        System.out.println("实例化花费时间 = " + (t1 - t0) + "ms");
    }
}
