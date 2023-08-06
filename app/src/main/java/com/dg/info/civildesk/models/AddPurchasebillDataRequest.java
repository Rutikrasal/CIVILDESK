package com.dg.info.civildesk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddPurchasebillDataRequest {
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("party_id")
    @Expose
    private String party_id;
    @SerializedName("bill_no")
    @Expose
    private String bill_no;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("customer_name")
    @Expose
    private String customer_name;
    @SerializedName("mobile_no")
    @Expose
    private String mobile_no;
    @SerializedName("total_amount")
    @Expose
    private String total_amount;
    @SerializedName("paid_amount")
    @Expose
    private String paid_amount;
    @SerializedName("balance_due")
    @Expose
    private String balance_due;
    @SerializedName("payment_type")
    @Expose
    private String payment_type;
    @SerializedName("payment_refno")
    @Expose
    private String payment_refno;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("round_off")
    @Expose
    private String round_off;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("Item")
    @Expose
    private ArrayList<ItemAdd> item = null;

    public AddPurchasebillDataRequest(String user_id, String party_id, String bill_no, String date, String customer_name, String mobile_no, String total_amount, String paid_amount, String balance_due, String payment_type, String payment_refno, String state, String round_off, String description, String image, ArrayList<ItemAdd> item) {
        this.user_id = user_id;
        this.party_id = party_id;
        this.bill_no = bill_no;
        this.date = date;
        this.customer_name = customer_name;
        this.mobile_no = mobile_no;
        this.total_amount = total_amount;
        this.paid_amount = paid_amount;
        this.balance_due = balance_due;
        this.payment_type = payment_type;
        this.payment_refno = payment_refno;
        this.state = state;
        this.round_off = round_off;
        this.description = description;
        this.image = image;
        this.item = item;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getParty_id() {
        return party_id;
    }

    public void setParty_id(String party_id) {
        this.party_id = party_id;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String getBalance_due() {
        return balance_due;
    }

    public void setBalance_due(String balance_due) {
        this.balance_due = balance_due;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getPayment_refno() {
        return payment_refno;
    }

    public void setPayment_refno(String payment_refno) {
        this.payment_refno = payment_refno;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRound_off() {
        return round_off;
    }

    public void setRound_off(String round_off) {
        this.round_off = round_off;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<ItemAdd> getItem() {
        return item;
    }

    public void setItem(ArrayList<ItemAdd> item) {
        this.item = item;
    }
}
