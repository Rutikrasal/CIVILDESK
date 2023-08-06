package com.dg.info.civildesk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowPurchasebillResponse {
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("ResponseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {
        @SerializedName("purchasebillitem_id")
        @Expose
        private String purchasebillitemId;
        @SerializedName("purchasebill_id")
        @Expose
        private String purchasebillId;
        @SerializedName("item_name")
        @Expose
        private String itemName;
        @SerializedName("purchasebillitem_qty")
        @Expose
        private String purchasebillitemQty;
        @SerializedName("purchasebillitem_unit")
        @Expose
        private String purchasebillitemUnit;
        @SerializedName("purchasebillitem_price")
        @Expose
        private String purchasebillitemPrice;
        @SerializedName("purchasebillitem_taxes")
        @Expose
        private String purchasebillitemTaxes;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("party_id")
        @Expose
        private String partyId;
        @SerializedName("purchasebillcustomer_name")
        @Expose
        private String purchasebillcustomerName;
        @SerializedName("purchasebill_no")
        @Expose
        private String purchasebillNo;
        @SerializedName("purchasebill_date")
        @Expose
        private String purchasebillDate;
        @SerializedName("payment_type")
        @Expose
        private String paymentType;
        @SerializedName("purchasebill_description")
        @Expose
        private String purchasebillDescription;
        @SerializedName("round_off")
        @Expose
        private String roundOff;
        @SerializedName("paid_amount")
        @Expose
        private String paidAmount;
        @SerializedName("balance_amount")
        @Expose
        private String balanceAmount;
        @SerializedName("purchasebill_image")
        @Expose
        private String purchasebillImage;

        public String getPurchasebillitemId() {
            return purchasebillitemId;
        }

        public void setPurchasebillitemId(String purchasebillitemId) {
            this.purchasebillitemId = purchasebillitemId;
        }

        public String getPurchasebillId() {
            return purchasebillId;
        }

        public void setPurchasebillId(String purchasebillId) {
            this.purchasebillId = purchasebillId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getPurchasebillitemQty() {
            return purchasebillitemQty;
        }

        public void setPurchasebillitemQty(String purchasebillitemQty) {
            this.purchasebillitemQty = purchasebillitemQty;
        }

        public String getPurchasebillitemUnit() {
            return purchasebillitemUnit;
        }

        public void setPurchasebillitemUnit(String purchasebillitemUnit) {
            this.purchasebillitemUnit = purchasebillitemUnit;
        }

        public String getPurchasebillitemPrice() {
            return purchasebillitemPrice;
        }

        public void setPurchasebillitemPrice(String purchasebillitemPrice) {
            this.purchasebillitemPrice = purchasebillitemPrice;
        }

        public String getPurchasebillitemTaxes() {
            return purchasebillitemTaxes;
        }

        public void setPurchasebillitemTaxes(String purchasebillitemTaxes) {
            this.purchasebillitemTaxes = purchasebillitemTaxes;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPartyId() {
            return partyId;
        }

        public void setPartyId(String partyId) {
            this.partyId = partyId;
        }

        public String getPurchasebillcustomerName() {
            return purchasebillcustomerName;
        }

        public void setPurchasebillcustomerName(String purchasebillcustomerName) {
            this.purchasebillcustomerName = purchasebillcustomerName;
        }

        public String getPurchasebillNo() {
            return purchasebillNo;
        }

        public void setPurchasebillNo(String purchasebillNo) {
            this.purchasebillNo = purchasebillNo;
        }

        public String getPurchasebillDate() {
            return purchasebillDate;
        }

        public void setPurchasebillDate(String purchasebillDate) {
            this.purchasebillDate = purchasebillDate;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public String getPurchasebillDescription() {
            return purchasebillDescription;
        }

        public void setPurchasebillDescription(String purchasebillDescription) {
            this.purchasebillDescription = purchasebillDescription;
        }

        public String getRoundOff() {
            return roundOff;
        }

        public void setRoundOff(String roundOff) {
            this.roundOff = roundOff;
        }

        public String getPaidAmount() {
            return paidAmount;
        }

        public void setPaidAmount(String paidAmount) {
            this.paidAmount = paidAmount;
        }

        public String getBalanceAmount() {
            return balanceAmount;
        }

        public void setBalanceAmount(String balanceAmount) {
            this.balanceAmount = balanceAmount;
        }

        public String getPurchasebillImage() {
            return purchasebillImage;
        }

        public void setPurchasebillImage(String purchasebillImage) {
            this.purchasebillImage = purchasebillImage;
        }
    }
}
