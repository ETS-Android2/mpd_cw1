package com.example.mclean_ross_s2030507;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

/**
 * A fragment representing a list of Items.
 */
public class ComponentListFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private ArrayList<ListComponent> components;
    ComponentRecyclerViewAdapter adapter;
    LocalDate localDate;
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
            int mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

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
            adapter = new ComponentRecyclerViewAdapter(requireActivity(), components);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) search.getActionView();
        searchView.setQueryHint("Search here");
        search(searchView);

        MaterialDatePicker.Builder<Long> materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Select a date");
        final MaterialDatePicker<Long> materialDatePicker = materialDateBuilder.build();

        menu.findItem(R.id.date_picker_button).setOnMenuItemClickListener(menuItem -> {
            materialDatePicker.show(requireActivity().getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            return true;
        });

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
            localDate = LocalDate.parse(materialDatePicker.getHeaderText(), dtf);
//            Toast.makeText(getContext(), String.valueOf(localDate), Toast.LENGTH_LONG).show();
            adapter.getFilter().filter(String.valueOf(localDate));
        });

        menu.findItem(R.id.search).setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                menu.findItem(R.id.date_picker_button).setEnabled(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                menu.findItem(R.id.date_picker_button).setEnabled(true);
                return true;
            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    private void search(androidx.appcompat.widget.SearchView searchView) {
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}