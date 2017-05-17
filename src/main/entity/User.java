package main.entity;

import java.sql.Timestamp;

/**
 * Created by 71903 on 2017/5/14.
 */
public class User {
    private Integer id;
    private String username;
    private String password;
    private String salt;
    private String realname;
    private String socialsecid;
    private String gender;
    private Integer regprovince;
    private Integer regcity;
    private Integer regregion;
    private String address;
    private String mphone;
    private Timestamp regtime;
    private Integer category;
    private Integer state;
    private Integer cardCount;
    private Integer age;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", realname='" + realname + '\'' +
                ", socialsecid='" + socialsecid + '\'' +
                ", gender='" + gender + '\'' +
                ", regprovince=" + regprovince +
                ", regcity=" + regcity +
                ", regregion=" + regregion +
                ", address='" + address + '\'' +
                ", mphone='" + mphone + '\'' +
                ", regtime=" + regtime +
                ", category=" + category +
                ", state=" + state +
                ", cardCount=" + cardCount +
                ", age=" + age +
                '}';
    }

    public Integer getCardCount() {
        return cardCount;
    }

    public void setCardCount(Integer cardCount) {
        this.cardCount = cardCount;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSocialsecid() {
        return socialsecid;
    }

    public void setSocialsecid(String socialsecid) {
        this.socialsecid = socialsecid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getRegprovince() {
        return regprovince;
    }

    public void setRegprovince(Integer regprovince) {
        this.regprovince = regprovince;
    }

    public Integer getRegcity() {
        return regcity;
    }

    public void setRegcity(Integer regcity) {
        this.regcity = regcity;
    }

    public Integer getRegregion() {
        return regregion;
    }

    public void setRegregion(Integer regregion) {
        this.regregion = regregion;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public Timestamp getRegtime() {
        return regtime;
    }

    public void setRegtime(Timestamp regtime) {
        this.regtime = regtime;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
