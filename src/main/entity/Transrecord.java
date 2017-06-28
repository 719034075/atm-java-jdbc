package main.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 71903 on 2017/5/14.
 */
public class Transrecord {
   private Integer id;
   private String cardnumber;
   private BigDecimal transamount;
   private BigDecimal balance;
   private Date transtime;
   private Integer transprovince;
   private Integer transcity;
   private Integer transregion;
   private Integer transkind;


    public Integer getTransprovince() {
        return transprovince;
    }

    public void setTransprovince(Integer transprovince) {
        this.transprovince = transprovince;
    }

    public Integer getTranscity() {
        return transcity;
    }

    public void setTranscity(Integer transcity) {
        this.transcity = transcity;
    }

    public Integer getTransregion() {
        return transregion;
    }

    public void setTransregion(Integer transregion) {
        this.transregion = transregion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public BigDecimal getTransamount() {
        return transamount;
    }

    public void setTransamount(BigDecimal transamount) {
        this.transamount = transamount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getTranstime() {
        return transtime;
    }

    public void setTranstime(Date transtime) {
        this.transtime = transtime;
    }

    public Integer getTranskind() {
        return transkind;
    }

    public void setTranskind(Integer transkind) {
        this.transkind = transkind;
    }
}
