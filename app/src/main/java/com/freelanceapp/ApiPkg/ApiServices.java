package com.freelanceapp.ApiPkg;


import com.freelanceapp.SetLangPkg.SetLangmodel;
import com.freelanceapp.homeTablayout.homeModel.ListOfProjectModel;
import com.freelanceapp.homeTablayout.homeModel.RepondreUneDemandeModelPkg.RepondreunedemandeModel;
import com.freelanceapp.homeTablayout.publishPkg.publishModlePkg.PostDemandModle;
import com.freelanceapp.loginInitial.LoginPkgModel.Loginmodel;
import com.freelanceapp.makeAnOfferPkg.makeAnOfferModlePkg.MakeOfferDetailModle;
import com.freelanceapp.makeAnOfferPkg.makeAnOfferModlePkg.saveOfferModel.SaveOfferModle;
import com.freelanceapp.myMissionPkg.myMissionModlePkg.MyMissionModel;
import com.freelanceapp.myRequestPkg.myRequestModlePkg.MyDemandeModel;
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

/*    @Multipart
    @POST("Authentication/publish")
    Call<Publishdemandemodel> publishandemande(@Part MultipartBody.Part title,
                                               @Part MultipartBody.Part description,
                                               @Part MultipartBody.Part budget,
                                               @Part MultipartBody.Part project_category,
                                               @Part MultipartBody.Part client_id,
                                               @Part MultipartBody.Part file,
                                               @Part MultipartBody.Part image);*/


/*    @Multipart
    @POST("Authentication/Client/post_a_demand")
    Call<PostDemandModle> post_a_demand(@Part MultipartBody.Part category_id,
                                        @Part MultipartBody.Part title,
                                        @Part MultipartBody.Part description,
                                        @Part MultipartBody.Part budget,
                                        @Part MultipartBody.Part client_id,
                                        @Part MultipartBody.Part project_file,
                                        @Part MultipartBody.Part project_image);*/


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
                                        @Field("description") String description);

    @FormUrlEncoded
    @POST("Language/setLanguage")
    Call<SetLangmodel> setlang(@Field("language_key") String language_key);

    @FormUrlEncoded
    @POST("Client/getUserReviews")
    Call<GetUserReviewsModel> GetUserReviewsModel(@Field("user_id") String user_id);

