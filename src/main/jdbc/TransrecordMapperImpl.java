package main.jdbc;

import main.entity.Transrecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 71903 on 2017/5/15.
 */
public class TransrecordMapperImpl implements TransrecordMapper {
    @Override
    public int insertSelective(DBConnection connection,Transrecord transrecord) throws SQLException {
        int i=0;
        String sql;
        sql=insertSelectiveSql(transrecord);
        i=executeUpdateSql(connection,sql);
        return i;
    }

    @Override
    public int updateByPrimaryKeySelective(DBConnection connection,Transrecord transrecord) throws SQLException {
        int i=0;
        if(transrecord.getId()!=null){
            String sql;
            sql=updateByPrimaryKeySelectiveSql(transrecord);
            i=executeUpdateSql(connection,sql);
        }else {
            i=-1;
        }
        return i;
    }

    @Override
    public Transrecord selectByPrimaryKey(DBConnection connection,Integer id) throws SQLException {
        Transrecord transrecord;
        List<Transrecord> transrecords;
        String sql;
        sql=selectByPrimaryKeySql(id);
        transrecords=executeQuerySql(connection,sql);
        transrecord=transrecords.get(0);
        return transrecord;
    }

    @Override
    public List<Transrecord> selectByCondition(DBConnection connection,Transrecord transrecord) throws SQLException {
        List<Transrecord> transrecords;
        String sql;
        sql=selectByConditionSql(transrecord);
        transrecords=executeQuerySql(connection,sql);
        return transrecords;
    }

    private int executeUpdateSql(DBConnection connection,String sql) throws SQLException {
        int i=0;
        connection.setPreparedStatement(sql);
        i=connection.getPreparedStatement().executeUpdate();
        connection.commit();
        connection.closeConnection();
        return i;
    }

    private List<Transrecord> executeQuerySql(DBConnection connection,String sql) throws SQLException {
        List<Transrecord> transrecords = new LinkedList<Transrecord>();
        connection.setPreparedStatement(sql);
        ResultSet resultSet=connection.getPreparedStatement().executeQuery();
        connection.commit();
        while (resultSet.next()){
            Transrecord transrecord=new Transrecord();
            transrecord.setId(resultSet.getInt(1));
            transrecord.setCardnumber(resultSet.getString(2));
            transrecord.setTransamount(resultSet.getBigDecimal(3));
            transrecord.setBalance(resultSet.getBigDecimal(4));
            transrecord.setCurrency(resultSet.getString(5));
            transrecord.setTranstime(resultSet.getDate(6));
            transrecord.setTranskind(resultSet.getInt(7));
            transrecords.add(transrecord);
        }
        connection.closeConnection();
        return transrecords;
    }

    private String insertSelectiveSql(Transrecord transrecord){
        String sql="insert into transrecord (";
        if(transrecord.getId()!=null){
            sql+="id,";
        }
        if(transrecord.getCardnumber()!=null){
            sql+="cardnumber,";
        }
        if(transrecord.getTransamount()!=null){
            sql+="transamount,";
        }
        if(transrecord.getBalance()!=null){
            sql+="balance,";
        }
        if(transrecord.getCurrency()!=null){
            sql+="currency,";
        }
        if(transrecord.getTranstime()!=null){
            sql+="transtime,";
        }
        if(transrecord.getTranskind()!=null){
            sql+="transkind,";
        }


        sql=sql.substring(0,sql.length()-1);
        sql+=") values(";

        if(transrecord.getId()!=null){
            sql+=transrecord.getId()+",";
        }
        if(transrecord.getCardnumber()!=null){
            sql+="'"+transrecord.getCardnumber()+"',";
        }
        if(transrecord.getTransamount()!=null){
            sql+="'"+transrecord.getTransamount()+"',";
        }
        if(transrecord.getBalance()!=null){
            sql+="'"+transrecord.getBalance()+"',";
        }
        if(transrecord.getCurrency()!=null){
            sql+="'"+transrecord.getCurrency()+"',";
        }
        if(transrecord.getTranstime()!=null){
            sql+="'"+transrecord.getTranstime()+"',";
        }
        if(transrecord.getTranskind()!=null){
            sql+="'"+transrecord.getTranskind()+"',";
        }

        sql=sql.substring(0,sql.length()-1);
        sql+=");";

        return sql;
    }

    private String updateByPrimaryKeySelectiveSql(Transrecord transrecord){
        String sql="update transrecord set ";
        if(transrecord.getId()!=null){
            sql+="id="+transrecord.getId()+",";
        }
        if(transrecord.getCardnumber()!=null){
            sql+="cardnumber='"+transrecord.getCardnumber()+"',";
        }
        if(transrecord.getTransamount()!=null){
            sql+="transamount='"+transrecord.getTransamount()+"',";
        }
        if(transrecord.getBalance()!=null){
            sql+="balance='"+transrecord.getBalance()+"',";
        }
        if(transrecord.getCurrency()!=null){
            sql+="currency='"+transrecord.getCurrency()+"',";
        }
        if(transrecord.getTranstime()!=null){
            sql+="transtime='"+transrecord.getTranstime()+"',";
        }
        if(transrecord.getTranskind()!=null){
            sql+="transkind='"+transrecord.getTranskind()+"',";
        }

        sql=sql.substring(0,sql.length()-1);
        sql+="where id="+transrecord.getId()+";";
        return sql;
    }

    private String selectByPrimaryKeySql(Integer id){
        String sql="select * from transrecord where id=";
        sql+=id+";";
        return sql;
    }

    private String selectByConditionSql(Transrecord transrecord){
        String sql="select * from transrecord where ";
        String subsql="";
        if(transrecord.getId()!=null){
            subsql+="and id="+transrecord.getId()+" ";
        }
        if(transrecord.getCardnumber()!=null){
            subsql+="and cardnumber='"+transrecord.getCardnumber()+"' ";
        }
        if(transrecord.getTransamount()!=null){
            subsql+="and transamount='"+transrecord.getTransamount()+"' ";
        }
        if(transrecord.getBalance()!=null){
            subsql+="and balance='"+transrecord.getBalance()+"' ";
        }
        if(transrecord.getCurrency()!=null){
            subsql+="and currency='"+transrecord.getCurrency()+"' ";
        }
        if(transrecord.getTranstime()!=null){
            subsql+="and transtime='"+transrecord.getTranstime()+"' ";
        }
        if(transrecord.getTranskind()!=null){
            subsql+="and transkind='"+transrecord.getTranskind()+"' ";
        }

        subsql=subsql.substring(3,subsql.length());
        sql+=subsql+"order by id;";
        return sql;
    }
}
