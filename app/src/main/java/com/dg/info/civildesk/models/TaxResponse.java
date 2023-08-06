package com.dg.info.civildesk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaxResponse {
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

        @SerializedName("tax_id")
        @Expose
        private String taxId;
        @SerializedName("taxname")
        @Expose
        private String taxname;
        @SerializedName("taxpercentage")
        @Expose
        private String taxpercentage;

        public String getTaxId() {
            return taxId;
        }

        public void setTaxId(String taxId) {
            this.taxId = taxId;
        }

        public String getTaxname() {
            return taxname;
        }

        public void setTaxname(String taxname) {
            this.taxname = taxname;
        }

        public String getTaxpercentage() {
            return taxpercentage;
        }

        public void setTaxpercentage(String taxpercentage) {
            this.taxpercentage = taxpercentage;
        }

    }
}
