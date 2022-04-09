package com.example.mclean_ross_s2030507;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class ComponentListFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private ArrayList<ListComponent> components;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ComponentListFragment() {
    }

    public ComponentListFragment(ArrayList<ListComponent> components) {
        this.components = components;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ComponentListFragment newInstance(int columnCount) {
        ComponentListFragment fragment = new ComponentListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Insert SearchView
        LinearLayout searchViewTarget = requireActivity().findViewById(R.id.search_view_target);
        SearchView searchView = new SearchView(this.getContext());
        searchView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        searchView.setQueryHint("Search here");
        searchViewTarget.addView(searchView);

        // Set the adapter
        View view = inflater.inflate(R.layout.fragment_component_list_list, container, false);
        if (view instanceof RecyclerView) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(
                    new DividerItemDecoration(recyclerView.getContext(),
                    linearLayoutManager.getOrientation())
            );
            MyItemRecyclerViewAdapter adapter = new MyItemRecyclerViewAdapter(components);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
//                    ArrayList<String> componentTitles = new ArrayList<>();
//                    for (ListComponent component : components) {
//                        componentTitles.add(component.getTitle().toLowerCase().trim());
//                    }
//                    if (componentTitles.contains(query.toLowerCase())) {
//                        adapter.getFilter().filter(query);
//                    } else {
//                        Toast.makeText(recyclerView.getContext(), "No Match found",Toast.LENGTH_LONG).show();
//                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });
        }
        return view;
    }
}