package com.freelanceapp.ApiPkg;


import com.freelanceapp.SetLangPkg.SetLangmodel;
import com.freelanceapp.detailsPkg.detailModlePkg.MissionViewDetailModle;
import com.freelanceapp.externalModlePkg.ProjectSendDisputeModle;
import com.freelanceapp.forgetpasswordPkg.ForgetPasswordModle;
import com.freelanceapp.homeTablayout.homeModel.ListOfProjectModel;
import com.freelanceapp.homeTablayout.homeModel.RepondreUneDemandeModelPkg.RepondreunedemandeModel;
import com.freelanceapp.homeTablayout.publishPkg.publishModlePkg.PostDemandModle;
import com.freelanceapp.loginInitial.LoginPkgModel.Loginmodel;
import com.freelanceapp.loginInitial.LoginPkgModel.socialLoginPkg.SocialLoginModel;
import com.freelanceapp.makeAnOfferPkg.makeAnOfferModlePkg.MakeOfferDetailModle;
import com.freelanceapp.makeAnOfferPkg.makeAnOfferModlePkg.saveOfferModel.SaveOfferModle;
import com.freelanceapp.myDemandsPkg.MyRequestOptionsPkg.myRequestPublishedPkg.Fragment.proposedModlePkg.notesModlePkg.AcceptOfferModle;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.completeePkg.myMissionCompleteModlePkg.MissionCompleteModle;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.InProgressModlePkg.SendProjectProgDetailModle;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.ongoingPkg.InProgressModlePkg.viewProgressModle.MissionInProgressModle;
import com.freelanceapp.myMissionPkg.MyMissionOptionsPkg.proposePkg.myMissionProposedModlePkg.MyMissionProposedModle;
import com.freelanceapp.myMissionPkg.myMissionModlePkg.MyMissionModel;
import com.freelanceapp.myDemandsPkg.MyRequestOptionsPkg.myDemandOngoingPkg.demandProgressModlePkg.DemandOnProgressModle;
import com.freelanceapp.myDemandsPkg.MyRequestOptionsPkg.myDemandsCompletePkg.demandCompleteModlePkg.DemandCompleteModle;
import com.freelanceapp.myDemandsPkg.MyRequestOptionsPkg.myDemandsLiveryPkg.demandDeliveryModlePkg.DemandDeliveredModle;
import com.freelanceapp.myDemandsPkg.MyRequestOptionsPkg.myDemandsLiveryPkg.demandDeliveryModlePkg.SubmitReviewModle;
import com.freelanceapp.myDemandsPkg.MyRequestOptionsPkg.myRequestPublishedPkg.Fragment.proposedModlePkg.DemandInProgressModle;
import com.freelanceapp.myDemandsPkg.myRequestModlePkg.MyDemandeModel;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardModlePkg.getProfileModlePkg.GetProfileModle;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardModlePkg.updateProfileModlePkg.UpdateProfileModle;
import com.freelanceapp.plusMorePkg.DashboardProfileOptionsPkg.DashboardPaymentOptionsPkg.supportPkg.dashboardsupportModlePkg.Dashboardsupportmodel;
import com.freelanceapp.signUpInitial.RegistrationPkgModel.RegistrationModel;
import com.freelanceapp.userProfileRatingPkg.getuserreviewsModulePkg.GetUserReviewsModel;

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
                           @Field("password") String password);


    @FormUrlEncoded
    @POST("Authentication/forgot_pass")
    Call<ForgetPasswordModle> forgot_pass(@Field("email") String email);


    @FormUrlEncoded
    @POST("Authentication/NewUser")
    Call<RegistrationModel> signup(@Field("email") String email,
                                   @Field("username") String username,
                                   @Field("password") String password);

    @GET("Projectlist/ListProject")
    Call<ListOfProjectModel> publisherlist();

    @GET("Client/findMissionList")
    Call<RepondreunedemandeModel> repondrelist();

    @FormUrlEncoded
    @POST("Client/myMission")
    Call<MyMissionModel> mymission(@Field("status") String status);

    @FormUrlEncoded
    @POST("Client/myDemandList")
    Call<MyDemandeModel> myrequest(@Field("status") String status);


    @FormUrlEncoded
    @POST("Authentication/myprofilebyid")
    Call<GetProfileModle> getMyProfile(@Field("user_id") String user_id);


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
    @POST("Client/addReviewToUser")
    Call<SubmitReviewModle> addReviewToUser(
            @Field("client_user_id") String client_user_id,
            @Field("rating") String rating,
            @Field("reveiw_msg") String reveiw_msg,
            @Field("my_user_id") String my_user_id);


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
                                                         @Part MultipartBody.Part project_files,
                                                         @Part MultipartBody.Part project_image);

    @Multipart
    @POST("Authentication/editProfile")
    Call<UpdateProfileModle> updateProfile(@Part MultipartBody.Part profile_id,
                                           @Part MultipartBody.Part name,
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
                                           @Part MultipartBody.Part categroy_of_interest);


    @FormUrlEncoded
    @POST("Client/projectsenddispute")
    Call<ProjectSendDisputeModle> sendDispute(
            @Field("project_id") String project_id,
            @Field("message") String message,
            @Field("date_created") String date_created
    );


    @Multipart
    @POST("Client/post_a_demand")
    Call<PostDemandModle> post_a_demand(@Part MultipartBody.Part category_id,
                                        @Part MultipartBody.Part title,
                                        @Part MultipartBody.Part description,
                                        @Part MultipartBody.Part budget,
                                        @Part MultipartBody.Part client_id,
                                        @Part MultipartBody.Part project_image);

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
