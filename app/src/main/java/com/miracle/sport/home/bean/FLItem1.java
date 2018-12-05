package com.miracle.sport.home.bean;

import java.io.Serializable;
import java.util.List;

public class FLItem1 implements Serializable {


    /**
     * id : 55
     * title : 飞(Free)纯棉特长夜用卫生巾420mm*3片
     * author : ¥13.50
     * thumb : http://cdn.178hui.com/upload/2018/1029/15440956557.jpg
     * time : ¥6.69
     * click_num : 0
     * comment_num : 0
     * coupon : 3
     * images : ["http://cdn.178hui.com/upload/2018/1029/15440956557.jpg"]
     * click : 0
     */

    private int id;
    private String title;
    private String author;
    private String thumb;
    private String time;
    private int click_num;
    private int comment_num;
    private int coupon;
    private int click;
    private List<String> images;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
