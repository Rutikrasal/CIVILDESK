package com.dg.info.civildesk.api;


import com.dg.info.civildesk.models.AddItemResponse;
import com.dg.info.civildesk.models.AddLeavesResponse;
import com.dg.info.civildesk.models.AddPaymentOutResponse;
import com.dg.info.civildesk.models.AddPurchaseOrderResponse;
import com.dg.info.civildesk.models.AddPurchaseReturnResponse;
import com.dg.info.civildesk.models.AddPurchasebillDataRequest;
import com.dg.info.civildesk.models.AddPurchasebillResponse;
import com.dg.info.civildesk.models.AddSiteResponse;
import com.dg.info.civildesk.models.AddVendorResponse;
import com.dg.info.civildesk.models.CustomerBalanceResponse;
import com.dg.info.civildesk.models.CustomerDetailsResponse;
import com.dg.info.civildesk.models.CustomerResponse;
import com.dg.info.civildesk.models.ItemAdd;
import com.dg.info.civildesk.models.ItemResponse;
import com.dg.info.civildesk.models.LoginResponse;
import com.dg.info.civildesk.models.PartyResponse;
import com.dg.info.civildesk.models.PdfResponse;
import com.dg.info.civildesk.models.PurchaseBillDeleteResponse;
import com.dg.info.civildesk.models.ShowPaymentOutResponse;
import com.dg.info.civildesk.models.ShowPurchaseOrderResponse;
import com.dg.info.civildesk.models.ShowPurchasebillResponse;
import com.dg.info.civildesk.models.ShowpurchaseReturn;
import com.dg.info.civildesk.models.TaxResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    //Login API
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(@Field("mobile_no") String mobile_no, @Field("password") String password ,@Field("imei_no") String imei_no);

    //Add Item API
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("add_item.php")
    Call<AddItemResponse> additem(@Field("item_name") String item_name , @Field("item_unit") String item_unit, @Field("taxes") String taxes, @Field("item_hsn") String item_hsn);

    //Add Site API
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("add_party.php")
    Call<AddSiteResponse> addsite(@Field("party_name") String party_name , @Field("gst_no") String gst_no, @Field("phone_no") String phone_no, @Field("party_address") String party_address, @Field("state_code") String state_code, @Field("state") String state);

    //Add Vendor API
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("add_customer.php")
    Call<AddVendorResponse> addvendor(@Field("customer_name") String customer_name , @Field("customer_gstno") String customer_gstno, @Field("customer_address") String customer_address, @Field("customer_mobileno") String customer_mobileno, @Field("customer_panno") String customer_panno, @Field("state_code") String state_code, @Field("state") String state);

    //Get Customer API
    @GET("get_customer.php")
    Call<CustomerResponse> getcustomer();


    //Get Party API
    @GET("get_party.php")
    Call<PartyResponse> getparty();

    //Get Item API
    @GET("get_item.php")
    Call<ItemResponse> get_item();

    //Get Item API
    @GET("get_taxes.php")
    Call<TaxResponse> get_tax();


    //Add Purchase Bill API
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("add_purchasebill.php")
    Call<AddPurchasebillResponse> addpurchasebill(@Field("user_id") String user_id,@Field("party_id") String party_id,@Field("bill_no") String bill_no,
                                                  @Field("date") String date,@Field("customer_name") String customer_name,@Field("mobile_no") String mobile_no,
                                                  @Field("total_amount") String total_amount,@Field("paid_amount") String paid_amount,@Field("balance_due") String balance_due,
                                                  @Field("payment_type") String payment_type,@Field("payment_refno") String payment_refno,@Field("state") String state,@Field("round_off") String round_off,
                                                  @Field("description") String description,@Field("image") String image,@Field("item_id") ArrayList<String> item_id,@Field("item_name") ArrayList<String> item_name,
                                                  @Field("item_taxname") ArrayList<String> item_taxname,@Field("item_taxpercent") ArrayList<String> item_taxpercent,@Field("item_discountpercent") ArrayList<String> item_discountpercent,
                                                  @Field("item_unit") ArrayList<String> item_unit,@Field("item_qty") ArrayList<String> item_qty,@Field("item_price") ArrayList<String> item_price,@Field("item_subtotal") ArrayList<String> item_subtotal
                                                    ,@Field("item_discount") ArrayList<String> item_discount,@Field("item_taxes") ArrayList<String> item_taxes,@Field("item_total") ArrayList<String> item_total);

    //Get Puchase Bill API
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("get_purchasebill.php")
    Call<ShowPurchasebillResponse> showpurchasebill(@Field("user_id") String user_id);


    //Get Payment Out API
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("get_paymentout.php")
    Call<ShowPaymentOutResponse> showpaymentout(@Field("user_id") String user_id);

    //Get Purchase Return API
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("get_purchasereturn.php")
    Call<ShowpurchaseReturn> showpurchasereturn(@Field("user_id") String user_id);

    //Get Purchase Order API
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("get_purchaseorder.php")
    Call<ShowPurchaseOrderResponse> showpurchaseorder(@Field("user_id") String user_id);


    //Get PDG API
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("getpdf.php")
    Call<PdfResponse> getpdf(@Field("id") String id);

    //Get PDG API
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("get_customerbalance.php")
    Call<CustomerBalanceResponse> getbalance(@Field("id") String id);

    //Purchase Bill Delete
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("delete_purchasebill.php")
    Call<PurchaseBillDeleteResponse> deletebill(@Field("id") String id);

    //Add Payment-Out API
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("add_paymentout.php")
    Call<AddPaymentOutResponse> Addpaymentout(@Field("user_id") String user_id,@Field("bill_id") String bill_id,@Field("party_id") String party_id,@Field("receipt_no") String receipt_no,@Field("paymentout_date") String paymentout_date,@Field("paymentout_customername") String paymentout_customername,@Field("phone_no") String phone_no,
                                              @Field("balance_amount") String balance_amount,@Field("unused_amount") String unused_amount,@Field("paymentout_amount") String paymentout_amount,@Field("total_amount") String totalamount,@Field("paymentout_type") String paymentout_type,@Field("ref_no") String ref_no,@Field("paymentout_description") String paymentout_description,@Field("image") String image );

    //Add Purchasereturn API
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("add_purchasereturn.php")
    Call<AddPurchaseReturnResponse> Addpurchasereturn(@Field("user_id") String user_id,@Field("party_id") String party_id,@Field("purchasereturn_no") String purchasereturn_no,@Field("purchasereturn_date") String purchasereturn_date,@Field("customer_name") String customer_name,@Field("mobile_no") String mobile_no,@Field("purchasereturnbill_no") String purchasereturnbill_no
            ,@Field("purchasereturnbill_date") String purchasereturnbill_date,@Field("total_amount") String total_amount,@Field("purchasereturnreceived_amount") String purchasereturnreceived_amount
            ,@Field("balance_due") String balance_due,@Field("purchasereturnpayment_type") String purchasereturnpayment_type,@Field("payment_refno") String payment_refno,@Field("state") String state,@Field("purchasereturnround_off") String purchasereturnround_off
            ,@Field("purchasereturn_description") String purchasereturn_description,@Field("image") String image,@Field("item_id") ArrayList<String> item_id,@Field("item_name") ArrayList<String> item_name,
                                                      @Field("item_taxname") ArrayList<String> item_taxname,@Field("item_taxpercent") ArrayList<String> item_taxpercent,@Field("item_discountpercent") ArrayList<String> item_discountpercent,
                                                      @Field("item_unit") ArrayList<String> item_unit,@Field("item_qty") ArrayList<String> item_qty,@Field("item_price") ArrayList<String> item_price,@Field("item_subtotal") ArrayList<String> item_subtotal
            ,@Field("item_discount") ArrayList<String> item_discount,@Field("item_taxes") ArrayList<String> item_taxes,@Field("item_total") ArrayList<String> item_total);

    //Add Purchaseorder API
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("add_purchaseorder.php")
    Call<AddPurchaseOrderResponse> Addpurchaseorder(@Field("user_id") String user_id,@Field("party_id") String party_id,@Field("purchaseorder_no") String purchaseorder_no,@Field("purchaseorder_date") String purchaseorder_date,@Field("customer_name") String customer_name,@Field("purchaseorder_duedate") String purchaseorder_duedate,@Field("total_amount") String total_amount,@Field("purchaseorderpaid_amount") String purchaseorderpaid_amount,
                                                    @Field("balance_due") String balance_due,@Field("purchaseorderpayment_type") String purchaseorderpayment_type,@Field("paymenttype_no") String paymenttype_no,@Field("state") String state,@Field("purchaseorderround_off") String purchaseorderround_off,@Field("purchaseorder_description") String purchaseorder_description,@Field("image") String image,@Field("item_id") ArrayList<String> item_id,@Field("item_name") ArrayList<String> item_name,
                                                    @Field("item_taxname") ArrayList<String> item_taxname,@Field("item_taxpercent") ArrayList<String> item_taxpercent,@Field("item_discountpercent") ArrayList<String> item_discountpercent,
                                                    @Field("item_unit") ArrayList<String> item_unit,@Field("item_qty") ArrayList<String> item_qty,@Field("item_price") ArrayList<String> item_price,@Field("item_subtotal") ArrayList<String> item_subtotal
            ,@Field("item_discount") ArrayList<String> item_discount,@Field("item_taxes") ArrayList<String> item_taxes,@Field("item_total") ArrayList<String> item_total);


    //Get Customer Details
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("get_customerdetail.php")
    Call<CustomerDetailsResponse> getcustomerdetails(@Field("id") String id);

    //Get Customer Details
    @Headers("X-API-KEY: laappbyv")
    @FormUrlEncoded
    @POST("add_leaves.php")
    Call<AddLeavesResponse> Addleave(@Field("user_id") String user_id,@Field("leaves_date") String leaves_date,@Field("leavesend_date") String leavesend_date,@Field("leaves_type") String leaves_type,@Field("leaves_reason") String leaves_reason,@Field("image") String image);


}
