package com.fstracker.foodstoragetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class ViewListActivity extends AppCompatActivity {

    public static final String EXTRA_SEARCH = "com.fstracker.foodstoragetracker.SEARCH";
    public static final String EXTRA_CATEGORY = "com.fstracker.foodstoragetracker.CATEGORY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        Spinner mySpinner = (Spinner) findViewById(R.id.categorySpinner);
        mySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Category.values()));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);
        // use a linear layout manager
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        final MyAdapter mAdapter = new MyAdapter(StorageManager.getLocalStorage().getItemsByCategory(Category.ALL));
        recyclerView.setAdapter(mAdapter);

        //Create adapter for recyclerview, that allows us toadd/replace itens
        //https://developer.android.com/guide/topics/ui/layout/recyclerview
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //A callback with the position selected
                Category selected = Category.values()[position];
                //Here we should update the  list by /StorageManager.getLocalStorage().getItemsByCategory()
                List<FoodItem> updatedList =  (selected == Category.ALL) ? StorageManager.getLocalStorage().getAllItems() : StorageManager.getLocalStorage().getItemsByCategory(selected);
                mAdapter.updateList(updatedList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        //StorageManager.getLocalStorage().getItemsByCategory()
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private List<FoodItem> mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView textView;
            public MyViewHolder(TextView v) {
                super(v);
                textView = v;
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(List <FoodItem> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            // create a new view
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.foodview_list_item, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.textView.setText(mDataset.get(position).getName());

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        public void updateList(List<FoodItem> newList) {
            mDataset = newList;
        }
    }

}
