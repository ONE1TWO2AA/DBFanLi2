package com.miracle.sport.home.bean;

import java.io.Serializable;
import java.util.List;

public class Football implements Serializable {


    /**
     * id : 36686
     * bigtype : cp
     * smalltype : wycp
     * title : 德乙:马格德堡主场盼首胜 杜伊斯堡客场求反弹
     * thumb :
     * content :
     * <p>
     * 周三016德乙 马格德堡VS杜伊斯堡2018-09-27 00:30北京时间9月27日00:30，2018/19赛季德国足球乙级联赛第七轮第二个比赛日将同时展开四场比赛的争夺，其中马格德堡队将在主场迎战杜伊斯堡队。主队在新赛季七场正式比赛中取得4平3负，客队在新赛季七场正式比赛中取得1胜1平5负。主队近况：进攻恢复 马格德堡主场稳健马格德堡队在上周日进行的德乙联赛中客场4比4逼平帕德博恩队，在联赛中取得两连平。在开场仅仅七分钟就连丢两球的马格德堡队凭借强势反攻在第26分钟扳回一球，在下半场与对手展开对攻大战并在第三次两球落后的情况下凭借绝地反击在比赛最后阶段连入两球，最终客场4比4逼平帕德博恩队，赛后马格德堡队主帅海特尔表示开
     * author : 网易彩票
     * pubtime : 1538565000
     * time : 2018-10-03 19:10:00
     */

    private int id;
    private String bigtype;
    private String smalltype;
    private String title;
    private String thumb;
    private String content;
    private String author;
    private int pubtime;
    private String time;
    private String addTime;
    private String class_id;
    private String[] images;
    private int click_num;
    private int comment_num;
    private String add_time;
    private int coupon;
    private int click;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBigtype() {
        return bigtype;
    }

    public void setBigtype(String bigtype) {
        this.bigtype = bigtype;
    }

    public String getSmalltype() {
        return smalltype;
    }

    public void setSmalltype(String smalltype) {
        this.smalltype = smalltype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPubtime() {
        return pubtime;
    }

    public void setPubtime(int pubtime) {
        this.pubtime = pubtime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
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

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
