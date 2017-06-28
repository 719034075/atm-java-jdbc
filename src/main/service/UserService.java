package main.service;

/**
 * Created by 71903 on 2017/5/15.
 */
public interface UserService {

    /**
     * Author:czy
     * function:注册
     * @return 0-注册成功，1-用户名已被注册，2-身份证已被注册，3-注册异常，4-密码加密失败
     */
    int signUp();

    /**
     * Author:czy
     * function:登录
     * @return 0-登录成功，1-用户名或密码错误，2-密码加密失败，3-查询卡异常
     */
    int signIn();

    /**
     * Author:czy
     * function:绑定卡
     * @return 0-绑定成功，1-输入手机号与该卡预留手机号不同,2-输入身份证号与该卡预留身份证号不同，3-绑定异常，4-该卡已被绑定，5-查询异常
     */
    int bindCard();

    /**
     * Author:czy
     * function:查询卡
     * @return 0-查询成功，1-查询异常
     */
    int userCards();

    /**
     * Author:czy
     * function:查询用户信息
     * @return 0-查询成功，1-省市区信息获取失败
     */
    int userInfo();

    /**
     * Author:czy
     * function:修改用户信息
     *  @return 0-修改成功，1-修改失败，2-查无此人，3-查询失败，4-账号异常
     */
    int modifyInfo();

    /**
     * Author:czy
     * function:修改密码
     * @return 0-密码修改成功，1-密码加密失败，2-旧密码输入错误，3-密码修改失败
     */
    int modifyPassword();

    /**
     * Author:czy
     * function:查询交易记录
     * @return 0-查询成功，1-查询异常，2-该账号没有绑定的卡
     */
    int transactionRecord();

    /**
     * Author:czy
     * function:取款
     * @return 0-取款成功，1-卡查询失败，2-余额不足，3-取款异常，4-该账号没有绑定的卡;
     */
    int withdraw();

    /**
     * Author:czy
     * function:存款
     * @return 0-存款成功，1-卡查询失败，2-存款异常，3-该帐号没有绑定的卡
     */
    int deposit();

    /**
     * Author:czy
     * function:转账
     * @return 0-转账成功，1-查询失败，2-转账的卡不存在，3-该卡主已被注销，4-余额不足，5-转账异常，6-该账号没有绑定的卡
     */
    int transfer();

    /**
     * Author:czy
     * function:注销
     * @return 0-注销成功，1-查询失败，2-卡内还有余额，3-注销失败
     */
    int cancellation();

    void exit();
}
