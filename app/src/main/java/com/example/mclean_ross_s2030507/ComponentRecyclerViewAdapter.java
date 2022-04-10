package com.example.mclean_ross_s2030507;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mclean_ross_s2030507.databinding.FragmentComponentListItemBinding;
import com.example.mclean_ross_s2030507.placeholder.PlaceholderContent.PlaceholderItem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 */
public class ComponentRecyclerViewAdapter extends RecyclerView.Adapter<ComponentRecyclerViewAdapter.ViewHolder> implements Filterable {

    private final ArrayList<ListComponent> mValues;
    private final ArrayList<ListComponent> mValuesFull;
    private static FragmentManager fragmentManager;
    private static FragmentActivity fragmentActivity;

    public ComponentRecyclerViewAdapter(FragmentActivity fragmentActivity, ArrayList<ListComponent> items) {
        mValues = items;
        mValuesFull = new ArrayList<>(items);
        fragmentManager = fragmentActivity.getSupportFragmentManager();
        this.fragmentActivity = fragmentActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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

            if (holder.mItem.getTotalTimeAllotted() > 0 && holder.mItem.getTotalTimeAllotted() <= 10) {
                holder.mContentView.setTextColor(fragmentActivity.getResources().getColor(R.color.black, Resources.getSystem().newTheme()));
                holder.mContentView.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.yellow, Resources.getSystem().newTheme()));
            }
            if (holder.mItem.getTotalTimeAllotted() > 10 && holder.mItem.getTotalTimeAllotted() <= 20) {
                holder.mContentView.setTextColor(fragmentActivity.getResources().getColor(R.color.black, Resources.getSystem().newTheme()));
                holder.mContentView.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.dark_yellow, Resources.getSystem().newTheme()));
            }
            if (holder.mItem.getTotalTimeAllotted() > 20 && holder.mItem.getTotalTimeAllotted() <= 30) {
                holder.mContentView.setTextColor(fragmentActivity.getResources().getColor(R.color.white, Resources.getSystem().newTheme()));
                holder.mContentView.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.orange, Resources.getSystem().newTheme()));
            }
            if (holder.mItem.getTotalTimeAllotted() > 30 && holder.mItem.getTotalTimeAllotted() <= 40) {
                holder.mContentView.setTextColor(fragmentActivity.getResources().getColor(R.color.white, Resources.getSystem().newTheme()));
                holder.mContentView.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.tomato, Resources.getSystem().newTheme()));
            }
            if (holder.mItem.getTotalTimeAllotted() > 40) {
                holder.mContentView.setTextColor(fragmentActivity.getResources().getColor(R.color.white, Resources.getSystem().newTheme()));
                holder.mContentView.setBackgroundColor(fragmentActivity.getResources().getColor(R.color.red, Resources.getSystem().newTheme()));
            }
        }
    }

    @Override
    public Filter getFilter() { return filter; }

    private final Filter filter = new Filter() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ListComponent> filteredList = new ArrayList<>();
            String charString = charSequence.toString().toLowerCase().trim();
            if (charString.isEmpty()) {
                filteredList.addAll(mValuesFull);
            } else {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

                LocalDate actualDate = null;
                if (new GregorianDateMatcher().matches(charString)) {
                    actualDate = LocalDate.parse(charString, dtf);
                }
//                actualDate = LocalDate.parse(charString, dtf);

                for (ListComponent component : mValuesFull) {
                    if (component.getTitle().toLowerCase().trim().contains(charString)
                            || String.valueOf(component.getPublicationDate()).trim()
                            .equals(String.valueOf(actualDate).trim())) {
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

        @RequiresApi(api = Build.VERSION_CODES.O)
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

    public static class GregorianDateMatcher implements DateMatcher {
        private final Pattern DATE_PATTERN = Pattern.compile(
                "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
                        + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
                        + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
                        + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");

        @Override
        public boolean matches(String date) {
            return DATE_PATTERN.matcher(date).matches();
        }
    }
}