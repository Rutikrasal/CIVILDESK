package com.dg.info.civildesk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowPaymentOutResponse {
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

        @SerializedName("paymentout_id")
        @Expose
        private String paymentoutId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("party_id")
        @Expose
        private String partyId;
        @SerializedName("paymentout_customername")
        @Expose
        private String paymentoutCustomername;
        @SerializedName("ref_no")
        @Expose
        private String refNo;
        @SerializedName("paymentout_date")
        @Expose
        private String paymentoutDate;
        @SerializedName("paymentout_type")
        @Expose
        private String paymentoutType;
        @SerializedName("paymenttype_no")
        @Expose
        private String paymenttypeNo;
        @SerializedName("paymentout_amount")
        @Expose
        private String paymentoutAmount;
        @SerializedName("paymentout_description")
        @Expose
        private String paymentoutDescription;
        @SerializedName("paymentout_image")
        @Expose
        private String paymentoutImage;

        public String getPaymentoutId() {
            return paymentoutId;
        }

        public void setPaymentoutId(String paymentoutId) {
            this.paymentoutId = paymentoutId;
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

        public String getPaymentoutCustomername() {
            return paymentoutCustomername;
        }

        public void setPaymentoutCustomername(String paymentoutCustomername) {
            this.paymentoutCustomername = paymentoutCustomername;
        }

        public String getRefNo() {
            return refNo;
        }

        public void setRefNo(String refNo) {
            this.refNo = refNo;
        }

        public String getPaymentoutDate() {
            return paymentoutDate;
        }

        public void setPaymentoutDate(String paymentoutDate) {
            this.paymentoutDate = paymentoutDate;
        }

        public String getPaymentoutType() {
            return paymentoutType;
        }

        public void setPaymentoutType(String paymentoutType) {
            this.paymentoutType = paymentoutType;
        }

        public String getPaymenttypeNo() {
            return paymenttypeNo;
        }

        public void setPaymenttypeNo(String paymenttypeNo) {
            this.paymenttypeNo = paymenttypeNo;
        }

        public String getPaymentoutAmount() {
            return paymentoutAmount;
        }

        public void setPaymentoutAmount(String paymentoutAmount) {
            this.paymentoutAmount = paymentoutAmount;
        }

        public String getPaymentoutDescription() {
            return paymentoutDescription;
        }

        public void setPaymentoutDescription(String paymentoutDescription) {
            this.paymentoutDescription = paymentoutDescription;
        }

        public String getPaymentoutImage() {
            return paymentoutImage;
        }

        public void setPaymentoutImage(String paymentoutImage) {
            this.paymentoutImage = paymentoutImage;
        }

    }
}
