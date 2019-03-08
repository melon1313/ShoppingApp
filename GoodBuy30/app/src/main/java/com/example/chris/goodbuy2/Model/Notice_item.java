package com.example.chris.goodbuy2.Model;

public class Notice_item {
    private String title; //標題
    private String information; //訊息內文
    private String notice_time; //時間
    private String images; //圖片
    private String ID; //ID

    public Notice_item(String title, String information, String notice_time, String images, String ID) {
        this.title = title;
        this.information = information;
        this.notice_time = notice_time;
        this.images = images;
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getNotice_time() {
        return notice_time;
    }

    public void setNotice_time(String notice_time) {
        this.notice_time = notice_time;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
