package com.dg.info.civildesk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemAdd {
    @SerializedName("item_id")
    @Expose
    private Integer itemId;
    @SerializedName("item_name")
    @Expose
    private String itemName;

    @SerializedName("item_qty")
    @Expose
    private String itemQuantity;


    @SerializedName("item_price")
    @Expose
    private String itemRate;


    @SerializedName("item_subtotal")
    @Expose
    private String itemSubtotal;

    @SerializedName("item_discount")
    @Expose
    private String itemDiscount;

    @SerializedName("item_taxes")
    @Expose
    private String itemTax;

    @SerializedName("item_total")
    @Expose
    private String itemTotal;
    @SerializedName("item_taxname")
    @Expose
    private String itemTaxName;
    @SerializedName("item_taxpercent")
    @Expose
    private String itemTaxperc;
    @SerializedName("item_discountpercent")
    @Expose
    private String itemDiscperc;
    @SerializedName("item_unit")
    @Expose
    private String item_unit;



    public ItemAdd(Integer itemId,String itemName, String itemQuantity,String itemRate,String itemSubtotal, String itemDiscount, String itemTax, String itemTotal,String itemTaxName,String itemTaxperc,String itemDiscperc,String item_unit ) {
        this.itemId=itemId;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemRate = itemRate;
        this.itemSubtotal = itemSubtotal;
        this.itemDiscount = itemDiscount;
        this.itemTax = itemTax;
        this.itemTotal = itemTotal;
        this.itemTaxName=itemTaxName;
        this.itemTaxperc=itemTaxperc;
        this.itemDiscperc=itemDiscperc;
        this.item_unit=item_unit;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }


    public String getItemRate() {
        return itemRate;
    }

    public void setItemRate(String itemRate) {
        this.itemRate = itemRate;
    }


    public String getItemSubtotal() {
        return itemSubtotal;
    }

    public void setItemSubtotal(String itemSubtotal) {
        this.itemSubtotal = itemSubtotal;
    }

    public String getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(String itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    public String getItemTax() {
        return itemTax;
    }

    public void setItemTax(String itemTax) {
        this.itemTax = itemTax;
    }

    public String getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(String itemTotal) {
        this.itemTotal = itemTotal;
    }

    public String getItemTaxName() {
        return itemTaxName;
    }

    public void setItemTaxName(String itemTaxName) {
        this.itemTaxName = itemTaxName;
    }

    public String getItemTaxperc() {
        return itemTaxperc;
    }

    public void setItemTaxperc(String itemTaxperc) {
        this.itemTaxperc = itemTaxperc;
    }

    public String getItemDiscperc() {
        return itemDiscperc;
    }

    public void setItemDiscperc(String itemDiscperc) {
        this.itemDiscperc = itemDiscperc;
    }

    public String getItem_unit() {
        return item_unit;
    }

    public void setItem_unit(String item_unit) {
        this.item_unit = item_unit;
    }
}
