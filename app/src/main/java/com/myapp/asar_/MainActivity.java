package com.myapp.asar_;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.myapp.asar_.adapter.PlacesAdapter;
import com.myapp.asar_.database.entities.Places;
import com.myapp.asar_.database.entities.User;
import com.myapp.asar_.helper.AppPreferenceHelper;
import com.myapp.asar_.model.NotificationModel;
import com.myapp.asar_.ui.PlaceInformationActivity;
import com.myapp.asar_.ui.ProfileActivity;
import com.myapp.asar_.utils.CustomMethods;
import com.myapp.asar_.viewmodel.AppViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_REQUEST_CODE = 123;
    @BindView(R.id.profileImage)
    CircleImageView profileImage;

    @BindView(R.id.tv_currentLoc)
    TextView currentLoc;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.searchPlaceUsingTitle)
    SearchView search;


    AppPreferenceHelper appPreferenceHelper;
    AppViewModel viewModel;

    PlacesAdapter adapter;
    FusedLocationProviderClient fusedLocationProviderClient;

    LocationCallback mLocationCallback;

    View.OnClickListener addToGoList;

    double userLat;
    double userLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.Theme_Dark);
        }else{
            setTheme(R.style.Theme_Light);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initUI();

    }

    private void initUI() {
        appPreferenceHelper = AppPreferenceHelper.getInstance(getApplicationContext());
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        profileImage.setOnClickListener(this::onProfileImageClick);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        User user = viewModel.getUserUsingID(appPreferenceHelper.getCurrentUserID());

        addToGoList = v -> {
          Places place = (Places) v.getTag();
          place.setAddedToGoList(true);
          place.setUserID(appPreferenceHelper.getCurrentUserID());
          int result = viewModel.updatePlace(place);

          if(result > 0){
              Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
          }
        };

        if (user.getProfileImage() != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(user.getProfileImage(), 0, user.getProfileImage().length);
            profileImage.setImageBitmap(bmp);
        }
        locationStatusCheck();

        viewModel.getAllPlaces().observe(this, new Observer<List<Places>>() {
            @Override
            public void onChanged(List<Places> places) {
                if (places.size() > 0) {
                    for(Places place: places){
                        Log.d("TAG", "Place is to go list: "+place.getTitle()+", "+place.isAddedToGoList());
                    }
                    adapter = new PlacesAdapter(places, MainActivity.this, addToGoList);
                    recyclerView.setAdapter(adapter);
                    adapter.setmItemClickListener(new PlacesAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                            Places place = places.get(position);
                            double placeLat = place.getLatitude();
                            double placeLng = place.getLongitude();

                            if(userLat != 0.0 && userLng != 0.0){
                                NotificationModel notification = new NotificationModel(placeLat, placeLng, userLat, userLng, place.getTitle());
                                Intent intent = new Intent(MainActivity.this, PlaceInformationActivity.class);
                                intent.putExtra("Notification",notification);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.d("TAG", "Location Fused not null: ");
                if (locationResult.getLastLocation() != null) {
                    checkIfUserPassesNearToAnyPlace(locationResult.getLastLocation());
                    Location location = locationResult.getLastLocation();
                    userLat = location.getLatitude();
                    userLng = location.getLongitude();

                    String loc = CustomMethods.getLocationFromLatLng(getApplicationContext(), new LatLng(location.getLatitude(), location.getLongitude()));
                    currentLoc.setText(loc);
                } else {
                    Log.d("TAG", "Location Fused null: ");
                    requestForLocationUpdates();
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", "Location Permission is requested: ");
            requestForPermission();

        } else {
            Log.d("TAG", "Location Permission is Granted: ");
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location == null) {
                        Log.d("TAG", "Location onSuccess null: ");
                        requestForLocationUpdates();
                    } else {
                        userLat = location.getLatitude();
                        userLng = location.getLongitude();

                        checkIfUserPassesNearToAnyPlace(location);
                        Log.d("TAG", "Location onSuccess not null: ");
                        String loc = CustomMethods.getLocationFromLatLng(getApplicationContext(), new LatLng(location.getLatitude(), location.getLongitude()));
                        currentLoc.setText(loc);
                    }
                }
            });
        }

    }

    private void checkIfUserPassesNearToAnyPlace(Location location) {
        viewModel.getAllPlaces().observe(this, new Observer<List<Places>>() {
            @Override
            public void onChanged(List<Places> places) {
                if (places.size() > 0) {
                    for (Places place : places) {
                        double placeLat = place.getLatitude();
                        double placeLng = place.getLongitude();

                        double userLat = location.getLatitude();
                        double userLng = location.getLongitude();

                        double distanceInKms = distanceInKms(placeLat, placeLng, userLat, userLng);
                        Log.d("TAG", "distanceInKms: " + distanceInKms);
                        if (distanceInKms < 1) {
                            NotificationModel notification = new NotificationModel(placeLat, placeLng, userLat, userLng, place.getTitle());
                            String message = "You are near to " + place.getTitle() + ", Let's have a visit";
                            CustomMethods.displayNotification(MainActivity.this, notification, message);
                        }
                    }
                }
            }
        });
    }

    @OnClick(R.id.profileImage)
    void onProfileImageClick(View view) {
        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
    }

    private void requestForLocationUpdates() {
        Log.d("TAG", "Location requestLocationUpdates: ");
        LocationRequest request = new LocationRequest();
        request.setInterval(5 * 1000);
        request.setFastestInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", "Location requestLocationUpdates requestd: ");
            requestForPermission();
        } else {
            Log.d("TAG", "Location requestLocationUpdates permission Granted: ");
            fusedLocationProviderClient.requestLocationUpdates(request, mLocationCallback, Looper.myLooper());
        }
    }

    private void requestForPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestForLocationUpdates();
        }
    }

    public void locationStatusCheck() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private double distanceInKms(double hLat, double hLong, double ULat, double ULong) {
        double theta = hLong - ULong;
        double dist = Math.sin(deg2rad(hLat))
                * Math.sin(deg2rad(ULat))
                + Math.cos(deg2rad(hLat))
                * Math.cos(deg2rad(ULat))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist * 1.60934);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}