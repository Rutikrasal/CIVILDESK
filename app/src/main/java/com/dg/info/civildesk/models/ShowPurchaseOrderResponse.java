package com.dg.info.civildesk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowPurchaseOrderResponse {
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

        @SerializedName("purchaseorderitem_id")
        @Expose
        private String purchaseorderitemId;
        @SerializedName("purchaseorder_id")
        @Expose
        private String purchaseorderId;
        @SerializedName("item_name")
        @Expose
        private String itemName;
        @SerializedName("purchaseorderitem_qty")
        @Expose
        private String purchaseorderitemQty;
        @SerializedName("purchaseorderitem_unit")
        @Expose
        private String purchaseorderitemUnit;
        @SerializedName("purchaseorderitem_price")
        @Expose
        private String purchaseorderitemPrice;
        @SerializedName("purchaseorderitem_taxes")
        @Expose
        private String purchaseorderitemTaxes;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("party_id")
        @Expose
        private String partyId;
        @SerializedName("purchaseordercustomer_name")
        @Expose
        private String purchaseordercustomerName;
        @SerializedName("purchaseorder_no")
        @Expose
        private String purchaseorderNo;
        @SerializedName("purchaseorder_date")
        @Expose
        private String purchaseorderDate;
        @SerializedName("purchaseorder_duedate")
        @Expose
        private String purchaseorderDuedate;
        @SerializedName("purchaseorderpayment_type")
        @Expose
        private String purchaseorderpaymentType;
        @SerializedName("purchaseorderref_no")
        @Expose
        private Object purchaseorderrefNo;
        @SerializedName("purchaseorder_description")
        @Expose
        private String purchaseorderDescription;
        @SerializedName("purchaseorderround_off")
        @Expose
        private String purchaseorderroundOff;
        @SerializedName("purchaseorderpaid_amount")
        @Expose
        private String purchaseorderpaidAmount;
        @SerializedName("purchaseorderbalance_amount")
        @Expose
        private String purchaseorderbalanceAmount;
        @SerializedName("purchaseorder_image")
        @Expose
        private String purchaseorderImage;

        public String getPurchaseorderitemId() {
            return purchaseorderitemId;
        }

        public void setPurchaseorderitemId(String purchaseorderitemId) {
            this.purchaseorderitemId = purchaseorderitemId;
        }

        public String getPurchaseorderId() {
            return purchaseorderId;
        }

        public void setPurchaseorderId(String purchaseorderId) {
            this.purchaseorderId = purchaseorderId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getPurchaseorderitemQty() {
            return purchaseorderitemQty;
        }

        public void setPurchaseorderitemQty(String purchaseorderitemQty) {
            this.purchaseorderitemQty = purchaseorderitemQty;
        }

        public String getPurchaseorderitemUnit() {
            return purchaseorderitemUnit;
        }

        public void setPurchaseorderitemUnit(String purchaseorderitemUnit) {
            this.purchaseorderitemUnit = purchaseorderitemUnit;
        }

        public String getPurchaseorderitemPrice() {
            return purchaseorderitemPrice;
        }

        public void setPurchaseorderitemPrice(String purchaseorderitemPrice) {
            this.purchaseorderitemPrice = purchaseorderitemPrice;
        }

        public String getPurchaseorderitemTaxes() {
            return purchaseorderitemTaxes;
        }

        public void setPurchaseorderitemTaxes(String purchaseorderitemTaxes) {
            this.purchaseorderitemTaxes = purchaseorderitemTaxes;
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

        public String getPurchaseordercustomerName() {
            return purchaseordercustomerName;
        }

        public void setPurchaseordercustomerName(String purchaseordercustomerName) {
            this.purchaseordercustomerName = purchaseordercustomerName;
        }

        public String getPurchaseorderNo() {
            return purchaseorderNo;
        }

        public void setPurchaseorderNo(String purchaseorderNo) {
            this.purchaseorderNo = purchaseorderNo;
        }

        public String getPurchaseorderDate() {
            return purchaseorderDate;
        }

        public void setPurchaseorderDate(String purchaseorderDate) {
            this.purchaseorderDate = purchaseorderDate;
        }

        public String getPurchaseorderDuedate() {
            return purchaseorderDuedate;
        }

        public void setPurchaseorderDuedate(String purchaseorderDuedate) {
            this.purchaseorderDuedate = purchaseorderDuedate;
        }

        public String getPurchaseorderpaymentType() {
            return purchaseorderpaymentType;
        }

        public void setPurchaseorderpaymentType(String purchaseorderpaymentType) {
            this.purchaseorderpaymentType = purchaseorderpaymentType;
        }

        public Object getPurchaseorderrefNo() {
            return purchaseorderrefNo;
        }

        public void setPurchaseorderrefNo(Object purchaseorderrefNo) {
            this.purchaseorderrefNo = purchaseorderrefNo;
        }

        public String getPurchaseorderDescription() {
            return purchaseorderDescription;
        }

        public void setPurchaseorderDescription(String purchaseorderDescription) {
            this.purchaseorderDescription = purchaseorderDescription;
        }

        public String getPurchaseorderroundOff() {
            return purchaseorderroundOff;
        }

        public void setPurchaseorderroundOff(String purchaseorderroundOff) {
            this.purchaseorderroundOff = purchaseorderroundOff;
        }

        public String getPurchaseorderpaidAmount() {
            return purchaseorderpaidAmount;
        }

        public void setPurchaseorderpaidAmount(String purchaseorderpaidAmount) {
            this.purchaseorderpaidAmount = purchaseorderpaidAmount;
        }

        public String getPurchaseorderbalanceAmount() {
            return purchaseorderbalanceAmount;
        }

        public void setPurchaseorderbalanceAmount(String purchaseorderbalanceAmount) {
            this.purchaseorderbalanceAmount = purchaseorderbalanceAmount;
        }

        public String getPurchaseorderImage() {
            return purchaseorderImage;
        }

        public void setPurchaseorderImage(String purchaseorderImage) {
            this.purchaseorderImage = purchaseorderImage;
        }

    }
}
