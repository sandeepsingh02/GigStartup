package com.example.gigstartup.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gigstartup.R;


public class ToastUtils {
    public static void toast(Context context,String msg) {
        Toast toast = Toast.makeText(context,msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        //Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    public static void toastFailure(Context context,String msg) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_toast,null);

        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.ic_error);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(msg);

        Toast toast = new Toast(context.getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    public static void toastSuccses(Context context,String msg) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_toast,null);

        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.ic_success);
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(msg);

        Toast toast = new Toast(context.getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
