package com.example.mclean_ross_s2030507;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {
    private String geoRssPoint, title;
    private GoogleMap gMap;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            gMap = googleMap;
            LatLng glasgow = new LatLng(55.787753, -3.925677);
            googleMap.addMarker(new MarkerOptions().position(glasgow).title("Marker in Glasgow, Scotland"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(glasgow));


            int space = geoRssPoint.indexOf(" ");
            double lat = Double.parseDouble(geoRssPoint.substring(0, space));
            double lon = Double.parseDouble(geoRssPoint.substring(space + 1, geoRssPoint.length()));
            LatLng target = new LatLng(lat, lon);

            gMap.addMarker(new MarkerOptions().position(target).title(title));
            gMap.moveCamera(CameraUpdateFactory.newLatLng(target));
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(target, 13));
        }
    };

    public MapsFragment(String geoRssPoint, String title) {
        this.geoRssPoint = geoRssPoint;
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}