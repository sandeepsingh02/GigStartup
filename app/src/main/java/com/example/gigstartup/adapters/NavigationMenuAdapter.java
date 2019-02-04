package com.example.gigstartup.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gigstartup.R;
import com.example.gigstartup.databinding.NavigationItemBinding;
import com.example.gigstartup.utils.Constants;
import com.example.gigstartup.utils.SharedPref;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NavigationMenuAdapter extends RecyclerView.Adapter<NavigationMenuAdapter.MyViewHolder> {
    private List<Integer> namesList = new ArrayList<>();
    private List<Integer> imagesList = new ArrayList<>();
    private Context mContext;
    private NavigationMenuItemClickListener navigationMenuItemClickListener;
    private int selectedPosition = 0;
    public NavigationMenuAdapter(Context mContext, NavigationMenuItemClickListener navigationMenuItemClickListener) {
        this.mContext = mContext;
        this.navigationMenuItemClickListener = navigationMenuItemClickListener;
        if (SharedPref.read(Constants.ROLE, "Requester").equalsIgnoreCase("Worker")) {

            namesList.addAll(Arrays.asList(R.string.menu_home, R.string.menu_requested_work, R.string.menu_completed_work, R.string.menu_denied_work, R.string.menu_feedback, R.string.menu_logout));
            int[] images = { R.drawable.ic_prgress, R.drawable.ic_new_work, R.drawable.ic_completed, R.drawable.ic_denied, R.drawable.ic_feedback,R.drawable.ic_logout};

            for (int image : images) {
                imagesList.add(image);
            }
        } else {
            namesList.addAll(Arrays.asList(R.string.menu_home, R.string.menu_hire_worker, R.string.menu_requested_work, R.string.menu_completed_work, R.string.menu_denied_work, R.string.menu_feedback, R.string.menu_logout));
            int[] images = { R.drawable.ic_prgress, R.drawable.ic_hire, R.drawable.ic_new_work, R.drawable.ic_completed, R.drawable.ic_denied, R.drawable.ic_feedback,R.drawable.ic_logout};

            for (int image : images) {
                imagesList.add(image);
            }
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        NavigationItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.navigation_item, viewGroup, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.binding.navTv.setText(mContext.getResources().getString(namesList.get(i)));
        myViewHolder.binding.navIcon.setImageResource(imagesList.get(i));

        if (selectedPosition == i) {
            myViewHolder.binding.navTv.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        } else {
            myViewHolder.binding.navTv.setTextColor(mContext.getResources().getColor(R.color.black));
        }


        myViewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationMenuItemClickListener.OnMenuItemClick(namesList.get(i));
                selectedPosition = i;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return namesList.size();
    }

    void setHighlighted(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    public interface NavigationMenuItemClickListener {
        void OnMenuItemClick(int name);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        NavigationItemBinding binding;
        MyViewHolder(@NonNull NavigationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
