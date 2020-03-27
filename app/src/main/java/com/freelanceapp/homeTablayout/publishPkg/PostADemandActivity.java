package com.freelanceapp.homeTablayout.publishPkg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asksira.bsimagepicker.BSImagePicker;
import com.bumptech.glide.Glide;
import com.freelanceapp.ApiPkg.ApiServices;
import com.freelanceapp.ApiPkg.RetrofitClient;
import com.freelanceapp.CustomToast;
import com.freelanceapp.R;
import com.freelanceapp.homeTablayout.publishPkg.publishModlePkg.PostDemandModle;
import com.freelanceapp.notificationPkg.NotificationActivity;
import com.freelanceapp.paymentPkg.ComfirmationActivity;
import com.freelanceapp.utility.CheckNetwork;
import com.freelanceapp.utility.ImagePicker;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostADemandActivity extends AppCompatActivity implements View.OnClickListener, BSImagePicker.OnMultiImageSelectedListener, BSImagePicker.ImageLoaderDelegate, SelectImageAdapter.SelectImageOnClickListener {
    private RelativeLayout rlpublishapplication, RlItemdestitle, rlPieceJoint, rlPiece;
    private ImageView ivdetailback, ivnotification, IvItemdesImage;
    private TextView tvHomeData;
    private EditText EtItemdesTitleText, EtItemdes, EtItemdesBudget;
    private String title, description, budget, imageUrl, project_category, client_id, file, image, projectId;
    private ApiServices apiServices;
    private ProgressBar Pbitemdescription;
    private static final int SELECT_PICTURE = 101;
    private static final int PERMISSION_READ_EXTERNAL_STORAGE = 100;
    private String wholePath, wholeImagePath;
    private File fileForImage, fileForDocs;
    private String filePath = null, profilImgPath, docPath;
    private File sourceFile;
    private static final int FILE_SELECT_CODE = 0;
    private static Animation shakeAnimation;
    private RecyclerView rvselectimageId;
    private SelectImageAdapter selectImageAdapter;

    public static final void customToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);
        apiServices = RetrofitClient.getClient().create(ApiServices.class);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        budget = intent.getStringExtra("budget");
        imageUrl = intent.getStringExtra("imageUrl");
        projectId = intent.getStringExtra("projectId");

        init();
        return;
    }


    private void init() {
        Pbitemdescription = findViewById(R.id.PbitemdescriptionId);
        rlPieceJoint = findViewById(R.id.rlPieceJointId);
        rlPieceJoint.setOnClickListener(this);
        rlPiece = findViewById(R.id.rlPieceId);
        rlPiece.setOnClickListener(this);
        RlItemdestitle = findViewById(R.id.RlItemdestitleId);
        IvItemdesImage = findViewById(R.id.IvItemdesImageId);
        tvHomeData = findViewById(R.id.tvHomeDataId);
        EtItemdesTitleText = findViewById(R.id.EtItemdesTitleTextid);
        EtItemdes = findViewById(R.id.EtItemdesId);
        EtItemdesBudget = findViewById(R.id.EtItemdesBudgetId);
        ivdetailback = findViewById(R.id.ivdetailbackId);
        ivnotification = findViewById(R.id.ivnotificationId);
        ivdetailback.setOnClickListener(this);
        ivnotification.setOnClickListener(this);
        rlpublishapplication = findViewById(R.id.rlpublishapplicationid);
        rlpublishapplication.setOnClickListener(this);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);

        if (title == null) {
            title = "";
        } else {
            tvHomeData.setText(title);
        }
        if (description == null) {
            description = "";
        } else {
            // EtItemdes.setText(description);
        }
        if (budget == null) {
            EtItemdesBudget.setText(budget);
        }
        if (imageUrl == null) {
            imageUrl = "";
        } else {
            Picasso.with(getApplicationContext()).load(RetrofitClient.IMAGE_URL + imageUrl).into(IvItemdesImage);
        }
        rvselectimageId = findViewById(R.id.rvselectimageId);
    }

    private void postDemands() {
        client_id = "1";
        Pbitemdescription.setVisibility(View.VISIBLE);
        MultipartBody.Part imgFileStation = null;
        MultipartBody.Part imgFileStationDoc = null;
        if (profilImgPath == null) {
        } else {
            fileForImage = new File(profilImgPath);
            RequestBody requestFileOne = RequestBody.create(MediaType.parse("multipart/form-data"), fileForImage);
            imgFileStation = MultipartBody.Part.createFormData("project_image", fileForImage.getName(), requestFileOne);
        }

        if (docPath == null) {
        } else {
            fileForDocs = new File(docPath);
            RequestBody requestFileOne = RequestBody.create(MediaType.parse("multipart/form-data"), docPath);
            imgFileStationDoc = MultipartBody.Part.createFormData("project_file", fileForDocs.getName(), requestFileOne);
        }

        MultipartBody.Part category_id = MultipartBody.Part.createFormData("category_id", String.valueOf(projectId));
        MultipartBody.Part title_ = MultipartBody.Part.createFormData("title", String.valueOf(title));
        MultipartBody.Part description = MultipartBody.Part.createFormData("description", EtItemdes.getText().toString());
        MultipartBody.Part budget_ = MultipartBody.Part.createFormData("budget", EtItemdesBudget.getText().toString());
        MultipartBody.Part client_id_ = MultipartBody.Part.createFormData("client_id", String.valueOf(client_id));
        // Log.v("data", projectId + "/" + title + "/" + EtItemdes.getText().toString() + "/" + client_id + "/" + docPath + "/" + profilImgPath);
        apiServices.post_a_demand(category_id, title_, description, budget_, client_id_, imgFileStation)
                .enqueue(new Callback<PostDemandModle>() {
                    @Override
                    public void onResponse(Call<PostDemandModle> call, Response<PostDemandModle> response) {
                        Pbitemdescription.setVisibility(View.GONE);
                        //Toast.makeText(getActivity(), "testing" + response.toString(), Toast.LENGTH_LONG).show();
                        if (response.isSuccessful()) {
                            {
                                Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_LONG).show();
                                CheckNetwork.goTobackScreen(PostADemandActivity.this, ComfirmationActivity.class);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PostDemandModle> call, Throwable t) {
                        Log.v("Error", t.toString());
                        Pbitemdescription.setVisibility(View.GONE);
                        //Toast.makeText(getActivity(), "testing" + t.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void askStoragePermission() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            {
                ActivityCompat.requestPermissions(PostADemandActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_READ_EXTERNAL_STORAGE);
            }
        } else {
            chooseFromGallery();
        }
    }

    private void chooseFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseFromGallery();
                } else {
                    //Constants.customToast(getApplicationContext(), "Permission Denied");
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }


    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivnotificationId:
                CheckNetwork.nextScreenWithoutFinish(PostADemandActivity.this, NotificationActivity.class);
                break;
            case R.id.rlpublishapplicationid:
                validation(v);
                break;
            case R.id.ivdetailbackId:
                onBackPressed();
                break;
            case R.id.rlPieceId:
                askStoragePermission();
               /* BSImagePicker pickerDialog = new BSImagePicker.Builder("com.asksira.imagepickersheetdemo")
                        .setMaximumDisplayingImages(Integer.MAX_VALUE)
                        .isMultiSelect()
                        .setMinimumMultiSelectCount(1)
                        .setMaximumMultiSelectCount(6)
                        .build();
                pickerDialog.show(getSupportFragmentManager(), "picker");*/
                break;
            case R.id.rlPieceJointId:
                showFileChooser();
                break;

        }
    }


    private void validation(View v) {
        if (EtItemdesTitleText.getText().toString().isEmpty()) {
            new CustomToast().Show_Toast(this, v, "Title Can't Empty");
            EtItemdesTitleText.startAnimation(shakeAnimation);
            EtItemdesTitleText.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        } else {
            postDemands();
        }

    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*file/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Copy"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK) {
            Uri content_describer = data.getData();
            docPath = getRealPathFromURI(PostADemandActivity.this, content_describer);
            Log.v("images", docPath);
            BufferedReader reader = null;
            try {
                // open the user-picked file for reading:
                InputStream in = getContentResolver().openInputStream(content_describer);
                // now read the content:
                reader = new BufferedReader(new InputStreamReader(in));
                String line;
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                // Do something with the content in
                //  some_view.setText(builder.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = ImagePicker.getImageFromResult(PostADemandActivity.this, resultCode, data);
                profilImgPath = getRealPathFromURI(PostADemandActivity.this, imageUri);
                Log.v("iamges", profilImgPath.toString());

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }

    @Override
    public void loadImage(Uri imageUri, ImageView ivImage) {
        Glide.with(PostADemandActivity.this).load(imageUri).into(ivImage);
        // Glide.with(PostADemandActivity.this).load(imageUri).into(ivImage);

    }

    @Override
    public void onMultiImageSelected(List<Uri> uriList, String tag) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(PostADemandActivity.this, RecyclerView.HORIZONTAL, false);
        rvselectimageId.setLayoutManager(layoutManager);
        selectImageAdapter = new SelectImageAdapter(this, this);
        rvselectimageId.setAdapter(selectImageAdapter);
        selectImageAdapter.scheduleappoinList(uriList);
    }

    @Override
    public void onClick(View view, int position) {

    }
}
