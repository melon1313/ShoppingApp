package com.example.chris.goodbuy2.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Product_item_detail implements Serializable {
    int product_id; // 產品id
    String product_name; // 產品名稱
    String  group_id; // 產品群組 id
    int  categories_id; // 產品的分類 id
    String  large_categories; // 產品的大類
    String  middle_categories; // 產品的中類
    String  small_categories; // 產品的小類
    String  product_image; // 產品圖片路徑
    String  product_image2; // 產品圖片路徑2
    String  product_image3; // 產品圖片路徑3
    ArrayList<Integer> quantity; // 產品數量
    int price; // 產品價格
    String  description; // 產品描述
    int seller_id; // 賣家id
    String seller_name;//賣家名字

    ArrayList<String> specification; // 產品規格
    int status; // 產品狀態: 0 不可賣，1:可賣
    String  product_all_image; // 產品圖片的集合，例如該產品有三張，這裡長度就有3

    ArrayList<Integer> evaluatiion_member; // 評價人的id
    ArrayList<String>  evaluatiion_member_name; // 評價人的 名稱
    ArrayList<String> comment_datetime; // 評價時間
    ArrayList<String> comment_context; // 評價內容
    ArrayList<Double> score; // 評價分數

    ArrayList<Integer> commendProductId; // 推薦商品的id
    ArrayList<String>  commendProductName; // 推薦商品的產品名稱
    ArrayList<String>  commendproductImage; // 推薦商品的產品照片路徑
    ArrayList<Integer> commendProductPrice; //推薦商品的價格

    public ArrayList<Integer> getEvaluatiion_member() {
        return evaluatiion_member;
    }

    public void setEvaluatiion_member(ArrayList<Integer> evaluatiion_member) {
        this.evaluatiion_member = evaluatiion_member;
    }

    public ArrayList<String> getEvaluatiion_member_name() {
        return evaluatiion_member_name;
    }

    public void setEvaluatiion_member_name(ArrayList<String> evaluatiion_member_name) {
        this.evaluatiion_member_name = evaluatiion_member_name;
    }

    public ArrayList<String> getComment_datetime() {
        return comment_datetime;
    }

    public void setComment_datetime(ArrayList<String> comment_datetime) {
        this.comment_datetime = comment_datetime;
    }

    public ArrayList<String> getComment_context() {
        return comment_context;
    }

    public void setComment_context(ArrayList<String> comment_context) {
        this.comment_context = comment_context;
    }

    public ArrayList<Double> getScore() {
        return score;
    }

    public void setScore(ArrayList<Double> score) {
        this.score = score;
    }

    public ArrayList<Integer> getCommendProductId() {
        return commendProductId;
    }

    public void setCommendProductId(ArrayList<Integer> commendProductId) {
        this.commendProductId = commendProductId;
    }

    public ArrayList<String> getCommendProductName() {
        return commendProductName;
    }

    public void setCommendProductName(ArrayList<String> commendProductName) {
        this.commendProductName = commendProductName;
    }

    public ArrayList<String> getCommendproductImage() {
        return commendproductImage;
    }

    public void setCommendproductImage(ArrayList<String> commendproductImage) {
        this.commendproductImage = commendproductImage;
    }

    public ArrayList<Integer> getCommendProductPrice() {
        return commendProductPrice;
    }

    public void setCommendProductPrice(ArrayList<Integer> commendProductPrice) {
        this.commendProductPrice = commendProductPrice;
    }

    public Product_item_detail() {
        quantity = new ArrayList<>();
        specification = new ArrayList<>();

        evaluatiion_member = new ArrayList<>();
        evaluatiion_member_name = new ArrayList<>();
        comment_datetime = new ArrayList<>();
        comment_context = new ArrayList<>();
        score = new ArrayList<>();

        commendProductId = new ArrayList<>();
        commendProductName  = new ArrayList<>();
        commendproductImage = new ArrayList<>();
        commendProductPrice = new ArrayList<>();
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public int getCategories_id() {
        return categories_id;
    }

    public void setCategories_id(int categories_id) {
        this.categories_id = categories_id;
    }

    public String getLarge_categories() {
        return large_categories;
    }

    public void setLarge_categories(String large_categories) {
        this.large_categories = large_categories;
    }

    public String getMiddle_categories() {
        return middle_categories;
    }

    public void setMiddle_categories(String middle_categories) {
        this.middle_categories = middle_categories;
    }

    public String getSmall_categories() {
        return small_categories;
    }

    public void setSmall_categories(String small_categories) {
        this.small_categories = small_categories;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_image2() {
        return product_image2;
    }

    public void setProduct_image2(String product_image2) {
        this.product_image2 = product_image2;
    }

    public String getProduct_image3() {
        return product_image3;
    }

    public void setProduct_image3(String product_image3) {
        this.product_image3 = product_image3;
    }

    public ArrayList<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(ArrayList<Integer> quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }
    //////////////////////////////////////////
    //20190210新增 Jacky
    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }
    //20190210新增 Jacky
    /////////////////////////////////////////
    public ArrayList<String> getSpecification() {
        return specification;
    }

    public void setSpecification(ArrayList<String> specification) {
        this.specification = specification;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProduct_all_image() {
        return product_all_image;
    }

    public void setProduct_all_image(String product_all_image) {
        this.product_all_image = product_all_image;
    }
}
