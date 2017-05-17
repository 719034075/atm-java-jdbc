package main.utils;

/**
 * Created by 71903 on 2017/5/15.
 */
public class utils {

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

}
