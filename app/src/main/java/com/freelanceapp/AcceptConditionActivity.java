package com.freelanceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Window;

import com.freelanceapp.utility.CheckNetwork;
import com.freelanceapp.utility.StatusBarManagment;

public class AcceptConditionActivity extends AppCompatActivity {

    private AppCompatTextView tvtextthree,tvhelpcentertexttwo,tvtextthreeee,tvtextfive,tvtextseven,tvtextnine,tvtextele,tvtextthird,
            tvtextfifthin,tvtextseventhin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_condition);
        Window window = getWindow();
        StatusBarManagment.hideShowStatusBar(getApplicationContext(), window);
        render();
    }

    private void render() {
        tvtextseventhin=findViewById(R.id.tvtextseventhinid);
        tvtextfifthin=findViewById(R.id.tvtextfifthinid);
        tvtextthird=findViewById(R.id.tvtextthirdid);
        tvtextele=findViewById(R.id.tvtexteleid);
        tvtextnine=findViewById(R.id.tvtextnineid);
        tvtextseven=findViewById(R.id.tvtextsevenid);
        tvtextfive=findViewById(R.id.tvtextfiveid);
        tvtextthreeee=findViewById(R.id.tvtextthreeeeid);
        tvhelpcentertexttwo=findViewById(R.id.tvhelpcentertexttwoid);
        tvtextthree = findViewById(R.id.tvtextthreeid);

        SpannableString content = new SpannableString("Vos engagements :");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvtextthree.setText(content);

        SpannableString content1 = new SpannableString("Heelp permet aux étudiants de :");
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        tvhelpcentertexttwo.setText(content1);

        SpannableString content2 = new SpannableString("Données personnelles :");
        content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
        tvtextthreeee.setText(content2);

        SpannableString content3 = new SpannableString("Terminologie :");
        content3.setSpan(new UnderlineSpan(), 0, content3.length(), 0);
        tvtextfive.setText(content3);

        SpannableString content4 = new SpannableString("Statuts des missions et demandes :");
        content4.setSpan(new UnderlineSpan(), 0, content4.length(), 0);
        tvtextseven.setText(content4);

        SpannableString content5 = new SpannableString("Parrainage :");
        content5.setSpan(new UnderlineSpan(), 0, content5.length(), 0);
        tvtextnine.setText(content5);

        SpannableString content6 = new SpannableString("Paiement :");
        content6.setSpan(new UnderlineSpan(), 0, content6.length(), 0);
        tvtextele.setText(content6);

        SpannableString content7 = new SpannableString("Litige :");
        content7.setSpan(new UnderlineSpan(), 0, content7.length(), 0);
        tvtextthird.setText(content7);

        SpannableString content8 = new SpannableString("Frais de service :");
        content8.setSpan(new UnderlineSpan(), 0, content8.length(), 0);
        tvtextfifthin.setText(content8);

        SpannableString content9 = new SpannableString("Listes des frais :");
        content9.setSpan(new UnderlineSpan(), 0, content9.length(), 0);
        tvtextseventhin.setText(content9);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckNetwork.backScreenWithouFinish(AcceptConditionActivity.this);
    }
}
