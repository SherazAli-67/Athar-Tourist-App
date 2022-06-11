package com.myapp.asar_.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.myapp.asar_.MainActivity;
import com.myapp.asar_.R;
import com.myapp.asar_.adapter.PlacesImagesAdapter;
import com.myapp.asar_.database.entities.PlaceImages;
import com.myapp.asar_.database.entities.Places;
import com.myapp.asar_.model.NotificationModel;
import com.myapp.asar_.viewmodel.AppViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceInformationActivity extends FragmentActivity {

    private GoogleMap mMap;
    AppViewModel viewModel;

    @BindView(R.id.placeImagesRecyclerView)
    RecyclerView recyclerView;

    String title = "";
    PlacesImagesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_information);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        onNewIntent(getIntent());
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras() != null) {
            NotificationModel notification = (NotificationModel) intent.getSerializableExtra("Notification");
            if (notification != null) {
                title = notification.getTitle();
                loadAllImages(notification.getTitle());
                LatLng placeLatLng = new LatLng(notification.getPlaceLat(), notification.getPlaceLng());
                LatLng userLatLng = new LatLng(notification.getUserLat(), notification.getUserLng());

                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {
                        mMap = googleMap;

                        mMap.addPolygon(new PolygonOptions().add(placeLatLng).add(userLatLng).fillColor(getResources().getColor(R.color.btn_color)));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng,5.0f));
                    }
                });
            }
        }
    }

    private void loadAllImages(String title) {
//
//        String boulevardRiyadh = getResources().getString(R.string.boulevard_city_title);
//        String almasmak = getResources().getString(R.string.al_masmak_palace_musem_title);
//        String murabba = getResources().getString(R.string.murabba_historic_palace_title);
//        String libraryy = getResources().getString(R.string.king_fahad_lib_title);
//        String zoo = getResources().getString(R.string.riyadh_zoo_title);
//        String museum = getResources().getString(R.string.national_muesam_park_title);

        viewModel.getAllImagesForPlaces(title).observe(this, new Observer<List<PlaceImages>>() {
            @Override
            public void onChanged(List<PlaceImages> placeImages) {

                /*
                for(PlaceImages image : placeImages){
                    Log.d("TAG", "Place title: "+image.getPlaceID());
                }
                 */
                if (placeImages.size() > 0) {
//                    Toast.makeText(getApplicationContext(), "Size: " + placeImages, Toast.LENGTH_SHORT).show();
                    adapter = new PlacesImagesAdapter(placeImages, PlaceInformationActivity.this);
                    recyclerView.setAdapter(adapter);
                }else{

                }
            }
        });
     /*
        switch (title){
            case "Boulevard Riyadh City":
                break;

            case "Al Masmak Palace Museum":
                break;

            case "Murabba Historical Palace":
                break;

            case "The King Fahad National Library":
                break;

            case "Riyadh Zoo":
                break;

            case "The National Museum Park":
                break;
        }
    }

      */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PlaceInformationActivity.this, MainActivity.class));
        finish();
    }
}