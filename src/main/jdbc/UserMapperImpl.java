package main.jdbc;

import main.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 71903 on 2017/5/14.
 */
public class UserMapperImpl implements UserMapper {
    @Override
    public int insertSelective(DBConnection connection,User user) throws SQLException {
        int i=0;
        String sql;
        sql=insertSelectiveSql(user);
        i=executeUpdateSql(connection,sql);
        return i;
    }

    @Override
    public int updateByPrimaryKeySelective(DBConnection connection,User user) throws SQLException {
        int i=0;
        if(user.getId()!=null){
        String sql;
        sql=updateByPrimaryKeySelectiveSql(user);
        i=executeUpdateSql(connection,sql);
        }else {
            i=-1;
        }
        return i;
    }

    @Override
    public User selectByPrimaryKey(DBConnection connection,Integer id) throws SQLException {
        User user;
        List<User> users;
        String sql;
        sql=selectByPrimaryKeySql(id);
        users=executeQuerySql(connection,sql);
        user=users.get(0);
        return user;
    }

    @Override
    public List<User> selectByCondition(DBConnection connection,User user) throws SQLException {
        List<User> users;
        String sql;
        sql=selectByConditionSql(user);
        users=executeQuerySql(connection,sql);
        return users;
    }

    private int executeUpdateSql(DBConnection connection,String sql) throws SQLException {
        int i=0;
        connection.setPreparedStatement(sql);
        i=connection.getPreparedStatement().executeUpdate();
        connection.commit();
        connection.closeConnection();
        return i;
    }

