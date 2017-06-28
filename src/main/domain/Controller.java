package main.domain;

import main.service.UserService;
import main.service.UserServiceImpl;

import java.util.Scanner;

import static main.service.UserServiceImpl.currentUser;
import static main.utils.utils.cleanScreen;

/**
 * Created by 71903 on 2017/5/22.
 */
public class Controller {
    private int code;
    private int choice=0;
    private UserService userService=new UserServiceImpl();
    public void loginPage(){
        System.out.println("****************************************************");
        System.out.println("*                                                  *");
        System.out.println("*                                                  *");
        System.out.println("*                 银行ATM管理系统                  *");
        System.out.println("*                                                  *");
        System.out.println("*              1. 已有账户，立即登录               *");
        System.out.println("*                                                  *");
        System.out.println("*              2. 没有账户，马上注册               *");
        System.out.println("*                                                  *");
        System.out.println("*              3. 退出系统                         *");
        System.out.println("*                                                  *");
        System.out.println("*                                                  *");
        System.out.println("*                                                  *");
        System.out.println("****************************************************");
        Scanner scanner=new Scanner(System.in);
        do{
            System.out.print("选择：");
            choice=scanner.nextInt();
        }while (choice<1||choice>3);
        switch (choice){
            case 1:signInController();break;
            case 2:signUpController();break;
            case 3:System.exit(1);break;
            default:System.out.println("错误操作");break;
        }
    }
    public void okuserPage(){
        System.out.println("****************************************************");
        System.out.println("*                                                  *");
        System.out.println("*                                                  *");
        System.out.println("*                 银行ATM管理系统                  *");
        System.out.println("*                                                  *");
        System.out.println("*        1.  绑定银行卡      2.  查看银行卡        *");
        System.out.println("*        3.  查询用户信息    4.  修改用户信息      *");
        System.out.println("*        5.  修改密码        6.  查询交易记录      *");
        System.out.println("*        7.  取款            8.  存款              *");
        System.out.println("*        9.  转账            10. 注销账号          *");
        System.out.println("*        11. 登出                                  *");
        System.out.println("*                                                  *");
        System.out.println("*                                                  *");
        System.out.println("****************************************************");
        Scanner scanner=new Scanner(System.in);
        do{
            System.out.print("选择：");
            choice=scanner.nextInt();
        }while (choice<1||choice>11);
        switch (choice){
            case 1:bindCardController();break;
            case 2:userCardsController();break;
            case 3:userInfoController();break;
            case 4:modifyInfoController();break;
            case 5:modifyPasswordController();break;
            case 6:transactionRecordController();break;
            case 7:withdrawController();break;
            case 8:depositController();break;
            case 9:transferController();break;
            case 10:cancellationController();break;
            case 11:exitController();loginPage();break;
            default:System.out.println("错误操作");break;
        }
    }
    public void canceluserPage(){
        System.out.println("****************************************************");
        System.out.println("*                                                  *");
        System.out.println("*                                                  *");
        System.out.println("*                 银行ATM管理系统                  *");
        System.out.println("*                                                  *");
        System.out.println("*        1.  绑定银行卡      2.  查看银行卡        *");
        System.out.println("*        3.  查询用户信息    4.  修改用户信息      *");
        System.out.println("*        5.  修改密码        6.  查询交易记录      *");
        System.out.println("*        7.  登出                                  *");
        System.out.println("*                                                  *");
        System.out.println("*                                                  *");
        System.out.println("*                                                  *");
        System.out.println("*                                                  *");
        System.out.println("****************************************************");
        Scanner scanner=new Scanner(System.in);
        do{
            System.out.print("选择：");
            choice=scanner.nextInt();
        }while (choice<1||choice>7);
        switch (choice){
            case 1:bindCardController();break;
            case 2:userCardsController();break;
            case 3:userInfoController();break;
            case 4:modifyInfoController();break;
            case 5:modifyPasswordController();break;
            case 6:transactionRecordController();break;
            case 7:exitController();loginPage();break;
            default:System.out.println("错误操作");break;
        }
    }
    public void adminPage(){
        System.out.println("****************************************************");
        System.out.println("*                                                  *");
        System.out.println("*                                                  *");
        System.out.println("*                 银行ATM管理系统                  *");
        System.out.println("*                                                  *");
        System.out.println("*        1.  修改用户信息    2.  登出              *");
        System.out.println("*                                                  *");
        System.out.println("*                                                  *");
        System.out.println("*                                                 *");
        System.out.println("*                                                  *");
        System.out.println("*                                                  *");
        System.out.println("*                                                  *");
        System.out.println("*                                                  *");
        System.out.println("****************************************************");
        Scanner scanner=new Scanner(System.in);
        do{
            System.out.print("选择：");
            choice=scanner.nextInt();
        }while (choice<1||choice>2);
        switch (choice){
            case 1:modifyInfoController();break;
            case 2:exitController();break;
            default:System.out.println("错误操作");break;
        }
    }
    public void signUpController(){
        code=userService.signUp();
        switch (code){
            case 0:System.out.println("注册成功！");cleanScreen();loginPage();break;
            case 1:System.out.println("用户名已被注册！");cleanScreen();loginPage();break;
            case 2:System.out.println("身份证已被注册！");cleanScreen();loginPage();break;
            case 3:System.out.println("注册异常！");cleanScreen();loginPage();break;
            case 4:System.out.println("密码加密失败！");cleanScreen();loginPage();break;
            default:System.out.println("异常错误！");cleanScreen();loginPage();break;
        }
    }
    public void signInController(){
        code=userService.signIn();
        switch (code){
            case 0:System.out.println("登录成功！");cleanScreen();backPage();break;
            case 1:System.out.println("用户名或密码错误！");cleanScreen();loginPage();break;
            case 2:System.out.println("密码加密失败！");cleanScreen();loginPage();break;
            case 3:System.out.println("查询卡异常！");cleanScreen();loginPage();break;
            default:System.out.println("异常错误！");cleanScreen();loginPage();break;
        }
    }
    public void bindCardController(){
        code=userService.bindCard();
        switch (code){
            case 0:System.out.println("绑定成功！");cleanScreen();backPage();break;
            case 1:System.out.println("输入手机号与该卡预留手机号不同！");cleanScreen();backPage();break;
            case 2:System.out.println("输入身份证号与该卡预留身份证号不同！");cleanScreen();backPage();break;
            case 3:System.out.println("绑定异常！");cleanScreen();backPage();break;
            case 4:System.out.println("该卡已被绑定！");cleanScreen();backPage();break;
            case 5:System.out.println("查询异常！");cleanScreen();backPage();break;
            default:System.out.println("异常错误！");cleanScreen();backPage();break;
        }
    }
    public void userCardsController(){
        code=userService.userCards();
        switch (code){
            case 0:System.out.println("查询成功！");cleanScreen();backPage();break;
            case 1:System.out.println("查询异常！");cleanScreen();backPage();break;
            default:System.out.println("异常错误！");cleanScreen();backPage();break;
        }
    }
    public void userInfoController(){
        code=userService.userInfo();
        switch (code){
            case 0:System.out.println("查询成功！");cleanScreen();backPage();break;
            case 1:System.out.println("省市区信息获取失败！");cleanScreen();backPage();break;
            default:System.out.println("异常错误！");cleanScreen();backPage();break;
        }
    }
    public void modifyInfoController(){
        code=userService.modifyInfo();
        switch (code){
            case 0:System.out.println("修改成功！");cleanScreen();backPage();break;
            case 1:System.out.println("修改失败！");cleanScreen();backPage();break;
            case 2:System.out.println("查无此人！");cleanScreen();backPage();break;
            case 3:System.out.println("查询失败！");cleanScreen();backPage();break;
            case 4:System.out.println("账号异常！");cleanScreen();backPage();break;
            default:System.out.println("异常错误！");cleanScreen();backPage();break;
        }
    }
    public void modifyPasswordController(){
        code=userService.modifyPassword();
        switch (code){
            case 0:System.out.println("密码修改成功！");cleanScreen();backPage();break;
            case 1:System.out.println("密码加密失败！");cleanScreen();backPage();break;
            case 2:System.out.println("旧密码输入错误！");cleanScreen();backPage();break;
            case 3:System.out.println("密码修改失败！");cleanScreen();backPage();break;
            default:System.out.println("异常错误！");cleanScreen();backPage();break;
        }
    }
    public void transactionRecordController(){
        code=userService.transactionRecord();
        switch (code){
            case 0:System.out.println("查询成功！");cleanScreen();backPage();break;
            case 1:System.out.println("查询异常！");cleanScreen();backPage();break;
            case 2:System.out.println("该账号没有绑定的卡！");cleanScreen();backPage();break;
            default:System.out.println("异常错误！");cleanScreen();backPage();break;
        }
    }
    public void withdrawController(){
        code=userService.withdraw();
        switch (code){
            case 0:System.out.println("取款成功！");cleanScreen();backPage();break;
            case 1:System.out.println("卡查询失败！");cleanScreen();backPage();break;
            case 2:System.out.println("余额不足！");cleanScreen();backPage();break;
            case 3:System.out.println("取款异常！");cleanScreen();backPage();break;
            case 4:System.out.println("该账号没有绑定的卡！");cleanScreen();backPage();break;
            default:System.out.println("异常错误！");cleanScreen();backPage();break;
        }
    }
    public void depositController(){
        code=userService.deposit();
        switch (code){
            case 0:System.out.println("存款成功！");cleanScreen();backPage();break;
            case 1:System.out.println("卡查询失败！");cleanScreen();backPage();break;
            case 2:System.out.println("存款异常！");cleanScreen();backPage();break;
            case 3:System.out.println("该账号没有绑定的卡！");cleanScreen();backPage();break;
            default:System.out.println("异常错误！");cleanScreen();backPage();break;
        }
    }
    public void transferController(){
        code=userService.transfer();
        switch (code){
            case 0:System.out.println("转账成功！");cleanScreen();backPage();break;
            case 1:System.out.println("查询失败！");cleanScreen();backPage();break;
            case 2:System.out.println("转账的卡不存在！");cleanScreen();backPage();break;
            case 3:System.out.println("该卡主已被注销！");cleanScreen();backPage();break;
            case 4:System.out.println("余额不足！");cleanScreen();backPage();break;
            case 5:System.out.println("转账异常！");cleanScreen();backPage();break;
            case 6:System.out.println("该账号没有绑定的卡！");cleanScreen();backPage();break;
            default:System.out.println("异常错误！");cleanScreen();backPage();break;
        }
    }
    public void cancellationController(){
        code=userService.cancellation();
        switch (code){
            case 0:System.out.println("注销成功！");cleanScreen();backPage();break;
            case 1:System.out.println("查询失败！");cleanScreen();backPage();break;
            case 2:System.out.println("卡内还有余额！");cleanScreen();backPage();break;
            case 3:System.out.println("注销失败！");cleanScreen();backPage();break;
            default:System.out.println("异常错误！");cleanScreen();backPage();break;
        }
    }
    public void exitController(){
        userService.exit();
    }
    public void backPage(){
        if(currentUser.getCategory()==2){
            if(currentUser.getState()==1){
                okuserPage();
            }else if(currentUser.getState()==2) {
                canceluserPage();
            }else {
                loginPage();
            }
        }else if(currentUser.getCategory()==1){
            adminPage();
        }else {
            loginPage();
        }
    }

}
