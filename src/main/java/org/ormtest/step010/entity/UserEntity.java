package org.ormtest.step010.entity;

/**
 * @Auther: gengwei
 * @Date: 2019-12-22 12:44
 * @Description: 
 */
public class UserEntity {
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
