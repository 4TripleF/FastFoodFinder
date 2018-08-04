package com.iceteaviet.fastfoodfinder.ui.store;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.iceteaviet.fastfoodfinder.App;
import com.iceteaviet.fastfoodfinder.R;
import com.iceteaviet.fastfoodfinder.data.DataManager;
import com.iceteaviet.fastfoodfinder.data.remote.routing.model.MapsDirection;
import com.iceteaviet.fastfoodfinder.data.remote.store.model.Comment;
import com.iceteaviet.fastfoodfinder.data.remote.store.model.Store;
import com.iceteaviet.fastfoodfinder.ui.routing.MapRoutingActivity;
import com.iceteaviet.fastfoodfinder.utils.Constant;
import com.iceteaviet.fastfoodfinder.utils.FormatUtils;
import com.iceteaviet.fastfoodfinder.utils.LocationUtils;
import com.iceteaviet.fastfoodfinder.utils.PermissionUtils;
import com.iceteaviet.fastfoodfinder.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by taq on 18/11/2016.
 */

public class StoreDetailActivity extends AppCompatActivity implements StoreDetailAdapter.StoreActionListener, GoogleApiClient.ConnectionCallbacks {

    public static final int REQUEST_COMMENT = 113;
    private static final String TAG = StoreDetailActivity.class.getSimpleName();

    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.backdrop)
    ImageView ivBackdrop;
    @BindView(R.id.content)
    RecyclerView rvContent;

    private LatLng currLocation;
    private LocationRequest mLocationRequest;
    private Store currentStore;
    private GoogleApiClient googleApiClient;
    private SupportMapFragment mMapFragment;
    private GoogleMap mGoogleMap;
    private StoreDetailAdapter mStoreDetailAdapter;
    private DataManager dataManager;

    public static Intent getIntent(Context context, Store store) {
        Intent intent = new Intent(context, StoreDetailActivity.class);
        intent.putExtra(Constant.STORE, store);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        ButterKnife.bind(this);

        currentStore = getIntent().getParcelableExtra(Constant.STORE);
        mStoreDetailAdapter = new StoreDetailAdapter(currentStore);
        mStoreDetailAdapter.setListener(this);
        rvContent.setAdapter(mStoreDetailAdapter);
        rvContent.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar.setTitle(currentStore.getTitle());

        Glide.with(this)
                .load(R.drawable.detail_sample_circlekcover)
                .apply(new RequestOptions().centerCrop())
                .into(ivBackdrop);

        dataManager = App.getDataManager();
        mLocationRequest = LocationUtils.createLocationRequest();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.e(TAG, getString(R.string.cannot_get_curr_location));
                    }
                })
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_COMMENT) {
            Comment comment = (Comment) data.getSerializableExtra(Constant.COMMENT);
            if (comment != null) {
                mStoreDetailAdapter.addComment(comment);
                appbar.setExpanded(false);
                rvContent.scrollToPosition(3);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PermissionUtils.REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                } else {
                    Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
                }
                return;
            }

            default:
                break;
        }
    }

    @Override
    public void onShowComment() {
        startActivityForResult(new Intent(this, CommentActivity.class), REQUEST_COMMENT);
    }

    @Override
    public void onCall(String tel) {
        if (!StringUtils.isEmpty(tel)) {
            startActivity(FormatUtils.getCallIntent(tel));
        } else {
            Toast.makeText(this, R.string.store_no_phone_numb, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDirect() {
        LatLng storeLocation = currentStore.getPosition();
        Map<String, String> queries = new HashMap<>();

        queries.put("origin", LocationUtils.getLatLngString(currLocation));
        queries.put("destination", LocationUtils.getLatLngString(storeLocation));

        dataManager.getMapsRoutingApiHelper().getMapsDirection(queries, currentStore)
                .subscribe(new SingleObserver<MapsDirection>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(MapsDirection mapsDirection) {
                        Intent intent = new Intent(StoreDetailActivity.this, MapRoutingActivity.class);
                        Bundle extras = new Bundle();
                        extras.putParcelable(Constant.KEY_ROUTE_LIST, mapsDirection);
                        extras.putParcelable(Constant.KEY_DES_STORE, currentStore);
                        intent.putExtras(extras);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void onAddToFavorite(int storeId) {
        //TODO gọi hàm lưu vào danh sách yêu thích
        Toast.makeText(this, getString(R.string.fav_stores_added) + storeId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckIn(int storeId) {
        //TODO gọi hàm check in
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (PermissionUtils.isLocationPermissionGranted(this)) {
            getCurrentLocation();
        } else {
            PermissionUtils.requestLocationPermission(this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, R.string.cannot_connect_location_service, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currLocation = new LatLng(location.getLatitude(), location.getLongitude());
                // Creating a LatLng object for the current location
            }
        });

        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            currLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        } else
            Toast.makeText(StoreDetailActivity.this, R.string.cannot_get_curr_location, Toast.LENGTH_SHORT).show();
    }
}
