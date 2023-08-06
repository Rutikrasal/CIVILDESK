package com.dg.info.civildesk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PartyResponse {
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

        @SerializedName("party_id")
        @Expose
        private String partyId;
        @SerializedName("party_name")
        @Expose
        private String partyName;
        @SerializedName("gst_no")
        @Expose
        private String gstNo;
        @SerializedName("phone_no")
        @Expose
        private String phoneNo;
        @SerializedName("party_address")
        @Expose
        private String partyAddress;
        @SerializedName("state_code")
        @Expose
        private Object stateCode;
        @SerializedName("state")
        @Expose
        private Object state;
        @SerializedName("dateAdded")
        @Expose
        private String dateAdded;

        public String getPartyId() {
            return partyId;
        }

        public void setPartyId(String partyId) {
            this.partyId = partyId;
        }

        public String getPartyName() {
            return partyName;
        }

        public void setPartyName(String partyName) {
            this.partyName = partyName;
        }

        public String getGstNo() {
            return gstNo;
        }

        public void setGstNo(String gstNo) {
            this.gstNo = gstNo;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getPartyAddress() {
            return partyAddress;
        }

        public void setPartyAddress(String partyAddress) {
            this.partyAddress = partyAddress;
        }

        public Object getStateCode() {
            return stateCode;
        }

        public void setStateCode(Object stateCode) {
            this.stateCode = stateCode;
        }

        public Object getState() {
            return state;
        }

        public void setState(Object state) {
            this.state = state;
        }

        public String getDateAdded() {
            return dateAdded;
        }

        public void setDateAdded(String dateAdded) {
            this.dateAdded = dateAdded;
        }

    }
}
