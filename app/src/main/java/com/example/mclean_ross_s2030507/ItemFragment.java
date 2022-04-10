package com.example.mclean_ross_s2030507;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ItemFragment extends Fragment {
    private String title, description, link, point, author, comments, pubDate;
    private FragmentManager fragmentManager;

    public ItemFragment() {}

    public ItemFragment(ListComponent component) {
        this.title = component.getTitle();
        this.description = component.getDescription();
        this.link = component.getLink();
        this.point = component.getGeoRssPoint();
        this.author = component.getAuthor();
        this.comments = component.getComments();
        this.pubDate = String.valueOf(component.getPublicationDate());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActivity().setContentView(R.layout.fragment_item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        TextView title = view.findViewById(R.id.title);
        TextView description = view.findViewById(R.id.description);
        TextView link = view.findViewById(R.id.link);
        TextView author = view.findViewById(R.id.author);
        TextView comments = view.findViewById(R.id.comments);
        TextView pubDate = view.findViewById(R.id.pubdate);

        title.setText(this.title);
        description.setText(this.description);
        link.setText(this.link);
        author.setText(this.author);
        comments.setText(this.comments);
        pubDate.setText(this.pubDate);

        fragmentManager = getParentFragmentManager();
        MapsFragment mapsFragment = new MapsFragment(this.point, this.title);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.maps_fragment_target, mapsFragment);
        fragmentTransaction.commit();

        return view;
    }
}