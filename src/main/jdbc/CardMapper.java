package main.jdbc;

import main.entity.Card;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 71903 on 2017/5/14.
 */
public interface CardMapper {
    /**
     * author:Czy
     * function:动态插入Card
     * @param connection,card
     * @return
     */
    int insertSelective(DBConnection connection,Card card) throws SQLException;

    /**
     * author:Czy
     * function:动态更新Card
     * @param connection,card
     * @return
     */
    int updateByPrimaryKeySelective(DBConnection connection,Card card) throws SQLException;

    /**
     * author:Czy
     * function:根据主键查询Card
     * @param connection,id
     * @return
     */
    Card selectByPrimaryKey(DBConnection connection,Integer id) throws SQLException;

    /**
     * author:Czy
     * function:根据条件查询Card
     * @param connection,card
     * @return
     */
    List<Card> selectByCondition(DBConnection connection,Card card) throws SQLException;
}
