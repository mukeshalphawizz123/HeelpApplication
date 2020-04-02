package com.frelance;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class CustomToast {
	public void Show_Toast(Context context, View view, String error) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.custom_toast,
				(ViewGroup) view.findViewById(R.id.toast_root));
		TextView text = (TextView) layout.findViewById(R.id.toast_error);
		RelativeLayout  toast_root = layout.findViewById(R.id.toast_root);
		text.setText(error);
		Toast toast = new Toast(context);
		toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);// Set
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		Animation anim = AnimationUtils.loadAnimation(toast_root.getContext(), R.anim.shake);
		toast_root.setAnimation(anim);
		anim.start();
		toast_root.startAnimation(anim);
		toast.show();
	}

}
