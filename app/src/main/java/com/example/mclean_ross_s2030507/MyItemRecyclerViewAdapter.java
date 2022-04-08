package com.example.mclean_ross_s2030507;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.mclean_ross_s2030507.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.mclean_ross_s2030507.databinding.FragmentComponentListBinding;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final ArrayAdapter<ListComponent> mValues;

    public MyItemRecyclerViewAdapter(ArrayAdapter<ListComponent> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentComponentListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.getItem(position);
//        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.getItem(position).getTitle());

        holder.itemView.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return mValues.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final TextView mIdView;
        public final TextView mContentView;
        public ListComponent mItem;

        public ViewHolder(FragmentComponentListBinding binding) {
            super(binding.getRoot());
//            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}