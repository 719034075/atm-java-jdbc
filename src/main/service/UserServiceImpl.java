package main.service;

import main.entity.Card;
import main.entity.User;
import main.jdbc.*;
import main.utils.DBProperties;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static main.utils.utils.TransactSQLInjection;

/**
 * Created by 71903 on 2017/5/15.
 */
public class UserServiceImpl implements UserService {

    public static User currentUser=null;

    private static UserMapper userMapper=null;
    public static void setUserMapper(){
        if (userMapper==null){
            userMapper=new UserMapperImpl();
        }
    }

    private static CardMapper cardMapper=null;
    public static void setCardMapper(){
        if(cardMapper==null){
            cardMapper=new CardMapperImpl();
        }
    }

    @Override
    public void signUp() {
         String username;
         String password;
         String salt;
         String realname;
         String socialsecid;
         String gender;
         Integer regprovince;
         Integer regcity;
         Integer regregion;
         String address;
         String mphone;
         Timestamp regtime;

        User user=new User();
        Scanner scanner=new Scanner(System.in);
        System.out.print("请输入用户名：");
        username=scanner.next();
        System.out.print("请输入密码：");
        password=scanner.next();
        System.out.print("请输入盐值：");
        salt=scanner.next();
        System.out.print("请输入真实姓名：");
        realname=scanner.next();
        System.out.print("请输入身份证：");
        socialsecid=scanner.next();
        System.out.print("请输入性别：");
        gender=scanner.next();
        System.out.print("请输入省：");
        regprovince=scanner.nextInt();
        System.out.print("请输入市：");
        regcity=scanner.nextInt();
        System.out.print("请输入区：");
        regregion=scanner.nextInt();
        System.out.print("请输入地址：");
        address=scanner.next();
        System.out.print("请输入手机：");
        mphone=scanner.next();
        regtime=new Timestamp(System.currentTimeMillis());

        user.setUsername(TransactSQLInjection(username));
        user.setPassword(TransactSQLInjection(password));
        user.setSalt(TransactSQLInjection(salt));
        user.setRealname(TransactSQLInjection(realname));
        user.setSocialsecid(TransactSQLInjection(socialsecid));
        user.setGender(TransactSQLInjection(gender));
        user.setRegprovince(regprovince);
        user.setRegcity(regcity);
        user.setRegregion(regregion);
        user.setAddress(TransactSQLInjection(address));
        user.setMphone(TransactSQLInjection(mphone));
        user.setRegtime(regtime);

        DBConnection connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setUserMapper();
        try {
            userMapper.insertSelective(connection,user);
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        System.out.println("注册完成。");
    }

    @Override
    public void signIn() {
        String username;
        String password;
        Scanner scanner=new Scanner(System.in);
        System.out.print("请输入用户名：");
        username=scanner.next();
        System.out.print("请输入密码：");
        password=scanner.next();
        User user=new User();
        user.setUsername(TransactSQLInjection(username));
        DBConnection connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setUserMapper();
        try {
            currentUser=userMapper.selectByCondition(connection,user).get(0);
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        if(Objects.equals(password, currentUser.getPassword())){
            System.out.println("登录成功");
        }else {
            System.out.println("密码错误");
            currentUser=null;
        }

    }

    @Override
    public void bindCard(){
        String cardnumber;
        String socialsecid;
        String mphone;
        Scanner scanner=new Scanner(System.in);
        System.out.print("请输入需要绑定的卡号：");
        cardnumber=scanner.next();
        System.out.print("请输入该卡预留的身份证号：");
        socialsecid=scanner.next();
        System.out.print("请输入该卡预留的的手机号：");
        mphone=scanner.next();
        Card card=new Card();
        User user=new User();
        card.setCardholderid(currentUser.getId());
        card.setCardnumber(cardnumber);
        user.setCardCount(currentUser.getCardCount()+1);
        if(!Objects.equals(mphone, currentUser.getMphone())){
            System.out.print("输入手机号与该卡预留手机号不同。");
        }
        if(!Objects.equals(socialsecid, currentUser.getSocialsecid())){
            System.out.print("输入身份证号与该卡预留身份证号不同。");
        }
        DBConnection connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setCardMapper();
        try {
            cardMapper.insertSelective(connection,card);
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        System.out.println("银行卡绑定成功。");
    }

    @Override
    public void userCards(){
        Card card=new Card();
        List<Card> cards=new LinkedList<Card>();
        card.setCardholderid(currentUser.getId());
        DBConnection connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setCardMapper();
        try {
            cards=cardMapper.selectByCondition(connection,card);
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
        for(Card card1:cards){
            System.out.println(card1);
        }
    }

    @Override
    public void cardBalance(){
        Card card=new Card();
        List<Card> cards=new LinkedList<Card>();
        card.setCardholderid(currentUser.getId());
        DBConnection connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setCardMapper();
        try {
            cards=cardMapper.selectByCondition(connection,card);
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }

        System.out.println("请输入要查询的卡序号");
        Scanner scanner=new Scanner(System.in);
        Integer n=scanner.nextInt();
        card=cards.get(n-1);
        System.out.println(card.getBalance());
    }

    @Override
    public void userInfo(){
        System.out.println();
    }
}
