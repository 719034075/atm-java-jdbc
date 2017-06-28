package main.utils;

import main.entity.Areas;
import main.jdbc.AreasMapper;
import main.jdbc.AreasMapperImpl;
import main.jdbc.DBConnection;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by 71903 on 2017/5/15.
 */
public class utils {


    private static AreasMapper areasMapper=null;
    public static void setAreasMapper(){
        if(areasMapper==null){
            areasMapper=new AreasMapperImpl();
        }
    }


    /**
     * author:Czy
     * function:
     * 为了方便缺省条件的增删改查，所以执意不使用PreparedStatement来进行sql语句的预会话，
     * 而是使用字符串拼接这种辣鸡方法来组合sql。但是这样非常容易遭受到sql注入攻击。
     * 为了降低攻击风险，通过正则表达式来过滤掉一部分的sql注入语句。在上线应用中千万不要用字符串拼接的方式来组合sql
     * 但是想想jdbc现在已经很少有人用了，所以用这种方式暂时补墙。
     * @param str
     * @return
     */
    public static String TransactSQLInjection(String str){
        return str.replaceAll("(?:')|(?:--)|(/\\\\*(?:.|[\\\\n\\\\r])*?\\\\*/)|(\\\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\\\b)","");
    }

    /**
     *
     * author:Czy
     * function:生成盐
     * @return
     */
    public static byte[] setSalt(){
        Random rand = new Random();
        byte[] salt = new byte[12];
        rand.nextBytes(salt);
        return salt;
    }

    /**
     * author:Czy
     * function:单个bytes[]获得信息摘要
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String getMessageDigset(byte[] bytes) throws Exception{
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(bytes);
        byte s[] = m.digest();
        String result = "";
        for (int i = 0; i < s.length; i++) {
            result += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00)
                    .substring(6);
        }
        return result;
    }

    /**
     * author:Czy
     * function:两个bytes[]获得信息摘要
     * @param salt
     * @param password
     * @return
     * @throws Exception
     */
    public static String getMessageDigset(String salt,String password) throws Exception{
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(salt.getBytes("UTF8"));
        m.update(password.getBytes("UTF8"));
        byte s[] = m.digest();
        String result = "";
        for (int i = 0; i < s.length; i++) {
            result += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00)
                    .substring(6);
        }
        return result;
    }

    /**
     ** author:Czy
     * function:计算手续费，单笔交易0.5%，不低于2元，不超过50元。
     * @param transamount
     * @return
     */
    public static BigDecimal getCharge(BigDecimal transamount){
        BigDecimal charge;
        charge=transamount.multiply(new BigDecimal(0.005));
        if(charge.compareTo(new BigDecimal(2))==-1){
            return new BigDecimal(2);
        }else if(charge.compareTo(new BigDecimal(50))==1){
            return new BigDecimal(50);
        }else {
            return charge.setScale(2,   BigDecimal.ROUND_HALF_UP);
        }
    }

    /**
     *  author:Czy
     * function:获得所有省份
     */
    public static void getProvince(){
        Integer countryID=100000;
        Areas province=new Areas();
        province.setParentid(countryID);
        List<Areas> provinces=new LinkedList<Areas>();
        DBConnection connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setAreasMapper();
        try {
            provinces=areasMapper.selectByCondition(connection,province);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("编号\t省份");
        for(Areas province1:provinces){
            System.out.println(province1.getId()+"\t"+province1.getName());
        }
    }

    /**
     * author:Czy
     * function:获得所有城市
     * @param provinceID
     */
    public static void getCity(int provinceID){
        Areas city=new Areas();
        city.setParentid(provinceID);
        List<Areas> citys=new LinkedList<Areas>();
        DBConnection connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setAreasMapper();
        try {
            citys=areasMapper.selectByCondition(connection,city);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("编号\t城市");
        for(Areas city1:citys){
            System.out.println(city1.getId()+" "+city1.getName());
        }
    }

    /**
     * author:Czy
     * function:获得所有地区
     * @param cityID
     */
    public static void getRegion(int cityID){
        Areas region=new Areas();
        region.setParentid(cityID);
        List<Areas> regions=new LinkedList<Areas>();
        DBConnection connection=new DBConnection(DBProperties.DRIVER,DBProperties.URL,DBProperties.USER,DBProperties.PASSWORD);
        setAreasMapper();
        try {
            regions=areasMapper.selectByCondition(connection,region);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("编号\t地区");
        for(Areas region1:regions){
            System.out.println(region1.getId()+" "+region1.getName());
        }
    }

    /**
     * author:Czy
     * function:Java似乎没有现成的清屏函数，所以用比较辣鸡的换20行来替代。
     */
    public static void cleanScreen(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("按回车继续...");
        scanner.nextLine();
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    /**
     * author:Czy
     * function:很丑的分割线
     */
    public static void partingLine(){
        System.out.println("****************************************************");
    }


    /**
     * author:Czy
     * function:很丑的长的分割线
     */
    public static void partingLongLine(){
        System.out.println("********************************************************************************************************");
    }
}
