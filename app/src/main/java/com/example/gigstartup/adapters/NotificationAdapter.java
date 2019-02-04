package com.example.gigstartup.adapters;

import android.support.annotation.NonNull;

import com.example.gigstartup.R;
import com.example.gigstartup.databinding.NotificationItemRowBinding;
import com.example.gigstartup.dtos.DtoNotification;
import com.example.gigstartup.view.base.BaseRecyclerview;

public class NotificationAdapter extends BaseRecyclerview<NotificationItemRowBinding, DtoNotification> {
    private NotificationItemClickListner notificationItemClickListner;
    public NotificationAdapter() {
        super(R.layout.notification_item_row);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerview.BaseRecyclerViewHolder baseRecyclerViewHolder, int i) {
        DtoNotification dtoNotification=getItem(i);
        ((NotificationItemRowBinding)baseRecyclerViewHolder.getBinding()).setItem(dtoNotification);
        baseRecyclerViewHolder.getBinding().getRoot().setOnClickListener(v -> notificationItemClickListner.onItemClick(dtoNotification));
    }

    //Future Scope
    public void setNotificationItemClickListner(NotificationItemClickListner notificationItemClickListner) {
        this.notificationItemClickListner=notificationItemClickListner;
    }


    public interface  NotificationItemClickListner{
        void onItemClick(DtoNotification dtoNotification);

    }
}
