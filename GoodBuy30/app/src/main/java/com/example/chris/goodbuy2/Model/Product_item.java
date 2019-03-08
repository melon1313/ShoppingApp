package com.example.chris.goodbuy2.Model;

import java.io.Serializable;

public class Product_item implements Serializable {
    private int product_id ; // 產品在tb內的流水id號
    private int categories_id; // 產品的分類 id
    private int seller_id; // 賣家id
    private int total_quantity;  // 產品總數量
    private int product_price;  // 產品價錢
    private String product_name; // 產品名稱
    private String large_categories; // 產品的大類
    private String middle_categories; // 產品的中類
    private String small_categories;  // 產品的小類
    private String product_image; // 產品圖片路徑
    private String description; // 產品描述
    private String specification; // 產品規格
    private String seller_name; // 賣家姓名
    private String group_id; // 群組id
    private String join_date; //加入最愛日期

    public Product_item() {
    }

    public Product_item(int product_id, int categories_id, int seller_id,
                        int total_quantity, int product_price, String product_name,
                        String large_categories, String middle_categories, String small_categories,
                        String product_image, String description, String specification,
                        String seller_name , String group_id) {
        this.product_id = product_id;
        this.categories_id = categories_id;
        this.seller_id = seller_id;
        this.total_quantity = total_quantity;
        this.product_price = product_price;
        this.product_name = product_name;
        this.large_categories = large_categories;
        this.middle_categories = middle_categories;
        this.small_categories = small_categories;
        this.product_image = product_image;
        this.description = description;
        this.specification = specification;
        this.seller_name = seller_name;
        this.group_id = group_id;
    }

    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }


    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getCategories_id() {
        return categories_id;
    }

    public void setCategories_id(int categories_id) {
        this.categories_id = categories_id;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getGroup_id()
    {
        return this.group_id;
    }

    public void setGroup_id(String group_id)
    {
        this.group_id = group_id;
    }
}
