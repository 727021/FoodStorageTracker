package com.fstracker.foodstoragetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewListRecyclerAdapter extends RecyclerView.Adapter
        <ViewListRecyclerAdapter.MyViewHolder> {

    private List<FoodItem> mDataset;
    private OnFoodItemListener mOnFoodItemListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ViewListRecyclerAdapter(List<FoodItem> myDataset,
                                   OnFoodItemListener onFoodItemListener) {
        mDataset = myDataset;
        mOnFoodItemListener = onFoodItemListener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView textView;
        OnFoodItemListener onFoodItemListener;

        public MyViewHolder(TextView v, OnFoodItemListener onFoodItemListener) {
            super(v);
            textView = v;
            this.onFoodItemListener = onFoodItemListener;

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onFoodItemListener.onFoodItemClick(getAdapterPosition());
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewListRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.foodview_list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v, mOnFoodItemListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset.get(position).toString());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public FoodItem getItem(int position) {
        return mDataset.get(position);
    }

    public void updateList(List<FoodItem> newList) {
        mDataset = newList;
    }

    public interface OnFoodItemListener {
        void onFoodItemClick(int position);
    }
}