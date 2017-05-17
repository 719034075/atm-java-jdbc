package main.jdbc;

import main.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 71903 on 2017/5/14.
 */
public interface UserMapper {

    /**
     * author:Czy
     * function:动态插入User
     * @param connection,user
     * @return
     */
    int insertSelective(DBConnection connection,User user) throws SQLException;

    /**
     * author:Czy
     * function:动态更新User
     * @param connection,user
     * @return
     */
    int updateByPrimaryKeySelective(DBConnection connection,User user) throws SQLException;

    /**
     * author:Czy
     * function:根据主键查询User
     * @param connection,id
     * @return
     */
    User selectByPrimaryKey(DBConnection connection,Integer id) throws SQLException;

    /**
     * author:Czy
     * function:根据条件查询User
     * @param connection,user
     * @return
     */
    List<User> selectByCondition(DBConnection connection,User user) throws SQLException;


}
