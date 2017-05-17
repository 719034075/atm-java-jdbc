package main.jdbc;

import main.entity.Areas;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 71903 on 2017/5/15.
 */
public interface AreasMapper {

    /**
     * author:Czy
     * function:根据主键查询Areas
     * @param connection,id
     * @return
     */
    Areas selectByPrimaryKey(DBConnection connection,Integer id) throws SQLException;

    /**
     * author:Czy
     * function:根据条件查询Areas
     * @param connection,areas
     * @return
     */
    List<Areas> selectByCondition(DBConnection connection,Areas areas) throws SQLException;
}
