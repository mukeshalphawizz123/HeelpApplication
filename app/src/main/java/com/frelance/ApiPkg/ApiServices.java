package com.frelance.ApiPkg;


import com.frelance.InboxListPkg.msgModlePkg.ChatUserResponseModle;
import com.frelance.SendAdminMsgModle;
import com.frelance.SetLangPkg.SetLangmodel;
import com.frelance.chatPkg.chatModlePkg.ChatEntryModel;
import com.frelance.chatPkg.chatModlePkg.MsgSentModel;
import com.frelance.chatPkg.chatModlePkg.chatResponseModlePkg.ChatImageResponseModle;
import com.frelance.chatPkg.chatModlePkg.voiceRecordingModle.RecordingResponseModle;
import com.frelance.clientProfilePkg.getuserreviewsModulePkg.GetUserReviewsModel;
import com.frelance.detailsPkg.detailModlePkg.MissionViewDetailModle;
import com.frelance.externalModlePkg.ProjectSendDisputeModle;
import com.frelance.forgetpasswordPkg.ForgetPasswordModle;
import com.frelance.homeTablayout.homeModel.ListOfProjectModel;
import com.frelance.homeTablayout.homeModel.RepondreUneDemandeModelPkg.RepondreunedemandeModel;
import com.frelance.homeTablayout.publishPkg.publishModlePkg.PostDemandModle;
import com.frelance.loginInitial.LoginPkgModel.Loginmodel;
import com.frelance.loginInitial.LoginPkgModel.socialLoginPkg.SocialLoginModel;
import com.frelance.makeAnOfferPkg.makeAnOfferModlePkg.MakeOfferDetailModle;
import com.frelance.makeAnOfferPkg.makeAnOfferModlePkg.saveOfferModel.SaveOfferModle;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.GoDisputeModle;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandOngoingPkg.demandProgressModlePkg.DemandOnProgressModle;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsCompletePkg.demandCompleteModlePkg.DemandCompleteModle;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsDeliveryPkg.demandDeliveryModlePkg.AskToModifyResponseModle;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsDeliveryPkg.demandDeliveryModlePkg.DemandDeliveredModle;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsDeliveryPkg.demandDeliveryModlePkg.FetchProjectPriceModel;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsDeliveryPkg.demandDeliveryModlePkg.ReleasePaymenModel;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myDemandsDeliveryPkg.demandDeliveryModlePkg.SubmitReviewModle;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myRequestPublishedPkg.Fragment.proposedModlePkg.DemandInProgressModle;
import com.frelance.myDemandsPkg.MyDemandsOptionsPkg.myRequestPublishedPkg.Fragment.proposedModlePkg.notesModlePkg.AcceptOfferModle;
import com.frelance.myDemandsPkg.myRequestModlePkg.MyDemandeModel;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.completeePkg.myMissionCompleteModlePkg.MissionCompleteModle;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.myMissionPkg.disputeModlePkg.GetAllDiputeResponseModle;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.myMissionPkg.disputeModlePkg.SendDiputeResponseModle;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.InProgressModlePkg.SendProjectProgDetailModle;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.InProgressModlePkg.viewProgressModle.MissionInProgressModle;
import com.frelance.myMissionPkg.MyMissionOptionsPkg.proposePkg.myMissionProposedModlePkg.MyMissionProposedModle;
import com.frelance.myMissionPkg.myMissionModlePkg.MyMissionModel;
import com.frelance.notificationPkg.NotificationCountResponseModle;
import com.frelance.notificationPkg.NotificationModlePkg.NotificationResponseModle;
import com.frelance.notificationPkg.RemoveNotificationCountModle;
import com.frelance.paymentPkg.paymentModlePkg.ProjectAmountResponseModle;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardModlePkg.getProfileModlePkg.GetProfileModle;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardModlePkg.getWalletModlePkg.GetWattetAmountModle;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardModlePkg.updateProfileModlePkg.UpdateProfileModle;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment.transactionModlePkg.transactioOutModlePkg.TransactionOutResponseModle;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.TransactionPkg.Fragment.transactionModlePkg.transactionInModlePkg.TransactionInResponseModle;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.cardModlePkg.addCardDetailPkg.AddCardModel;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.cardModlePkg.deleteCardPkg.DeleteCardModel;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.cardModlePkg.getCardDetailModle.FetchCardModel;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.cardModlePkg.upadateCardpkg.UpdateCardModel;
import com.frelance.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.supportPkg.dashboardsupportModlePkg.Dashboardsupportmodel;
import com.frelance.signUpInitial.RegistrationPkgModel.RegistrationModel;
import com.frelance.stripePaymentPkg.stripModlePkg.PaymentResponseModle;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiServices {

    @FormUrlEncoded
    @POST("Authentication/Login")
    Call<Loginmodel> login(@Field("email") String email,
                           @Field("password") String password,
                           @Field("auth_token") String auth_token);


    @FormUrlEncoded
    @POST("client/getuserslist")
    Call<ChatUserResponseModle> getuserslist(@Field("client_id") String client_id);


    @FormUrlEncoded
    @POST("Authentication/forgot_pass")
    Call<ForgetPasswordModle> forgot_pass(@Field("email") String email);


    @FormUrlEncoded
    @POST("Authentication/NewUser")
    Call<RegistrationModel> signup(@Field("email") String email,
                                   @Field("username") String username,
                                   @Field("password") String password,
                                   @Field("auth_token") String auth_token);

    @GET("Projectlist/ListProject")
    Call<ListOfProjectModel> publisherlist();

/*    @GET("Client/findMissionList")
    Call<RepondreunedemandeModel> repondrelist();*/

    @FormUrlEncoded
    @POST("Client/findMissionListbyid")
    Call<RepondreunedemandeModel> repondrelist(
            @Field("client_id") String client_id,
            @Field("category_id") String category_id
    );


    @FormUrlEncoded
    @POST("Client/adddiscussuser")
    Call<ChatEntryModel> chatEntryApi(
            @Field("sender_id") String sender_id,
            @Field("reciver_id") String reciver_id);


//category_id

    @FormUrlEncoded
    @POST("Client/myMission")
    Call<MyMissionModel> mymission(
            @Field("status") String status,
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("Client/getprojectdispute")
    Call<GetAllDiputeResponseModle> getprojectdispute(
            @Field("user_id") String user_id,
            @Field("mission_id") String mission_id);


    @FormUrlEncoded
    @POST("Client/projectsenddispute")
    Call<SendDiputeResponseModle> projectsenddispute(
            @Field("project_id") String project_id,
            @Field("message") String message,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("Client/getnotificationbytype")
    Call<NotificationResponseModle> getNotification(
            @Field("user_id") String user_id,
            @Field("type_id") String type_id);


    @FormUrlEncoded
    @POST("Client/update_notification_status")
    Call<RemoveNotificationCountModle> update_notification_status(
            @Field("user_id") String user_id,
            @Field("type_id") String type_id);


    @FormUrlEncoded
    @POST("Client/getnotificationcount")
    Call<NotificationCountResponseModle> getnotificationcount(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("Client/myDemandList")
    Call<MyDemandeModel> myrequest(
            @Field("status") String status,
            @Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("Authentication/myprofilebyid")
    Call<GetProfileModle> getMyProfile(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("Client/deletestripecard")
    Call<DeleteCardModel> deletestripecard(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("Client/add_credit_card")
    Call<AddCardModel> add_credit_card(
            @Field("card_no") String card_no,
            @Field("expiry") String expiry,
            @Field("user_id") String user_id,
            @Field("name") String name,
            @Field("email_id") String email_id
    );


    @FormUrlEncoded
    @POST("Client/editstripecard")
    Call<UpdateCardModel> update_credit_card(
            @Field("exp_month") String exp_month,
            @Field("exp_year") String exp_year,
            @Field("user_id") String user_id,
            @Field("name") String name_on_card
    );

  /*  @FormUrlEncoded
    @POST("Client/get_card_details")
    Call<GetCarListModel> getCardList(@Field("user_id") String user_id);*/


    @FormUrlEncoded
    @POST("Client/retrieve_a_card")
    Call<FetchCardModel> retrieve_a_card(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("Client/send_message")
    Call<MsgSentModel> send_message(
            @Field("receiver_id") String receiver_id,
            @Field("message_dta") String message_dta);


    @FormUrlEncoded
    @POST("Client/payment_in")
    Call<TransactionInResponseModle> payment_in(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("Client/get_wallet_balance")
    Call<GetWattetAmountModle> get_wallet_balance(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("Client/payment_out")
    Call<TransactionOutResponseModle> payment_out(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("Client/paymentStrip")
    Call<TransactionOutResponseModle> payApi(
            @Field("client_id") String client_id,
            @Field("user_id") String user_id,
            @Field("user_name") String user_name,
            @Field("amount") String amount,
            @Field("projectaname") String projectaname,
            @Field("token_Id") String token_Id,
            @Field("currency") String currency);


    @FormUrlEncoded
    @POST("Client/myDemandbidbyid")
    Call<DemandInProgressModle> myDemandbidbyid(@Field("demand_id") String status);

    @FormUrlEncoded
    @POST("Authentication/SocialLogin")
    Call<SocialLoginModel> sociallogin(@Field("username") String username,
                                       @Field("email") String email,
                                       @Field("status") String status,
                                       @Field("auth_token") String auth_token);


    @FormUrlEncoded
    @POST("Client/paymentStrip")
    Call<PaymentResponseModle> paymentStrip(@Field("client_id") String client_id,
                                            @Field("user_id") String user_id,
                                            @Field("user_name") String user_name,
                                            @Field("amount") String amount,
                                            @Field("projectaname") String projectaname,
                                            @Field("mission_id") String mission_id,
                                            @Field("token_Id") String token_Id);


    @FormUrlEncoded
    @POST("Client/addstripecard")
    Call<AddCardModel> addStripCard(@Field("user_id") String user_id,
                                    @Field("token_Id") String token_Id,
                                    @Field("username") String username
    );


    @FormUrlEncoded
    @POST("Client/getmissionAmount")
    Call<ProjectAmountResponseModle> getProjectPrize(@Field("mission_id") String mission_id);

    @FormUrlEncoded
    @POST("Client/acceptOffer")
    Call<AcceptOfferModle> acceptOffer(
            @Field("offer_id") String offer_id,
            @Field("user_id") String user_id,
            @Field("mission_id") String mission_id,
            @Field("accept_status") String accept_status);

    @FormUrlEncoded
    @POST("Client/myDemandbidbydate")
    Call<DemandInProgressModle> myDemandbidbydate(@Field("demand_id") String status);


    @FormUrlEncoded
    @POST("Client/myDemandbidbybudget")
    Call<DemandInProgressModle> myDemandbidbybudget(@Field("demand_id") String status);


    @FormUrlEncoded
    @POST("Client/demandPorgress")
    Call<DemandOnProgressModle> demandPorgress(@Field("project_id") String project_id);
    //

    @FormUrlEncoded
    @POST("Client/myMissionbidbyid")
    Call<MyMissionProposedModle> myMissionbidbyProposed(@Field("mission_id") String mission_id);


    @FormUrlEncoded
    @POST("Client/projectComplete")
    Call<MissionCompleteModle> myMissionProjectComplete(@Field("project_id") String project_id);


    @FormUrlEncoded
    @POST("Client/projectDelivered")
    Call<MissionCompleteModle> myMissionProjectDeliery(@Field("project_id") String project_id);


    @FormUrlEncoded
    @POST("Client/demandomplete")
    Call<DemandCompleteModle> demandomplete(@Field("project_id") String project_id);


    @FormUrlEncoded
    @POST("Client/demandDelivered")
    Call<DemandDeliveredModle> demandDelivered(@Field("project_id") String project_id);


    @FormUrlEncoded
    @POST("Client/not_satisfied")
    Call<AskToModifyResponseModle> askToModify(@Field("mission_id") String mission_id);


    @FormUrlEncoded
    @POST("Client/get_amount")
    Call<FetchProjectPriceModel> get_amount(@Field("mission_id") String mission_id);


    @FormUrlEncoded
    @POST("Client/addReviewToUser")
    Call<SubmitReviewModle> addReviewToUser(
            @Field("client_user_id") String client_user_id,
            @Field("rating") String rating,
            @Field("comment") String comment,
            @Field("my_user_id") String my_user_id);


    @FormUrlEncoded
    @POST("Client/amount_released")
    Call<ReleasePaymenModel> amount_released(
            @Field("amount") String amount,
            @Field("user_id") String user_id,
            @Field("mission_id") String mission_id,
            @Field("client_id") String client_id);

    @FormUrlEncoded
    @POST("Client/projectPorgress")
    Call<MissionInProgressModle> projectPorgress(@Field("project_id") String project_id);


    @FormUrlEncoded
    @POST("Client/singleMissionViewDetails")
    Call<MissionViewDetailModle> myMisionViewDetail(@Field("mission_id") String mission_id);


    @Multipart
    @POST("Client/sendProjectPorgress")
    Call<SendProjectProgDetailModle> sendProjectPorgress(@Part MultipartBody.Part project_id,
                                                         @Part MultipartBody.Part user_id,
                                                         @Part MultipartBody.Part your_comments,
                                                         @Part MultipartBody.Part project_status,
                                                         @Part MultipartBody.Part[] project_files,
                                                         @Part MultipartBody.Part[] project_image);

    @Multipart
    @POST("Authentication/editProfile")
    Call<UpdateProfileModle> updateProfile(@Part MultipartBody.Part profile_id,
                                           @Part MultipartBody.Part last_name,
                                           @Part MultipartBody.Part first_name,
                                           @Part MultipartBody.Part email,
                                           @Part MultipartBody.Part username,
                                           @Part MultipartBody.Part password,
                                           @Part MultipartBody.Part dob,
                                           @Part MultipartBody.Part country,
                                           @Part MultipartBody.Part picture_url,
                                           @Part MultipartBody.Part presentation,
                                           @Part MultipartBody.Part level_of_study,
                                           @Part MultipartBody.Part school_college,
                                           @Part MultipartBody.Part skill,
                                           @Part MultipartBody.Part field_of_study,
                                           @Part MultipartBody.Part categroy_of_interest,
                                           @Part MultipartBody.Part account_number,
                                           @Part MultipartBody.Part ifsc_code
    );


    @Multipart
    @POST("Authentication/editProfile")
    Call<UpdateProfileModle> updateProfileWithouPassword(@Part MultipartBody.Part profile_id,
                                                         @Part MultipartBody.Part last_name,
                                                         @Part MultipartBody.Part first_name,
                                                         @Part MultipartBody.Part email,
                                                         @Part MultipartBody.Part username,
                                                         @Part MultipartBody.Part dob,
                                                         @Part MultipartBody.Part country,
                                                         @Part MultipartBody.Part picture_url,
                                                         @Part MultipartBody.Part presentation,
                                                         @Part MultipartBody.Part level_of_study,
                                                         @Part MultipartBody.Part school_college,
                                                         @Part MultipartBody.Part skill,
                                                         @Part MultipartBody.Part field_of_study,
                                                         @Part MultipartBody.Part categroy_of_interest,
                                                         @Part MultipartBody.Part account_number,
                                                         @Part MultipartBody.Part ifsc_code
    );


    @Multipart
    @POST("client/addchatimage")
    Call<ChatImageResponseModle> addchatimage(@Part MultipartBody.Part picture_url);


    @Multipart
    @POST("client/addchatvoicenote")
    Call<RecordingResponseModle> voice_url(@Part MultipartBody.Part voice_url);

    @FormUrlEncoded
    @POST("Client/projectsenddispute")
    Call<ProjectSendDisputeModle> sendDispute(
            @Field("project_id") String project_id,
            @Field("message") String message,
            @Field("user_id") String user_id,
            @Field("date_created") String date_created
    );


    @FormUrlEncoded
    @POST("Client/update_project_status")
    Call<GoDisputeModle> sendDisputeState(
            @Field("project_id") String project_id);


    @FormUrlEncoded
    @POST("Client/projectdefaultsenddispute")
    Call<SendAdminMsgModle> projectdefaultsenddispute(
            @Field("user_id") String user_id,
            @Field("project_id") String project_id,
            @Field("message") String message
    );


    @Multipart
    @POST("Client/post_a_demand")
    Call<PostDemandModle> post_a_demand(@Part MultipartBody.Part category_id,
                                        @Part MultipartBody.Part title,
                                        @Part MultipartBody.Part description,
                                        @Part MultipartBody.Part budget,
                                        @Part MultipartBody.Part client_id,
                                        @Part MultipartBody.Part[] project_image,
                                        @Part MultipartBody.Part[] project_file
    );


    @Multipart
    @POST("Client/post_a_demand")
    Call<PostDemandModle> post_a_demandWithoutImg(@Part MultipartBody.Part category_id,
                                                  @Part MultipartBody.Part title,
                                                  @Part MultipartBody.Part description,
                                                  @Part MultipartBody.Part budget,
                                                  @Part MultipartBody.Part client_id,
                                                  @Part MultipartBody.Part[] project_file
    );


    @Multipart
    @POST("Client/post_a_demand")
    Call<PostDemandModle> post_a_demandWithoutFile(@Part MultipartBody.Part category_id,
                                                   @Part MultipartBody.Part title,
                                                   @Part MultipartBody.Part description,
                                                   @Part MultipartBody.Part budget,
                                                   @Part MultipartBody.Part client_id,
                                                   @Part MultipartBody.Part[] project_image
    );


    @Multipart
    @POST("Client/post_a_demand")
    Call<PostDemandModle> post_a_demandWithoutFileImage(@Part MultipartBody.Part category_id,
                                                        @Part MultipartBody.Part title,
                                                        @Part MultipartBody.Part description,
                                                        @Part MultipartBody.Part budget,
                                                        @Part MultipartBody.Part client_id
    );


    /*@Part MultipartBody.Part project_file[]*/

    @FormUrlEncoded
    @POST("Offer/getofferid")
    Call<MakeOfferDetailModle> getofferid(@Field("mission_id") String mission_id);

    @FormUrlEncoded
    @POST("Offer/makeAnOffer")
    Call<SaveOfferModle> makeAnOffer(
            @Field("project_id") String project_id,
            @Field("user_id") String user_id,
            @Field("message") String message,
            @Field("accept_budget") String accept_budget,
            @Field("offer_budget") String offer_budget);


    @FormUrlEncoded
    @POST("Contact/Enquiry")
    Call<Dashboardsupportmodel> enquiry(@Field("title") String title,
                                        @Field("user_id") String user_id,
                                        @Field("description") String description
    );

    @FormUrlEncoded
    @POST("Language/setLanguage")
    Call<SetLangmodel> setlang(@Field("language_key") String language_key);

    @FormUrlEncoded
    @POST("Client/getUserReviews")
    Call<GetUserReviewsModel> GetUserReviewsModel(@Field("user_id") String user_id);

}
