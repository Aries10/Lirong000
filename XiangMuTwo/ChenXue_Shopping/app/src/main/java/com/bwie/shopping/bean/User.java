package com.bwie.shopping.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 1:类的用途
 * 2：@author Dell
 * 3：@date 2017/9/18
 */
@Entity
public class User {
    @Id
    private Long id;
    private String json;
    @Generated(hash = 1501120509)
    public User(Long id, String json) {
        this.id = id;
        this.json = json;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getJson() {
        return this.json;
    }
    public void setJson(String json) {
        this.json = json;
    }
}
