package com.example.gigstartup.view.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerview <DB extends ViewDataBinding ,T>  extends RecyclerView.Adapter<BaseRecyclerview.BaseRecyclerViewHolder> {
    private List<T> data;
    private int layoutId;
    public BaseRecyclerview(@LayoutRes int layoutId) {
        this.data = new ArrayList<>();
        this.layoutId = layoutId;
    }
    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BaseRecyclerViewHolder(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),getLayoutId(),viewGroup,false));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public int getLayoutId() {
        return layoutId;
    }

    public ArrayList<T> getData() {
        return new ArrayList<>(data);
    }

    public void addAllItems(List<T> t) {
        data.clear();
        data.addAll(t);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        if (position < data.size())
            return data.get(position);
        return null;
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
        private DB binding;
        public BaseRecyclerViewHolder(DB binging) {
            super(binging.getRoot());
            this.binding=binging;
        }

        public DB getBinding() {
            return binding;
        }
    }
}
