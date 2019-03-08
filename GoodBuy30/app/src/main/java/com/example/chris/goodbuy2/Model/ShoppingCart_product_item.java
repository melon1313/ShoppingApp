package com.example.chris.goodbuy2.Model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart_product_item {
    private String member_id;
    private String seller_id;
    private String seller_name;
    private List<String> shoppintCart_product_ids;
    private List<String> shoppintCart_product_names;
    private List<String> shoppingCart_product_images;
    private List<String> shoppingCart_product_unit_price;
    private List<String> shoppingCart_product_quantities;
    //20190209Jacky新增/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private List<String> ShoppingCart_product_speciations;
    private List<String> isCheckLists; // 1 為true(勾選) , 0 為 false (不勾選)
    private boolean isCheck; //true(勾選) , false (不勾選)

    public ShoppingCart_product_item(){
        shoppintCart_product_ids = new ArrayList<>();
        shoppintCart_product_names = new ArrayList<>();
        shoppingCart_product_images = new ArrayList<>();
        shoppingCart_product_unit_price = new ArrayList<>();
        shoppingCart_product_quantities = new ArrayList<>();
        ShoppingCart_product_speciations=new ArrayList<>();
        isCheckLists = new ArrayList<>();
        isCheck = false;
    }

    public boolean isCheck(){
        return isCheck;
    }

    public void setIsCheck(boolean b){
        isCheck = b;
    }

    public List<String> getIsCheckLists(){
        return isCheckLists;
    }

    public void setIsCheckLists(int position, String string){
        isCheckLists.set(position, string);
    }


    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public List<String> getShoppintCart_product_ids() {
        return shoppintCart_product_ids;
    }

    public void setShoppintCart_product_ids(List<String> shoppintCart_product_ids) {
        this.shoppintCart_product_ids = shoppintCart_product_ids;
    }

    public List<String> getShoppintCart_product_names() {
        return shoppintCart_product_names;
    }

    public void setShoppintCart_product_names(List<String> shoppintCart_product_names) {
        this.shoppintCart_product_names = shoppintCart_product_names;
    }

    public List<String> getShoppingCart_product_images() {
        return shoppingCart_product_images;
    }

    public void setShoppingCart_product_images(List<String> shoppingCart_product_images) {
        this.shoppingCart_product_images = shoppingCart_product_images;
    }

    public List<String> getShoppingCart_product_unit_price() {
        return shoppingCart_product_unit_price;
    }

    public void setShoppingCart_product_unit_price(List<String> shoppingCart_product_unit_price) {
        this.shoppingCart_product_unit_price = shoppingCart_product_unit_price;
    }

    public List<String> getShoppingCart_product_quantities() {
        return shoppingCart_product_quantities;
    }

    public void setShoppingCart_product_quantities(List<String> shoppingCart_product_quantities) {
        this.shoppingCart_product_quantities = shoppingCart_product_quantities;
    }
    //20190209Jacky新增/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<String> getShoppingCart_product_speciations() {
        return ShoppingCart_product_speciations;
    }
    //20190209Jacky新增/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setShoppingCart_product_speciations(List<String> ShoppingCart_product_speciations) {
        this.ShoppingCart_product_speciations = ShoppingCart_product_speciations;
    }
    //20190209Jacky新增/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
