package main.entity;

import java.math.BigDecimal;

/**
 * Created by 71903 on 2017/5/14.
 */
public class Card {
    private Integer id;
    private String cardnumber;
    private BigDecimal balance;
    private String currency;
    private Integer cardholderid;

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardnumber='" + cardnumber + '\'' +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", cardholderid=" + cardholderid +
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

    public Integer getCardholderid() {
        return cardholderid;
    }

    public void setCardholderid(Integer cardholderid) {
        this.cardholderid = cardholderid;
    }
}
