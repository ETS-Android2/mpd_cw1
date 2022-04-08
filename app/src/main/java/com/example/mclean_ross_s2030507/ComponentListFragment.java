package com.example.mclean_ross_s2030507;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class ComponentListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
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
        View view = inflater.inflate(R.layout.fragment_component_list_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.addItemDecoration(
                    new DividerItemDecoration(recyclerView.getContext(),
                    linearLayoutManager.getOrientation())
            );
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(linearLayoutManager);
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            // Convert ArrayList to ArrayAdapter
            ArrayAdapter<ListComponent> adapter = new ArrayAdapter<>(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    components
            );
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(adapter));
        }
        return view;
    }
}