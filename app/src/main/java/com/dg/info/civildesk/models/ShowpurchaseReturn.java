package com.dg.info.civildesk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowpurchaseReturn {
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

        @SerializedName("purchasereturnitem_id")
        @Expose
        private String purchasereturnitemId;
        @SerializedName("purchasereturn_id")
        @Expose
        private String purchasereturnId;
        @SerializedName("item_name")
        @Expose
        private String itemName;
        @SerializedName("purchasereturnitem_qty")
        @Expose
        private String purchasereturnitemQty;
        @SerializedName("purchasereturnitem_unit")
        @Expose
        private String purchasereturnitemUnit;
        @SerializedName("purchasereturnitem_price")
        @Expose
        private String purchasereturnitemPrice;
        @SerializedName("purchasereturnitem_taxes")
        @Expose
        private String purchasereturnitemTaxes;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("party_id")
        @Expose
        private String partyId;
        @SerializedName("purchasereturncustomer_name")
        @Expose
        private String purchasereturncustomerName;
        @SerializedName("purchasereturn_no")
        @Expose
        private String purchasereturnNo;
        @SerializedName("purchasereturnbill_no")
        @Expose
        private String purchasereturnbillNo;
        @SerializedName("purchasereturn_date")
        @Expose
        private String purchasereturnDate;
        @SerializedName("purchasereturnbill_date")
        @Expose
        private String purchasereturnbillDate;
        @SerializedName("purchasereturnpayment_type")
        @Expose
        private String purchasereturnpaymentType;
        @SerializedName("purchasereturnref_no")
        @Expose
        private Object purchasereturnrefNo;
        @SerializedName("purchasereturn_description")
        @Expose
        private String purchasereturnDescription;
        @SerializedName("purchasereturnround_off")
        @Expose
        private String purchasereturnroundOff;
        @SerializedName("purchasereturnreceived_amount")
        @Expose
        private String purchasereturnreceivedAmount;
        @SerializedName("purchasereturnbalance_amount")
        @Expose
        private String purchasereturnbalanceAmount;
        @SerializedName("purchasereturn_image")
        @Expose
        private String purchasereturnImage;

        public String getPurchasereturnitemId() {
            return purchasereturnitemId;
        }

        public void setPurchasereturnitemId(String purchasereturnitemId) {
            this.purchasereturnitemId = purchasereturnitemId;
        }

        public String getPurchasereturnId() {
            return purchasereturnId;
        }

        public void setPurchasereturnId(String purchasereturnId) {
            this.purchasereturnId = purchasereturnId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getPurchasereturnitemQty() {
            return purchasereturnitemQty;
        }

        public void setPurchasereturnitemQty(String purchasereturnitemQty) {
            this.purchasereturnitemQty = purchasereturnitemQty;
        }

        public String getPurchasereturnitemUnit() {
            return purchasereturnitemUnit;
        }

        public void setPurchasereturnitemUnit(String purchasereturnitemUnit) {
            this.purchasereturnitemUnit = purchasereturnitemUnit;
        }

        public String getPurchasereturnitemPrice() {
            return purchasereturnitemPrice;
        }

        public void setPurchasereturnitemPrice(String purchasereturnitemPrice) {
            this.purchasereturnitemPrice = purchasereturnitemPrice;
        }

        public String getPurchasereturnitemTaxes() {
            return purchasereturnitemTaxes;
        }

        public void setPurchasereturnitemTaxes(String purchasereturnitemTaxes) {
            this.purchasereturnitemTaxes = purchasereturnitemTaxes;
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

        public String getPurchasereturncustomerName() {
            return purchasereturncustomerName;
        }

        public void setPurchasereturncustomerName(String purchasereturncustomerName) {
            this.purchasereturncustomerName = purchasereturncustomerName;
        }

        public String getPurchasereturnNo() {
            return purchasereturnNo;
        }

        public void setPurchasereturnNo(String purchasereturnNo) {
            this.purchasereturnNo = purchasereturnNo;
        }

        public String getPurchasereturnbillNo() {
            return purchasereturnbillNo;
        }

        public void setPurchasereturnbillNo(String purchasereturnbillNo) {
            this.purchasereturnbillNo = purchasereturnbillNo;
        }

        public String getPurchasereturnDate() {
            return purchasereturnDate;
        }

        public void setPurchasereturnDate(String purchasereturnDate) {
            this.purchasereturnDate = purchasereturnDate;
        }

        public String getPurchasereturnbillDate() {
            return purchasereturnbillDate;
        }

        public void setPurchasereturnbillDate(String purchasereturnbillDate) {
            this.purchasereturnbillDate = purchasereturnbillDate;
        }

        public String getPurchasereturnpaymentType() {
            return purchasereturnpaymentType;
        }

        public void setPurchasereturnpaymentType(String purchasereturnpaymentType) {
            this.purchasereturnpaymentType = purchasereturnpaymentType;
        }

        public Object getPurchasereturnrefNo() {
            return purchasereturnrefNo;
        }

        public void setPurchasereturnrefNo(Object purchasereturnrefNo) {
            this.purchasereturnrefNo = purchasereturnrefNo;
        }

        public String getPurchasereturnDescription() {
            return purchasereturnDescription;
        }

        public void setPurchasereturnDescription(String purchasereturnDescription) {
            this.purchasereturnDescription = purchasereturnDescription;
        }

        public String getPurchasereturnroundOff() {
            return purchasereturnroundOff;
        }

        public void setPurchasereturnroundOff(String purchasereturnroundOff) {
            this.purchasereturnroundOff = purchasereturnroundOff;
        }

        public String getPurchasereturnreceivedAmount() {
            return purchasereturnreceivedAmount;
        }

        public void setPurchasereturnreceivedAmount(String purchasereturnreceivedAmount) {
            this.purchasereturnreceivedAmount = purchasereturnreceivedAmount;
        }

        public String getPurchasereturnbalanceAmount() {
            return purchasereturnbalanceAmount;
        }

        public void setPurchasereturnbalanceAmount(String purchasereturnbalanceAmount) {
            this.purchasereturnbalanceAmount = purchasereturnbalanceAmount;
        }

        public String getPurchasereturnImage() {
            return purchasereturnImage;
        }

        public void setPurchasereturnImage(String purchasereturnImage) {
            this.purchasereturnImage = purchasereturnImage;
        }

    }
}
