package com.freelanceapp.ApiPkg;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    /*https://alphawizz.com/Freelance/Api_controllers*/
    private static final String BASE_URL = "https://alphawizz.com/Freelance/Api_controllers/";
   public static final String IMAGE_URL = "https://alphawizz.com/Freelance/uploads/project_image/";
    public static final String MISSION_IMAGE_URL = "https://alphawizz.com/Freelance/uploads/mission/";
    public static final String MYMISSIONANDMYDEMANDE_IMAGE_URL = "https://alphawizz.com/Freelance/uploads/project_image/";
    public static final String MISSION_USER_IMAGE_URL = "https://alphawizz.com/Freelance/uploads/profiles/";
    private static OkHttpClient client;
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
   /* public static Retrofit getClienta() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_G)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }*/
}