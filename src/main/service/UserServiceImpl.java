package main.service;

import main.entity.Card;
import main.entity.Transrecord;
import main.entity.User;
import main.jdbc.*;
import main.utils.DBProperties;

import java.io.Console;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static main.utils.utils.*;

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

    private static TransrecordMapper transrecordMapper=null;
    public static void setTransrecordMapper(){
        if (transrecordMapper==null){
            transrecordMapper=new TransrecordMapperImpl();
        }
    }

    private static AreasMapper areasMapper=null;
    public static void setAreasMapper(){
        if(areasMapper==null){
            areasMapper=new AreasMapperImpl();
        }
    }

    @Override
    public int signUp() {
         String username;
         String password;
         String againpassword;
         String salt=null;
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
        Console cons = System.console();
        //开始键入信息
        do{
            System.out.print("请输入用户名：");
            username=scanner.next();
        }while (username==null);
        do {
            do {
                System.out.print("请输入密码：");
                if (cons != null) {
                    char[] passwd = cons.readPassword();
                    password=String.valueOf(passwd);
//                System.out.println(password);
                }else {
                    password = scanner.next();
                }
            } while (password == null);
            do {
                System.out.print("请再一次输入密码：");
                if (cons != null) {
                    char[] passwd = cons.readPassword();
                    againpassword=String.valueOf(passwd);
//                System.out.println(password);
                }else {
                    againpassword = scanner.next();
                }
            } while (againpassword == null);
        }while (!Objects.equals(password, againpassword));

        //密码密文形式存储
        try {
            password=getMessageDigset(password.getBytes("UTF8"));
            salt=getMessageDigset(setSalt());
            password=getMessageDigset(salt,password);
        } catch (Exception e) {
            return 4;//密码加密失败
            //e.printStackTrace();
        }

        do{
            System.out.print("请输入真实姓名：");
            realname=scanner.next();
        }while (realname==null);
        do{
            System.out.print("请输入身份证：");
            socialsecid=scanner.next();
        }while (socialsecid==null);
        do{
            System.out.print("请输入性别：");
            gender=scanner.next();
        }while (gender==null);
        getProvince();
        do {
            System.out.print("请输入省：");
            regprovince=scanner.nextInt();
        }while (regprovince==0);
        getCity(regprovince);
        do{
            System.out.print("请输入市：");
            regcity=scanner.nextInt();
        }while (regcity==0);
        getRegion(regcity);
        do{
            System.out.print("请输入区：");
            regregion=scanner.nextInt();
        }while (regregion==0);
        do{
            System.out.print("请输入地址：");
            address=scanner.next();
        }while (address==null);
        do{
            System.out.print("请输入手机：");
            mphone=scanner.next();
        }while (mphone==null);
        regtime=new Timestamp(System.currentTimeMillis());

        user.setUsername(TransactSQLInjection(username));
        user.setRealname(TransactSQLInjection(realname));
        user.setPassword(password);
        user.setSalt(salt);
        user.setSocialsecid(TransactSQLInjection(socialsecid));
        user.setGender(TransactSQLInjection(gender));
        user.setRegprovince(regprovince);
        user.setRegcity(regcity);
        user.setRegregion(regregion);
        user.setAddress(TransactSQLInjection(address));
        user.setMphone(TransactSQLInjection(mphone));
        user.setRegtime(regtime);

        DBConnection connection;
        connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        User testUser;
        List<User> testUsers;
        setUserMapper();
        try {
            testUser=new User();
            testUsers=null;
            testUser.setUsername(username);
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            testUsers=userMapper.selectByCondition(connection,testUser);
//            System.out.println(testUsers);
//            System.out.println(testUsers.size());
            if(testUsers.size()!=0){
                return 1;//用户名已存在
            }
            testUser=new User();
            testUsers=null;
            testUser.setSocialsecid(socialsecid);
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            testUsers=userMapper.selectByCondition(connection,testUser);
            if(testUsers.size()!=0){
                return 2;//身份证已存在
            }
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            userMapper.insertSelective(connection,user);
        } catch (SQLException e) {
            connection.rollback();
            return 3;//注册异常
            //e.printStackTrace();
        }
        return 0;//注册成功
    }

    @Override
    public int signIn() {
        String username;
        String password;
        Scanner scanner=new Scanner(System.in);
        Console cons = System.console();
        do{
            System.out.print("请输入用户名：");
            username=scanner.next();
        }while (username==null);
        do {
            System.out.print("请输入密码：");
            if (cons != null) {
                char[] passwd = cons.readPassword();
                password=String.valueOf(passwd);
//                System.out.println(password);
            }else {
                 password = scanner.next();
            }
        }while (password==null);
        User user=new User();
        List<User> users=new LinkedList<User>();
        user.setUsername(TransactSQLInjection(username));
        DBConnection connection;
        connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setUserMapper();
        try {
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            users=userMapper.selectByCondition(connection,user);
            if(users.size()==0){
                return 1;//用户名或密码错误
            }
            currentUser=users.get(0);
        } catch (SQLException e) {
            connection.rollback();
            return 1;//用户名或密码错误
            //e.printStackTrace();
        }
        try {
            password=getMessageDigset(password.getBytes("UTF8"));
            password=getMessageDigset(currentUser.getSalt(),password);
        } catch (Exception e) {
            return 2;//密码加密失败
            //e.printStackTrace();
        }
        if(Objects.equals(password, currentUser.getPassword())){
            Card card=new Card();
            List<Card> cards=new LinkedList<Card>();
            card.setCardholderid(currentUser.getId());
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            setCardMapper();
            try {
                cards=cardMapper.selectByCondition(connection,card);
                currentUser.setCardCount(cards.size());
                Calendar now=Calendar.getInstance();
                currentUser.setAge(now.get(Calendar.YEAR)-Integer.parseInt(currentUser.getSocialsecid().substring(6,10)));
            } catch (SQLException e) {
                connection.rollback();
                return 3;//查询卡异常
            }
            return 0;//登录成功
        }else {
            currentUser=null;
            return 1;//用户名或密码错误
        }
    }

    @Override
    public int bindCard(){
        String cardnumber;
        String socialsecid;
        String mphone;
        Scanner scanner=new Scanner(System.in);
        do{
            System.out.print("请输入需要绑定的卡号：");
            cardnumber=scanner.next();
        }while (cardnumber==null);
        do {
            System.out.print("请输入该卡预留的身份证号：");
            socialsecid = scanner.next();
        }while (socialsecid==null);
        do{
            System.out.print("请输入该卡预留的的手机号：");
            mphone=scanner.next();
        }while (mphone==null);

        Card card=new Card();
        Card testCard=new Card();
        List<Card> cards=new LinkedList<Card>();
        User user=new User();
        card.setCardholderid(currentUser.getId());
        card.setCardnumber(cardnumber);
        testCard.setCardnumber(cardnumber);
        if(!Objects.equals(mphone, currentUser.getMphone())){
            return 1;//输入手机号与该卡预留手机号不同
        }
        if(!Objects.equals(socialsecid, currentUser.getSocialsecid())){
           return 2;//输入身份证号与该卡预留身份证号不同。
        }

        DBConnection connection;
        connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setCardMapper();
        try {
            cards=cardMapper.selectByCondition(connection,testCard);
            if(cards.size()!=0){
                return 4;//该卡已被绑定
            }
        } catch (SQLException e) {
            connection.rollback();
            return 5;//查询异常
            //e.printStackTrace();
        }
        try {
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            cardMapper.insertSelective(connection,card);
        } catch (SQLException e) {
            connection.rollback();
            //e.printStackTrace();
            return 3;//绑定异常
        }
        user.setCardCount(currentUser.getCardCount()+1);
        return 0;//绑定成功
    }

    @Override
    public int userCards(){
        Card card=new Card();
        List<Card> cards=new LinkedList<Card>();
        card.setCardholderid(currentUser.getId());
        DBConnection connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setCardMapper();
        try {
            cards=cardMapper.selectByCondition(connection,card);
        } catch (SQLException e) {
            connection.rollback();
            return 1;//查询异常
            //e.printStackTrace();
        }
        int i=0;
        partingLine();
        System.out.println("序号\t\t卡号\t\t\t\t余额");
        for(Card card1:cards){
            i++;
            System.out.printf("%3d\t%20s\t\t%12.2f\t\n",i,card1.getCardnumber(),card1.getBalance());
        }
        partingLine();
        return 0;//查询成功
    }

    @Override
    public int userInfo(){
        String province;
        String city;
        String region;
        DBConnection connection;
        connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setAreasMapper();
        try {
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            province=areasMapper.selectByPrimaryKey(connection,currentUser.getRegprovince()).getName();
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            city=areasMapper.selectByPrimaryKey(connection,currentUser.getRegcity()).getName();
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            region=areasMapper.selectByPrimaryKey(connection,currentUser.getRegregion()).getName();
        } catch (SQLException e) {
            return 1;//省市区信息获取失败
            //e.printStackTrace();
        }

        partingLine();
        System.out.println("用户名："+currentUser.getUsername());
        System.out.println("用户真实姓名："+currentUser.getRealname());
        System.out.println("身份证号："+currentUser.getSocialsecid());
        System.out.println("年龄："+currentUser.getAge());
        System.out.println("性别："+currentUser.getGender());
        System.out.println("注册所在省："+province);
        System.out.println("注册所在市："+city);
        System.out.println("注册所在区："+region);
        System.out.println("地址："+currentUser.getAddress());
        System.out.println("手机："+currentUser.getMphone());
        System.out.println("用户注册时间："+currentUser.getRegtime());
        System.out.println("用户类型："+(currentUser.getCategory()==1?"系统管理员":"用户"));
        partingLine();
        return 0;
    }

    @Override
    public int modifyInfo(){
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
        List<User> users=new LinkedList<User>();
        Scanner scanner=new Scanner(System.in);
        if(currentUser.getCategory()==1){
            System.out.println("请输入需要修改的用户名：");
            username=scanner.next();
            user.setUsername(username);
            DBConnection connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            setUserMapper();
            try {
                users=userMapper.selectByCondition(connection,user);
                if(users.size()==0){
                    return 2;//查无此人
                }
            } catch (SQLException e) {
                return 3;//查询失败
                //e.printStackTrace();
            }
            do{
                System.out.print("请输入真实姓名：");
                realname=scanner.next();
            }while (username==null);
            do{
                System.out.print("请输入身份证：");
                socialsecid=scanner.next();
            }while (socialsecid==null);
            do{
                System.out.print("请输入性别：");
                gender=scanner.next();
            }while (gender==null);
            getProvince();
            do{
                System.out.print("请输入省：");
                regprovince=scanner.nextInt();
            }while (regprovince==0);
            getCity(regprovince);
            do{
                System.out.print("请输入市：");
                regcity=scanner.nextInt();
            }while (regcity==0);
            getRegion(regcity);
            do{
                System.out.print("请输入区：");
                regregion=scanner.nextInt();
            }while (regregion==0);
            do{
                System.out.print("请输入地址：");
                address=scanner.next();
            }while (address==null);
            do{
                System.out.print("请输入手机：");
                mphone=scanner.next();
            }while (mphone==null);

            user.setId(users.get(0).getId());
            user.setRealname(TransactSQLInjection(realname));
            user.setSocialsecid(TransactSQLInjection(socialsecid));
            user.setGender(TransactSQLInjection(gender));
            user.setRegprovince(regprovince);
            user.setRegcity(regcity);
            user.setRegregion(regregion);
            user.setAddress(TransactSQLInjection(address));
            user.setMphone(TransactSQLInjection(mphone));


            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            setUserMapper();
            try {
                userMapper.updateByPrimaryKeySelective(connection,user);
            } catch (SQLException e) {
                connection.rollback();
                return 1;//修改失败
                //e.printStackTrace();
            }
            return 0;//修改成功
        }else if(currentUser.getCategory()==2){
            System.out.println("如果需要修改另外的信息，请联系系统管理员。");
            do{
                System.out.print("请输入地址：");
                address=scanner.next();
            }while (address==null);
            do{
                System.out.print("请输入手机：");
                mphone=scanner.next();
            }while (mphone==null);
            user.setId(currentUser.getId());
            user.setAddress(TransactSQLInjection(address));
            user.setMphone(TransactSQLInjection(mphone));
            currentUser.setAddress(TransactSQLInjection(address));
            currentUser.setMphone(TransactSQLInjection(mphone));


            DBConnection connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            setUserMapper();
            try {
                userMapper.updateByPrimaryKeySelective(connection,user);
            } catch (SQLException e) {
                connection.rollback();
                return 1;//修改失败
                //e.printStackTrace();
            }
            return 0;//修改成功
        }else {
            return 4;//账号异常
        }
    }

    @Override
    public int modifyPassword(){
        String oldPassword;
        String newPassword;
        String againPassword;
        String salt;
        User user=new User();
        Scanner scanner=new Scanner(System.in);
        Console cons = System.console();
        do{
            System.out.print("请输入旧密码：");
            if (cons != null) {
                char[] passwd = cons.readPassword();
                oldPassword=String.valueOf(passwd);
//                System.out.println(password);
            }else {
                oldPassword = scanner.next();
            }
        }while (oldPassword==null);
        do {
            do {
                System.out.print("请输入新密码：");
                if (cons != null) {
                    char[] passwd = cons.readPassword();
                    newPassword=String.valueOf(passwd);
//                System.out.println(password);
                }else {
                    newPassword = scanner.next();
                }
            } while (newPassword == null);
            do {
                System.out.print("请再一次输入密码：");
                if (cons != null) {
                    char[] passwd = cons.readPassword();
                    againPassword=String.valueOf(passwd);
//                System.out.println(password);
                }else {
                    againPassword = scanner.next();
                }
            } while (againPassword == null);
        }while (!Objects.equals(newPassword, againPassword));
        DBConnection connection;
        connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setUserMapper();
        try {
            oldPassword=getMessageDigset(oldPassword.getBytes("UTF8"));
            oldPassword=getMessageDigset(currentUser.getSalt(),oldPassword);
        } catch (Exception e) {
            return 1;//密码加密失败
            //e.printStackTrace();
        }
        if(!Objects.equals(oldPassword, currentUser.getPassword())){
            return 2;//旧密码错误
        }else {
            try {
                newPassword=getMessageDigset(newPassword.getBytes("UTF8"));
                salt=getMessageDigset(setSalt());
                newPassword=getMessageDigset(salt,newPassword);
            } catch (Exception e) {
                return 1;//密码加密失败
                //e.printStackTrace();
            }
            user.setId(currentUser.getId());
            user.setPassword(newPassword);
            user.setSalt(salt);
            try {
                userMapper.updateByPrimaryKeySelective(connection, user);
            } catch (SQLException e) {
                connection.rollback();
                return 3;//密码修改失败
                //e.printStackTrace();
            }
            return 0;//密码修改成功
        }
    }

    @Override
    public int transactionRecord(){
        Transrecord transrecord=new Transrecord();
        Card card=new Card();
        List<Transrecord> transrecords=new LinkedList<Transrecord>();
        List<Card> cards=new LinkedList<Card>();
        card.setCardholderid(currentUser.getId());
        String province;
        String city;
        String region;
        DBConnection connection;
        connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        card.setCardholderid(currentUser.getId());
        setCardMapper();
        try {
            cards=cardMapper.selectByCondition(connection,card);
        } catch (SQLException e) {
            connection.rollback();
            return 1;//查询异常
            //e.printStackTrace();
        }
        int i;
        i=0;
        partingLine();
        System.out.println("序号\t\t卡号\t\t\t余额");
        for(Card card1:cards){
            i++;
            System.out.printf("%3d\t%20s\t\t%12.2f\t\n",i,card1.getCardnumber(),card1.getBalance());
        }
        partingLine();
        Scanner scanner=new Scanner(System.in);
        Integer n=0;
        if(i==0){return 2;}
        do{
            System.out.print("请输入要查询的卡序号：");
            n=scanner.nextInt();
        }while (n<1||n>i);
        card=cards.get(n-1);
        transrecord.setCardnumber(card.getCardnumber());
        connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setTransrecordMapper();
        try {
            transrecords=transrecordMapper.selectByCondition(connection,transrecord);
        } catch (SQLException e) {
            connection.rollback();
            return 1;//查询异常
            //e.printStackTrace();
        }

        connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setAreasMapper();
        try {
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            province=areasMapper.selectByPrimaryKey(connection,currentUser.getRegprovince()).getName();
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            city=areasMapper.selectByPrimaryKey(connection,currentUser.getRegcity()).getName();
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            region=areasMapper.selectByPrimaryKey(connection,currentUser.getRegregion()).getName();
        } catch (SQLException e) {
            return 1;//查询异常
            //e.printStackTrace();
        }
        partingLongLine();
        System.out.println("序号\t\t卡号\t\t\t交易金额\t\t余额\t\t交易地点\t\t交易时间\t交易种类");
        i=0;
        for(Transrecord transrecord1:transrecords){
            i++;
            String kind;
            if (transrecord1.getTranskind()==1){
                kind="存款";
            }else if(transrecord1.getTranskind()==2){
                kind="取款";
            }else if(transrecord1.getTranskind()==3){
                kind="转账";
            }else {
                kind="交易类型异常";
            }
            System.out.printf("%3d\t%20s\t\t%+12.2f\t%12.2f\t%s%s%s\t%s\t%s\n",i,transrecord1.getCardnumber(),transrecord1.getTransamount(),transrecord1.getBalance(),province,city,region,transrecord1.getTranstime(),kind);
        }
        partingLongLine();
        return 0;
    }

    @Override
    public int withdraw(){
        Transrecord transrecord=new Transrecord();
        Card card=new Card();
        List<Card> cards=new LinkedList<Card>();
        String cardnumber;
        BigDecimal transamount;
        BigDecimal charge=new BigDecimal(0);
        BigDecimal total;
        Integer province;
        Integer city;
        Integer region;

        card.setCardholderid(currentUser.getId());//先把用户绑定的卡都打印出来
        DBConnection connection;
        connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setCardMapper();
        try {
            cards=cardMapper.selectByCondition(connection,card);
        } catch (SQLException e) {
            connection.rollback();
            return 1;//卡查询异常
            //e.printStackTrace();
        }
        int i;
        i=0;
        partingLine();
        System.out.println("序号\t\t卡号\t\t\t\t余额");
        for(Card card1:cards){
            i++;
            System.out.printf("%3d\t%20s\t\t%12.2f\t\n",i,card1.getCardnumber(),card1.getBalance());
        }
        partingLine();
        Scanner scanner=new Scanner(System.in);//让用户选择取款的卡
        Integer n=0;
        if(i==0){return 4;}
        do{
            System.out.println("请输入要取款的卡序号");
            n=scanner.nextInt();
        }while (n<1||n>i);
        card=cards.get(n-1);

        getProvince();//让用户选择取款操作地
        do {
            System.out.print("请输入取款省：");
            province=scanner.nextInt();
        }while (province==0);
        getCity(province);
        do{
            System.out.print("请输入取款市：");
            city=scanner.nextInt();
        }while (city==0);
        getRegion(city);
        do{
            System.out.print("请输入取款区：");
            region=scanner.nextInt();
        }while (region==0);

        do{
            System.out.print("请输入取款额：");
            transamount=scanner.nextBigDecimal();
        }while (transamount==null);
        if(!Objects.equals(region, currentUser.getRegregion())){
            charge=getCharge(transamount);//计算出手续费
        }

        total=transamount.add(charge);
        System.out.println("取款额："+transamount);
        System.out.println("手续费："+charge);
        System.out.println("共计："+total);
        if(total.compareTo(card.getBalance())==1){
            return 2;//余额不足
        }

        card.setBalance(card.getBalance().subtract(total));
        connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setTransrecordMapper();
        transrecord.setCardnumber(card.getCardnumber());
        transrecord.setTransamount(transamount.multiply(new BigDecimal(-1)));
        transrecord.setBalance(card.getBalance());
        transrecord.setTranskind(2);
        transrecord.setTransprovince(province);
        transrecord.setTranscity(city);
        transrecord.setTransregion(region);
        transrecord.setTranstime(new Timestamp(System.currentTimeMillis()));
        try {
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            transrecordMapper.insertSelective(connection,transrecord);
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            cardMapper.updateByPrimaryKeySelective(connection,card);
        } catch (SQLException e) {
            connection.rollback();
            return 3;//取款异常
            //e.printStackTrace();
        }
        return 0;//取款成功
    }

    @Override
    public int deposit(){
        Transrecord transrecord=new Transrecord();
        Card card=new Card();
        List<Card> cards=new LinkedList<Card>();
        String cardnumber;
        BigDecimal transamount;
        BigDecimal charge=new BigDecimal(0);
        BigDecimal total;
        Integer province;
        Integer city;
        Integer region;

        card.setCardholderid(currentUser.getId());//先把用户绑定的卡都打印出来
        DBConnection connection;
        connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setCardMapper();
        try {
            cards=cardMapper.selectByCondition(connection,card);
        } catch (SQLException e) {
            connection.rollback();
            return 1;//卡查询异常
            //e.printStackTrace();
        }
        int i;
        i=0;
        partingLine();
        System.out.println("序号\t\t卡号\t\t\t\t余额");
        for(Card card1:cards){
            i++;
            System.out.printf("%3d\t%20s\t\t%12.2f\t\n",i,card1.getCardnumber(),card1.getBalance());
        }
        partingLine();
        Scanner scanner=new Scanner(System.in);//让用户选择存款的卡
        Integer n=0;
        if(i==0){return 3;}
        do{
            System.out.println("请输入要存款的卡序号");
            n=scanner.nextInt();
        }while (n<1||n>i);
        card=cards.get(n-1);

        getProvince();//让用户选择存款操作地
        do {
            System.out.print("请输入存款省：");
            province=scanner.nextInt();
        }while (province==0);
        getCity(province);
        do{
            System.out.print("请输入存款市：");
            city=scanner.nextInt();
        }while (city==0);
        getRegion(city);
        do{
            System.out.print("请输入存款区：");
            region=scanner.nextInt();
        }while (region==0);

        do{
            System.out.print("请输入存款额：");
            transamount=scanner.nextBigDecimal();
        }while (transamount==null);
        if(!Objects.equals(region, currentUser.getRegregion())){
            charge=getCharge(transamount);//计算出手续费
        }
        total=transamount.add(charge);
        System.out.println("存款额："+transamount);
        System.out.println("手续费："+charge);
        System.out.println("共计："+total);

        card.setBalance(card.getBalance().add(transamount));
        connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setTransrecordMapper();
        transrecord.setCardnumber(card.getCardnumber());
        transrecord.setTransamount(transamount);
        transrecord.setBalance(card.getBalance());
        transrecord.setTranskind(1);
        transrecord.setTransprovince(province);
        transrecord.setTranscity(city);
        transrecord.setTransregion(region);
        transrecord.setTranstime(new Timestamp(System.currentTimeMillis()));
        try {
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            transrecordMapper.insertSelective(connection,transrecord);
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            cardMapper.updateByPrimaryKeySelective(connection,card);
        } catch (SQLException e) {
            connection.rollback();
            return 2;//存款异常
            //e.printStackTrace();
        }
        return 0;//存款成功
    }

    @Override
    public int transfer(){
        Transrecord withdrawTransrecord=new Transrecord();
        Transrecord depositTransrecord=new Transrecord();
        Card withdrawCard=new Card();
        Card depositCard=new Card();
        List<Card> withdrawCards;
        List<Card> depositCards=new LinkedList<Card>();
        User user=new User();
        String withdrawCardnumber;
        String depositCardnumber;
        BigDecimal transamount;

        BigDecimal charge=new BigDecimal(0);
        BigDecimal total;

        Integer province;
        Integer city;
        Integer region;

        withdrawCard.setCardholderid(currentUser.getId());//先把用户绑定的卡都打印出来
        DBConnection connection;
        connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setCardMapper();
        try {
            withdrawCards=cardMapper.selectByCondition(connection,withdrawCard);
        } catch (SQLException e) {
            connection.rollback();
            return 1;//卡查询异常
            //e.printStackTrace();
        }
        int i;
        i=0;
        partingLine();
        System.out.println("序号\t卡号\t\t\t\t余额");
        for(Card card1:withdrawCards){
            i++;
            System.out.printf("%3d\t%20s\t\t%12.2f\t\n",i,card1.getCardnumber(),card1.getBalance());
        }
        partingLine();
        Scanner scanner=new Scanner(System.in);//让用户选择转出的卡
        Integer n=0;
        if(i==0){return 6;}
        do{
            System.out.println("请输入要转出的卡序号");
            n=scanner.nextInt();
        }while (n<1||n>i);
        withdrawCard=withdrawCards.get(n-1);
        do{
            System.out.println("请输入要转入的卡号：");//让用户输入转出的卡号
            depositCardnumber=scanner.next();
        }while (depositCardnumber==null);

        connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        Card testCard=new Card();
        testCard.setCardnumber(depositCardnumber);
        setCardMapper();
        try {
            depositCards=cardMapper.selectByCondition(connection,testCard);//检查卡是否存在
            if(depositCards.size()==0){
                return 2;//转账的卡不存在
            }
            depositCard=depositCards.get(0);
        } catch (SQLException e) {
            return 1;
            //e.printStackTrace();
        }


        connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setUserMapper();
        try {
            user=userMapper.selectByPrimaryKey(connection,depositCard.getCardholderid());
        } catch (SQLException e) {
            return 1;
            //e.printStackTrace();
        }
        if(user.getState()==2){
            return 3;//该卡主已被注销
        }

        getProvince();//让用户选择存款操作地
        do {
            System.out.print("请输入转账省：");
            province=scanner.nextInt();
        }while (province==0);
        getCity(province);
        do{
            System.out.print("请输入转账市：");
            city=scanner.nextInt();
        }while (city==0);
        getRegion(city);
        do{
            System.out.print("请输入转账区：");
            region=scanner.nextInt();
        }while (region==0);
        do{
            System.out.print("请输入转账额：");
            transamount=scanner.nextBigDecimal();
        }while (transamount==null);
        charge=getCharge(transamount);//计算出手续费
        total=transamount.add(charge);
        if(total.compareTo(withdrawCard.getBalance())==1){
            return 4;//余额不足
        }
        System.out.println("转出卡号："+withdrawCard.getCardnumber());
        System.out.println("转入卡号："+depositCard.getCardnumber());
        System.out.println("转账款额："+transamount);
        System.out.println("手续费："+charge);
        System.out.println("共计："+total);

        withdrawCard.setBalance(withdrawCard.getBalance().subtract(total));
        depositCard.setBalance(depositCard.getBalance().add(transamount));
        connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setTransrecordMapper();
        withdrawTransrecord.setCardnumber(withdrawCard.getCardnumber());
        withdrawTransrecord.setTransamount(transamount.multiply(new BigDecimal(-1)));
        withdrawTransrecord.setBalance(withdrawCard.getBalance());
        withdrawTransrecord.setTranskind(3);
        withdrawTransrecord.setTransprovince(province);
        withdrawTransrecord.setTranscity(city);
        withdrawTransrecord.setTransregion(region);
        withdrawTransrecord.setTranstime(new Timestamp(System.currentTimeMillis()));
        depositTransrecord.setCardnumber(depositCard.getCardnumber());
        depositTransrecord.setTransamount(transamount);
        depositTransrecord.setBalance(depositCard.getBalance());
        depositTransrecord.setTranskind(3);
        depositTransrecord.setTransprovince(province);
        depositTransrecord.setTranscity(city);
        depositTransrecord.setTransregion(region);
        depositTransrecord.setTranstime(new Timestamp(System.currentTimeMillis()));
        try {
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            transrecordMapper.insertSelective(connection,withdrawTransrecord);
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            transrecordMapper.insertSelective(connection,depositTransrecord);
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            cardMapper.updateByPrimaryKeySelective(connection,withdrawCard);
            connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
            cardMapper.updateByPrimaryKeySelective(connection,depositCard);
        } catch (SQLException e) {
            connection.rollback();
            return 5;//转账异常
            //e.printStackTrace();
        }
        return 0;//转账成功
    }

    @Override
    public int cancellation(){
        List<Card> cards=new LinkedList<Card>();
        Card card=new Card();
        card.setCardholderid(currentUser.getId());
        DBConnection connection;
        connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setCardMapper();
        try {
            cards=cardMapper.selectByCondition(connection,card);
        } catch (SQLException e) {
            return 1;//查询失败
            //e.printStackTrace();
        }

        for(Card card1:cards){
            if(card1.getBalance().compareTo(new BigDecimal(0))!=0){
                return 2;//卡内还有余额
            }
        }
        connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        currentUser.setState(2);
        setUserMapper();
        try {
            userMapper.updateByPrimaryKeySelective(connection,currentUser);
        } catch (SQLException e) {
            connection.rollback();
            return 3;//注销失败
            //e.printStackTrace();
        }
        return 0;//注销成功
    }

    @Override
    public void exit(){
        currentUser=null;
    }
}
