package main.jdbc;

import main.entity.Areas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 71903 on 2017/5/15.
 */
public class AreasMapperImpl implements AreasMapper {
    private DBConnection connection;
    @Override
    public Areas selectByPrimaryKey(DBConnection connection,Integer id) throws SQLException {
        Areas areas;
        List<Areas> areass;
        String sql;
        sql=selectByPrimaryKeySql(id);
        areass=executeQuerySql(connection,sql);
        areas=areass.get(0);
        return areas;
    }

    @Override
    public List<Areas> selectByCondition(DBConnection connection,Areas areas) throws SQLException {
        List<Areas> areass;
        String sql;
        sql=selectByConditionSql(areas);
        areass=executeQuerySql(connection,sql);
        return areass;
    }

    private List<Areas> executeQuerySql(DBConnection connection,String sql) throws SQLException {
        List<Areas> areass = new LinkedList<Areas>();
        connection.setPreparedStatement(sql);
        ResultSet resultSet=connection.getPreparedStatement().executeQuery();
        connection.commit();
        while (resultSet.next()){
            Areas areas=new Areas();
            areas.setId(resultSet.getInt(1));
            areas.setParentid(resultSet.getInt(2));
            areas.setName(resultSet.getString(3));
            areass.add(areas);
        }
        connection.closeConnection();
        return areass;
    }

    private String selectByPrimaryKeySql(Integer id){
        String sql="select * from areas where id=";
        sql+=id+";";
        return sql;
    }

    private String selectByConditionSql(Areas areas){
        String sql="select * from areas where ";
        String subsql="";
        if(areas.getId()!=null){
            subsql+="and id="+areas.getId()+" ";
        }
        if(areas.getName()!=null){
            subsql+="and name='"+areas.getName()+"' ";
        }
        if(areas.getParentid()!=null){
            subsql+="and parentid='"+areas.getParentid()+"' ";
        }

        subsql=subsql.substring(3,subsql.length());
        sql+=subsql+"order by id;";
        return sql;
    }
}
