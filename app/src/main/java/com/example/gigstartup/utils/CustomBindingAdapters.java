package com.example.gigstartup.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.gigstartup.view.base.BaseRecyclerview;

import java.text.DecimalFormat;
import java.util.List;

public class CustomBindingAdapters {
    private CustomBindingAdapters() {
        //NO-OP
    }
    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView recyclerView, BaseRecyclerview baseRecyclerViewAdapter) {
        if (baseRecyclerViewAdapter != null && recyclerView != null) {
            recyclerView.setAdapter(baseRecyclerViewAdapter);
        }
    }

    @BindingAdapter({"imageUrl", "error"})
    public static void setImageUrl(ImageView imageView, String url, Drawable drawable){
        if (imageView != null) {
            Context context = imageView.getContext();
            if (url == null) imageView.setImageDrawable(drawable);
            else Glide.with(context).load(url).apply(RequestOptions.placeholderOf(drawable).dontAnimate()).into(imageView);
        }
    }
    @BindingAdapter({"imageCircleUrl","error"})
    public static void setCircleUrl(ImageView imageView,String url,Drawable drawable){
        if (imageView != null) {
            Context context=imageView.getContext();
            if (url == null) imageView.setImageDrawable(drawable);
            else Glide.with(context).load(url).apply(RequestOptions.placeholderOf(drawable).transform(new CircleCrop()).dontAnimate()).into(imageView);
        }
    }

    @BindingAdapter({"custom_entries"})
    public  static  void setSpinnerCustomAdapter(Spinner spinner, List<?> list){
        if (list != null) {
            ArrayAdapter<?> adapter = new ArrayAdapter<>(spinner.getContext(), android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }

    }
    @BindingAdapter({"editTextDecimalValue"})
    public static void setVal(EditText editText, double newVal) {

        String currentValue = editText.getText().toString();

        try {

            if (Double.valueOf(currentValue) != newVal) {
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String val = decimalFormat.format(newVal);
                editText.setText(val);
            }
        } catch (NumberFormatException exception) {
            // Do nothing
        }
    }
}