    private List<User> executeQuerySql(DBConnection connection,String sql) throws SQLException {
        List<User> users = new LinkedList<User>();
        connection.setPreparedStatement(sql);
        ResultSet resultSet=connection.getPreparedStatement().executeQuery();
        connection.commit();
        while (resultSet.next()){
            User user=new User();
            user.setId(resultSet.getInt(1));
            user.setUsername(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setSalt(resultSet.getString(4));
            user.setRealname(resultSet.getString(5));
            user.setSocialsecid(resultSet.getString(6));
            user.setGender(resultSet.getString(7));
            user.setRegprovince(resultSet.getInt(8));
            user.setRegcity(resultSet.getInt(9));
            user.setRegregion(resultSet.getInt(10));
            user.setAddress(resultSet.getString(11));
            user.setMphone(resultSet.getString(12));
            user.setRegtime(resultSet.getTimestamp(13));
            user.setCategory(resultSet.getInt(14));
            user.setState(resultSet.getInt(15));
            users.add(user);
        }
        connection.closeConnection();
        return users;
    }

    private String insertSelectiveSql(User user){
        String sql="insert into user (";
        if(user.getId()!=null){
            sql+="id,";
        }
        if(user.getUsername()!=null){
            sql+="username,";
        }
        if(user.getPassword()!=null){
            sql+="password,";
        }
        if(user.getSalt()!=null){
            sql+="salt,";
        }
        if(user.getRealname()!=null){
            sql+="realname,";
        }
        if(user.getSocialsecid()!=null){
            sql+="socialsecid,";
        }
        if(user.getGender()!=null){
            sql+="gender,";
        }
        if(user.getRegprovince()!=null){
            sql+="regprovince,";
        }
        if(user.getRegcity()!=null){
            sql+="regcity,";
        }
        if(user.getRegregion()!=null){
            sql+="regregion,";
        }
        if(user.getAddress()!=null){
            sql+="address,";
        }
        if(user.getMphone()!=null){
            sql+="mphone,";
        }
        if(user.getRegtime()!=null){
            sql+="regtime,";
        }
        if(user.getCategory()!=null){
            sql+="category,";
        }
        if(user.getState()!=null){
            sql+="state,";
        }

        sql=sql.substring(0,sql.length()-1);
        sql+=") values(";

        if(user.getId()!=null){
            sql+=user.getId()+",";
        }
        if(user.getUsername()!=null){
            sql+="'"+user.getUsername()+"',";
        }
        if(user.getPassword()!=null){
            sql+="'"+user.getPassword()+"',";
        }
        if(user.getSalt()!=null){
            sql+="'"+user.getSalt()+"',";
        }
        if(user.getRealname()!=null){
            sql+="'"+user.getRealname()+"',";
        }
        if(user.getSocialsecid()!=null){
            sql+="'"+user.getSocialsecid()+"',";
        }
        if(user.getGender()!=null){
            sql+="'"+user.getGender()+"',";
        }
        if(user.getRegprovince()!=null){
            sql+=user.getRegprovince()+",";
        }
        if(user.getRegcity()!=null){
            sql+=user.getRegcity()+",";
        }
        if(user.getRegregion()!=null){
            sql+=user.getRegregion()+",";
        }
        if(user.getAddress()!=null){
            sql+="'"+user.getAddress()+"',";
        }
        if(user.getMphone()!=null){
            sql+="'"+user.getMphone()+"',";
        }
        if(user.getRegtime()!=null){
            sql+="'"+user.getRegtime()+"',";
        }
        if(user.getCategory()!=null){
            sql+=user.getCategory()+",";
        }
        if(user.getState()!=null){
            sql+=user.getState()+",";
        }

        sql=sql.substring(0,sql.length()-1);
        sql+=");";

        return sql;
    }

    private String updateByPrimaryKeySelectiveSql(User user){
        String sql="update user set ";
        if(user.getId()!=null){
            sql+="id="+user.getId()+",";
        }
        if(user.getUsername()!=null){
            sql+="username='"+user.getUsername()+"',";
        }
        if(user.getPassword()!=null){
            sql+="password='"+user.getPassword()+"',";
        }
        if(user.getSalt()!=null){
            sql+="salt='"+user.getSalt()+"',";
        }
        if(user.getRealname()!=null){
            sql+="realname='"+user.getRealname()+"',";
        }
        if(user.getSocialsecid()!=null){
            sql+="socialsecid='"+user.getSocialsecid()+"',";
        }
        if(user.getGender()!=null){
            sql+="gender='"+user.getGender()+"',";
        }
        if(user.getRegprovince()!=null){
            sql+="regprovince="+user.getRegprovince()+",";
        }
        if(user.getRegcity()!=null){
            sql+="regcity="+user.getRegcity()+",";
        }
        if(user.getRegregion()!=null){
            sql+="regregion="+user.getRegregion()+",";
        }
        if(user.getAddress()!=null){
            sql+="address='"+user.getAddress()+"',";
        }
        if(user.getMphone()!=null){
            sql+="mphone='"+user.getMphone()+"',";
        }
        if(user.getRegtime()!=null){
            sql+="regtime='"+user.getRegtime()+"',";
        }
        if(user.getCategory()!=null){
            sql+="category='"+user.getCategory()+"',";
        }
        if(user.getState()!=null){
            sql+="state='"+user.getState()+"',";
        }
        sql=sql.substring(0,sql.length()-1);
        sql+="where id="+user.getId()+";";
        return sql;
    }

    private String selectByPrimaryKeySql(Integer id){
        String sql="select * from user where id=";
        sql+=id+";";
        return sql;
    }

    private String selectByConditionSql(User user){
        String sql="select * from user where ";
        String subsql="";
        if(user.getId()!=null){
            subsql+="and id="+user.getId()+" ";
        }
        if(user.getUsername()!=null){
            subsql+="and username='"+user.getUsername()+"' ";
        }
        if(user.getPassword()!=null){
            subsql+="and password='"+user.getPassword()+"' ";
        }
        if(user.getSalt()!=null){
            subsql+="and salt='"+user.getSalt()+"' ";
        }
        if(user.getRealname()!=null){
            subsql+="and realname='"+user.getRealname()+"' ";
        }
        if(user.getSocialsecid()!=null){
            subsql+="and socialsecid='"+user.getSocialsecid()+"' ";
        }
        if(user.getGender()!=null){
            subsql+="and gender='"+user.getGender()+"' ";
        }
        if(user.getRegprovince()!=null){
            subsql+="and regprovince="+user.getRegprovince()+" ";
        }
        if(user.getRegcity()!=null){
            subsql+="and regcity="+user.getRegcity()+" ";
        }
        if(user.getRegregion()!=null){
            subsql+="and regregion="+user.getRegregion()+" ";
        }
        if(user.getAddress()!=null){
            subsql+="and address='"+user.getAddress()+"' ";
        }
        if(user.getMphone()!=null){
            subsql+="and mphone='"+user.getMphone()+"' ";
        }
        if(user.getRegtime()!=null){
            subsql+="and regtime='"+user.getRegtime()+"' ";
        }
        if(user.getCategory()!=null){
            subsql+="and category='"+user.getCategory()+"' ";
        }
        if(user.getState()!=null){
            subsql+="and state='"+user.getState()+"' ";
        }
        subsql=subsql.substring(3,subsql.length());
        sql+=subsql+"order by id;";
        return sql;
    }
}
