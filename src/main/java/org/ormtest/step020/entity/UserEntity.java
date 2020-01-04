package org.ormtest.step020.entity;

/**
 * @Auther: gengwei
 * @Date: 2019-12-22 12:44
 * @Description: 
 */
public class UserEntity {
    public static void main(String[] args) {
        // 非io密集时的线程池的数量
        int i = Runtime.getRuntime().availableProcessors();

        // io密集时线程池的数量为 2N
        System.out.println(i);
    }

    /**
     * 用户 Id
     */
    @Column(name = "user_id")
    public int _userId;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    public String _userName;

    /**
     * 密码
     */
    @Column(name = "password")
    public String _password;
}
