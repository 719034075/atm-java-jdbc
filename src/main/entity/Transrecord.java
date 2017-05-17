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
   private String currency;
   private Date transtime;
   private Integer transkind;

    @Override
    public String toString() {
        return "Transrecord{" +
                "id=" + id +
                ", cardnumber='" + cardnumber + '\'' +
                ", transamount=" + transamount +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", transtime=" + transtime +
                ", transkind=" + transkind +
                '}';
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
