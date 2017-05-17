package main.jdbc;

import main.entity.Card;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 71903 on 2017/5/14.
 */
public class CardMapperImpl implements CardMapper {
    private DBConnection connection;

    @Override
    public int insertSelective(DBConnection connection,Card card) throws SQLException {
        int i=0;
        String sql;
        sql=insertSelectiveSql(card);
        i=executeUpdateSql(connection,sql);
        return i;
    }

    @Override
    public int updateByPrimaryKeySelective(DBConnection connection,Card card) throws SQLException {
        int i=0;
        if(card.getId()!=null){
            String sql;
            sql=updateByPrimaryKeySelectiveSql(card);
            i=executeUpdateSql(connection,sql);
        }else {
            i=-1;
        }
        return i;
    }

    @Override
    public Card selectByPrimaryKey(DBConnection connection,Integer id) throws SQLException {
        Card card;
        List<Card> cards;
        String sql;
        sql=selectByPrimaryKeySql(id);
        cards=executeQuerySql(connection,sql);
        card=cards.get(0);
        return card;
    }

    @Override
    public List<Card> selectByCondition(DBConnection connection,Card card) throws SQLException {
        List<Card> cards;
        String sql;
        sql=selectByConditionSql(card);
        cards=executeQuerySql(connection,sql);
        return cards;
    }

    private int executeUpdateSql(DBConnection connection,String sql) throws SQLException {
        int i=0;
        connection.setPreparedStatement(sql);
        i=connection.getPreparedStatement().executeUpdate();
        connection.commit();
        connection.closeConnection();
        return i;
    }

    private List<Card> executeQuerySql(DBConnection connection,String sql) throws SQLException {
        List<Card> cards = new LinkedList<Card>();
        connection.setPreparedStatement(sql);
        ResultSet resultSet=connection.getPreparedStatement().executeQuery();
        connection.commit();
        while (resultSet.next()){
            Card card=new Card();
            card.setId(resultSet.getInt(1));
            card.setCardnumber(resultSet.getString(2));
            card.setBalance(resultSet.getBigDecimal(3));
            card.setCurrency(resultSet.getString(4));
            card.setCardholderid(resultSet.getInt(5));
            cards.add(card);
        }
        connection.closeConnection();
        return cards;
    }

    private String insertSelectiveSql(Card card){
        String sql="insert into card (";
        if(card.getId()!=null){
            sql+="id,";
        }
        if(card.getCardnumber()!=null){
            sql+="cardnumber,";
        }
        if(card.getBalance()!=null){
            sql+="balance,";
        }
        if(card.getCurrency()!=null){
            sql+="currency,";
        }
        if(card.getCardholderid()!=null){
            sql+="cardholderid,";
        }

        sql=sql.substring(0,sql.length()-1);
        sql+=") values(";

        if(card.getId()!=null){
            sql+=card.getId()+",";
        }
        if(card.getCardnumber()!=null){
            sql+="'"+card.getCardnumber()+"',";
        }
        if(card.getBalance()!=null){
            sql+="'"+card.getBalance()+"',";
        }
        if(card.getCurrency()!=null){
            sql+="'"+card.getCurrency()+"',";
        }
        if(card.getCardholderid()!=null){
            sql+="'"+card.getCardholderid()+"',";
        }

        sql=sql.substring(0,sql.length()-1);
        sql+=");";

        return sql;
    }

    private String updateByPrimaryKeySelectiveSql(Card card){
        String sql="update card set ";
        if(card.getId()!=null){
            sql+="id="+card.getId()+",";
        }
        if(card.getCardnumber()!=null){
            sql+="cardnumber='"+card.getCardnumber()+"',";
        }
        if(card.getBalance()!=null){
            sql+="balance='"+card.getBalance()+"',";
        }
        if(card.getCurrency()!=null){
            sql+="currency='"+card.getCurrency()+"',";
        }
        if(card.getCardholderid()!=null){
            sql+="cardholderid='"+card.getCardholderid()+"',";
        }

        sql=sql.substring(0,sql.length()-1);
        sql+="where id="+card.getId()+";";
        return sql;
    }

    private String selectByPrimaryKeySql(Integer id){
        String sql="select * from card where id=";
        sql+=id+";";
        return sql;
    }

    private String selectByConditionSql(Card card){
        String sql="select * from card where ";
        String subsql="";
        if(card.getId()!=null){
            subsql+="and id="+card.getId()+" ";
        }
        if(card.getCardnumber()!=null){
            subsql+="and cardnumber='"+card.getCardnumber()+"' ";
        }
        if(card.getBalance()!=null){
            subsql+="and balance='"+card.getBalance()+"' ";
        }
        if(card.getCurrency()!=null){
            subsql+="and currency='"+card.getCurrency()+"' ";
        }
        if(card.getCardholderid()!=null){
            subsql+="and cardholderid='"+card.getCardholderid()+"' ";
        }

        subsql=subsql.substring(3,subsql.length());
        sql+=subsql+"order by id;";
        return sql;
    }
}
