package com.dg.info.civildesk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemResponse {
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

        @SerializedName("item_id")
        @Expose
        private String itemId;
        @SerializedName("item_name")
        @Expose
        private String itemName;
        @SerializedName("item_unit")
        @Expose
        private String itemUnit;
        @SerializedName("taxes")
        @Expose
        private String taxes;
        @SerializedName("item_hsn")
        @Expose
        private String itemHsn;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemUnit() {
            return itemUnit;
        }

        public void setItemUnit(String itemUnit) {
            this.itemUnit = itemUnit;
        }

        public String getTaxes() {
            return taxes;
        }

        public void setTaxes(String taxes) {
            this.taxes = taxes;
        }

        public String getItemHsn() {
            return itemHsn;
        }

        public void setItemHsn(String itemHsn) {
            this.itemHsn = itemHsn;
        }

    }}
