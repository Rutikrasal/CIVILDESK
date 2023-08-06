package com.dg.info.civildesk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CustomerResponse implements Serializable {
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
    public class Datum implements Serializable{

        @SerializedName("customer_id")
        @Expose
        private String customerId;
        @SerializedName("customer_name")
        @Expose
        private String customerName;
        @SerializedName("customer_gstno")
        @Expose
        private String customerGstno;
        @SerializedName("customer_address")
        @Expose
        private String customerAddress;
        @SerializedName("customer_mobileno")
        @Expose
        private String customerMobileno;
        @SerializedName("state_code")
        @Expose
        private String stateCode;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("customer_panno")
        @Expose
        private String customerPanno;

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerGstno() {
            return customerGstno;
        }

        public void setCustomerGstno(String customerGstno) {
            this.customerGstno = customerGstno;
        }

        public String getCustomerAddress() {
            return customerAddress;
        }

        public void setCustomerAddress(String customerAddress) {
            this.customerAddress = customerAddress;
        }

        public String getCustomerMobileno() {
            return customerMobileno;
        }

        public void setCustomerMobileno(String customerMobileno) {
            this.customerMobileno = customerMobileno;
        }

        public String getStateCode() {
            return stateCode;
        }

        public void setStateCode(String stateCode) {
            this.stateCode = stateCode;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCustomerPanno() {
            return customerPanno;
        }

        public void setCustomerPanno(String customerPanno) {
            this.customerPanno = customerPanno;
        }

    }
}
