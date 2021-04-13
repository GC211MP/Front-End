package gachon.hanul.codenamerun;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.widget.Toast;

import java.awt.font.NumericShaper;
import java.util.ArrayList;

/*
 * 이 클래스가 해줘야할 역할
 * 1. 위치정보를 받아와야함
 * 2. 위치정보가 바뀔때마다 지도정보를 바꿀 수 있게 콜 해줘야함
 * 3. 속도가 떨어지면 알려줘야해 --> 이거 어케 구현하지?
 * 4. 스테이지가 끝난걸 알려주면 디비에 속 정보를 넣어줘야해
 *
 */
public class HelpGPS extends Service implements LocationListener {


    private static final long MIN_DISTANCE_UPDATES = 10;
    private static final long MIN_TIME_UPDATES = 3000; // 3 seconds
    protected LocationManager locationManager;

    private final Context mContext;
    private Location location;
    private double latitude;
    private double longitude;
    private double altitude;


    private HelpMap helpMap;
    private ArrayList<Location> location_list;

    public HelpGPS(Context context) {
        this.mContext = context;
        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

        location_list = new ArrayList<Location>();
        getLocation();

        location_list.add(location);
    }

    /*
     * 위치정보가 바뀔때마다 알려주는 리스너 함수
     * 1. 바뀌면 어레이 정보를 업데이트
     * 2. 헬프맵을 콜해서 지도 업데이트
     */
    @Override
    public void onLocationChanged(Location location) {
        location_list.add(location);
        helpMap.updateMAP(location);

    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public Location getLocation() {
        try {


            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            // Defensive Coding
            if (!isGPSEnabled && !isNetworkEnabled) {

            } else {
                int hasFineLocationPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
                int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION);

                if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
                    ;
                } else return null;


                if (isNetworkEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_UPDATES, MIN_DISTANCE_UPDATES, this);
                        if (locationManager != null)
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }

                }
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_UPDATES, MIN_DISTANCE_UPDATES, this);
                        if (locationManager != null)
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }
            }
        } catch (Exception e) {
            Log.d("@@@", "" + e.toString());
        }

        return location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String locationToString() {
        return Double.toString(latitude) + "/" + Double.toString(longitude) +
                "/" + Double.toString(altitude);
    }

    public void setHelpMap(HelpMap helpMap) {
        this.helpMap = helpMap;
    }

    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(HelpGPS.this);
        }
    }


}