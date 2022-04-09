package com.example.mclean_ross_s2030507;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mclean_ross_s2030507.databinding.FragmentComponentListBinding;
import com.example.mclean_ross_s2030507.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> implements Filterable {

    private final ArrayList<ListComponent> mValues;
    private ArrayList<ListComponent> mValuesFull;

    public MyItemRecyclerViewAdapter(ArrayList<ListComponent> items) {
        mValues = items;
        mValuesFull = new ArrayList<>(items);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentComponentListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Log.e("MyTag", String.valueOf(holder));
        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).getTitle());

        holder.itemView.setOnClickListener(view -> {

        });
    }

    @Override
    public Filter getFilter() { return filter; }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ListComponent> filteredList = new ArrayList<>();
            String charString = charSequence.toString().toLowerCase().trim();
            if (charString.isEmpty()) {
                filteredList.addAll(mValuesFull);
            } else {
                for (ListComponent component : mValuesFull) {
                    if (component.getTitle().toLowerCase().trim().contains(charString)) {
                        filteredList.add(component);
                    }
                }
                mValuesFull = filteredList;
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mValues.clear();
            mValues.addAll((ArrayList<ListComponent>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public int getItemCount() {
        return mValuesFull.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public final TextView mIdView;
        public final TextView mContentView;
        public ListComponent mItem;

        public ViewHolder(FragmentComponentListBinding binding) {
            super(binding.getRoot());
//            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}