package com.miracle.sport.home.bean;

import java.io.Serializable;

public class FLItem implements Serializable {

    /**
     * id : 3
     * title : 【旗舰店品质】北极绒 孕妇无钢圈可哺乳文胸内裤套装
     * class_id : 4
     * author : ¥65.90
     * add_time : 2018-11-29 14:14:42
     * thumb :
     * click_num : 0
     * comment_num : 0
     * coupon : 3
     */

    private int id;
    private String title;
    private int class_id;
    private String author;
    private String add_time;
    private String thumb;
    private int click_num;
    private int comment_num;
    private int coupon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getClick_num() {
        return click_num;
    }

    public void setClick_num(int click_num) {
        this.click_num = click_num;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getCoupon() {
        return coupon;
    }

    public void setCoupon(int coupon) {
        this.coupon = coupon;
    }
}