/*
    @FormUrlEncoded
    @POST("Doctors/DoctorProfile")
    Call<DoctorProfileModle> doctorprofile(@Field("doctor_id") String doctor_id);


    @FormUrlEncoded
    @POST("Authentication/generateOtp")
    Call<MobileOtpLoginModle> app_mobile_otp_generate(@Field("phone") String phone, @Field("forget_type") String forget_type);

    @FormUrlEncoded
    @POST("Authentication/changePassword")
    Call<ForgotPasswordModle> forgotpassword(@Field("phone_number") String phone_number,
                                             @Field("user_type") String user_type,
                                             @Field("password") String password);

    @GET("Banner")
    Call<TopBannerModle> getBanner();

    @FormUrlEncoded
    @POST("Authentication/patient_registration")
    Call<PatientRegistrationModle> Patientsignup(@Field("username") String username,
                                                 @Field("email") String email,
                                                 @Field("password") String password,
                                                 @Field("dob") String dob,
                                                 @Field("gender") String gender,
                                                 @Field("address") String address,
                                                 @Field("phone_number") String phone_number);

    @FormUrlEncoded
    @POST("Authentication/doctor_registration")
    Call<DoctorRegistrationModle> Doctorsignup(@Field("username") String username,
                                               @Field("email") String email,
                                               @Field("password") String password,
                                               @Field("dob") String dob,
                                               @Field("gender") String gender,
                                               @Field("address") String address,
                                               @Field("phone_number") String phone_number);

    *//* @GET("Banner")
     Call<TopBannerModle>Banner();
 *//*
    @Multipart
    @POST("Doctors/updateMyProfile")
    Call<UpDatedDoctorProfileModle> updateMyProfile(@Part MultipartBody.Part doctor_id,
                                                    @Part MultipartBody.Part username,
                                                    @Part MultipartBody.Part profession,
                                                    @Part MultipartBody.Part experience,
                                                    @Part MultipartBody.Part fees,
                                                    @Part MultipartBody.Part address,
                                                    @Part MultipartBody.Part email,
                                                    @Part MultipartBody.Part phone_number,
                                                    @Part MultipartBody.Part dob,
                                                    @Part MultipartBody.Part gender,
                                                    @Part MultipartBody.Part password,
                                                    @Part MultipartBody.Part city,
                                                    @Part MultipartBody.Part country,
                                                    @Part MultipartBody.Part state,
                                                    @Part MultipartBody.Part postal_code,
                                                    @Part MultipartBody.Part hospital_id,
                                                    @Part MultipartBody.Part education,
                                                    @Part MultipartBody.Part biography,
                                                    @Part MultipartBody.Part profile_image);


    @Multipart
    @POST("Patients/updateMyProfile")
    Call<UpdatePatientProfileModle> patientupdateMyProfile(@Part MultipartBody.Part patient_id,
                                                           @Part MultipartBody.Part email,
                                                           @Part MultipartBody.Part dob,
                                                           @Part MultipartBody.Part address,
                                                           @Part MultipartBody.Part phone_number,
                                                           @Part MultipartBody.Part profile_image);


    @FormUrlEncoded
    @POST("Appointments/addAppointment")
    Call<BookAppoinmentModle> addappoinment(@Field("doctor_id") String doctor_id,
                                            @Field("patient_id") String patient_id,
                                            @Field("appointment_reason") String appointment_reason,
                                            @Field("full_name") String full_name,
                                            @Field("phone_number") String phone_number,
                                            @Field("age") String age,
                                            @Field("address") String address,
                                            @Field("description") String description,
                                            @Field("appointment_date") String appointment_date,
                                            @Field("appointment_time") String appointment_time);

    @GET("Doctors/Doctorslist")
    Call<DoctorListModle> doctorlist();

    @FormUrlEncoded
    @POST("Authentication/otpVarification")
    Call<OtpModle> verify_login(@Field("otp") String otp,
                                @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("Appointments/AppointmentsOfDoctors")
    Call<BookGetAppoinmentModle> bookingappointment(@Field("doctor_id") String doctor_id,
                                                    @Field("token_number") String token_number);


    @FormUrlEncoded
    @POST("Appointments/PatientAppointmentHistory")
    Call<MyAppoinmentModle> appoinmenthis(@Field("patient_id") String patient_id);

    @FormUrlEncoded
    @POST("Patients/yourAppointment")
    Call<ScheduleAppoinmentModle> scheduleappoinment(@Field("doctor_id") String doctor_id,
                                                     @Field("patient_id") String patient_id);


*//*  @FormUrlEncoded
    @POST("Authentication/registration")
    Call<RegistrationModle> signup(@Field("user_fullname") String user_fullname,
                                   @Field("user_email") String user_email,
                                   @Field("user_phone") String user_phone,
                                   @Field("user_bdate") String user_bdate,
                                   @Field("user_city") String user_city,
                                   @Field("user_password") String user_password);

    @GET("Products/getAllProduct")
    Call<AllProduct> getAllproduct();



    @GET("Products/Banner")
    Call<TopBannerModle> get_home_top_banner();*//*

     *//*
    @GET("licences")
    Call<LicencesModle> getlicences();
*//*

     *//* @Headers("Content-Type: application/json")
    // @FormUrlEncoded
    @POST("authApi/login")
    Call<LoginModle> login(@Body JsonObject body);

    @GET("authApi/getcollege")
    Call<GetCollegeModle> getcollege();

    @GET("examapi/getexam")
    Call<GetExamModle> getExam();

    @Headers("Content-Type: application/json")
    // @FormUrlEncoded
    @POST("authApi/createUser")
    Call<CreateUserModle> signUp(@Body JsonObject body);
*/

}
