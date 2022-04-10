package com.example.mclean_ross_s2030507;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mclean_ross_s2030507.databinding.FragmentComponentListItemBinding;
import com.example.mclean_ross_s2030507.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 */
public class ComponentRecyclerViewAdapter extends RecyclerView.Adapter<ComponentRecyclerViewAdapter.ViewHolder> implements Filterable {

    private final ArrayList<ListComponent> mValues;
    private final ArrayList<ListComponent> mValuesFull;
    private static FragmentManager fragmentManager;

    public ComponentRecyclerViewAdapter(FragmentActivity fragmentActivity, ArrayList<ListComponent> items) {
        mValues = items;
        mValuesFull = new ArrayList<>(items);
        fragmentManager = fragmentActivity.getSupportFragmentManager();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentComponentListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (mValues.size() > 0) {
            holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).getTitle());
        }
    }

    @Override
    public Filter getFilter() { return filter; }

    private final Filter filter = new Filter() {
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
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public final TextView mIdView;
        public final TextView mContentView;
        public ListComponent mItem;

        public ViewHolder(FragmentComponentListItemBinding binding) {
            super(binding.getRoot());
//            mIdView = binding.itemNumber;
            mContentView = binding.content;
            mContentView.setOnClickListener(view -> {
                ItemFragment itemFragment = new ItemFragment(mItem);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_target, itemFragment);
                fragmentTransaction.commit();
            });
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}