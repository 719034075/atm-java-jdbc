package main.jdbc;

import main.entity.Transrecord;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 71903 on 2017/5/15.
 */
public interface TransrecordMapper {

    /**
     * author:Czy
     * function:动态插入Transrecord
     * @param connection,transrecord
     * @return
     */
    int insertSelective(DBConnection connection,Transrecord transrecord) throws SQLException;

    /**
     * author:Czy
     * function:动态更新Transrecord
     * @param connection,transrecord
     * @return
     */
    int updateByPrimaryKeySelective(DBConnection connection,Transrecord transrecord) throws SQLException;

    /**
     * author:Czy
     * function:根据主键查询Transrecord
     * @param connection,id
     * @return
     */
    Transrecord selectByPrimaryKey(DBConnection connection,Integer id) throws SQLException;

    /**
     * author:Czy
     * function:根据条件查询Transrecord
     * @param connection,transrecord
     * @return
     */
    List<Transrecord> selectByCondition(DBConnection connection,Transrecord transrecord) throws SQLException;
}
