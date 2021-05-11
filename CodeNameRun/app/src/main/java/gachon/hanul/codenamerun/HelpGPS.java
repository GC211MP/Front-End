package gachon.hanul.codenamerun;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;

import java.util.ArrayList;

/*
 * 이 클래스가 해줘야할 역할
 * 1. 위치정보를 받아와야함 -> 완료
 * 2. 위치정보가 바뀔때마다 지도정보를 바꿀 수 있게 콜 해줘야함 -> 완료
 * 3. 속도가 떨어지면 알려줘야해 --> 완료
 * 4. 스테이지가 끝난걸 알려주면 디비에 속 정보를 넣어줘야해
 *
 */
public class HelpGPS extends Service implements LocationListener {


    private static final long MIN_DISTANCE_UPDATES = 10; // 10미터
    private static final long MIN_TIME_UPDATES = 5000; // 5초

    // 찐
//    public static final float WALK_SLOW = (float)0.833; // 3km/h
//    public static final float WALK_FAST = (float)1.11111; // 4km/h
//    public static final float RUN_SLOW = (float)1.66667; // 6km/h
//    public static final float RUN_AVG = (float)2.72; // 10km/h
//    public static final float RUN_FAST = (float)4.16; // 15km/h

    // 테스트용
    public static final float WALK_SLOW = (float) 1.5;
    public static final float WALK_FAST = (float) 3.0;
    public static final float RUN_SLOW = (float) 4.5;
    public static final float RUN_AVG = (float) 6.0;
    public static final float RUN_FAST = (float) 7.0;

    public static final String LOG_HELP_GPS = "speed_check";
    public static final String MSG_SLOW = "limit_speed_slow";
    public static final String MSG_COMPLETE = "complete";


    protected LocationManager locationManager;
    private final Context mContext;
    private HelpMap helpMap;
    private ArrayList<Location> locationList;
    private ArrayList<Float> speedList; // m/s
    private float minSpeed; // m/s
    private long targetTime;
    private long lastTime;
    private long nowTime;
    private float calories = 0;
    private float distance = 0;

    public HelpGPS(Context context) {
        this.mContext = context;
        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

        locationList = new ArrayList<Location>();
        speedList = new ArrayList<Float>();
        locationList.add(getLocation());
        speedList.add((float) 0.0);
        lastTime = System.currentTimeMillis();
    }

    /*
     * 위치정보가 바뀔때마다 알려주는 리스너 함수
     * 1. 바뀌면 어레이 정보를 업데이트
     * 2. 헬프맵을 콜해서 지도 업데이트
     */
    @Override
    public void onLocationChanged(Location location) {
        locationList.add(getLocation());
        speedList.add(location.getSpeed());
        helpMap.updateMAP(location, speedList.get(speedList.size() - 1));
        nowTime = System.currentTimeMillis();
        calories += calculateCalories(nowTime - lastTime, getLastSpeed());
        distance += calculateDistance(nowTime - lastTime, getLastSpeed());

        // 최소 속도보다 속도가 느리면 로컬방송으로 알려줌
        if (minSpeed > getLastSpeed()) {
            sendMSGSpeedIsSlow();
            Log.d(LOG_HELP_GPS, "speed is slow");
        }


        Log.d(LOG_HELP_GPS, Double.toString(speedList.get(speedList.size() - 1)));
        Log.d(LOG_HELP_GPS, "now limit: " + Float.toString(minSpeed) + "speed: " + Float.toString(getLastSpeed()));
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
        Location new_location = null;
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
                    if (new_location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_UPDATES, MIN_DISTANCE_UPDATES, this);
                        if (locationManager != null)
                            new_location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }

                }
                if (isGPSEnabled) {
                    if (new_location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_UPDATES, MIN_DISTANCE_UPDATES, this);
                        if (locationManager != null)
                            new_location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }
            }
        } catch (Exception e) {
            Log.d("@@@", "" + e.toString());
        }

        return new_location;
    }


    public void setHelpMap(HelpMap helpMap) {
        this.helpMap = helpMap;
    }

    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(HelpGPS.this);
        }
    }

    public float getLastSpeed() {
        return speedList.get(speedList.size() - 1);
    }

    public Location getLastLocation() {
        return locationList.get(locationList.size() - 1);
    }

    public void setMinSpeed(float speed) {
        this.minSpeed = speed;
    }

    public void setRemainTime(int sec) {
        targetTime = System.currentTimeMillis() + sec * 1000;
        Log.d(LOG_HELP_GPS, "setRemainTime");

    }

    private void sendMSGSpeedIsSlow() {
        Intent intent = new Intent("gachon.hanul.codenamerun.local");
        intent.putExtra(MSG_SLOW, false);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    private void sendMSGTimeIsDone() {
        Intent intent = new Intent("gachon.hanul.codenamerun.local");
        intent.putExtra(MSG_COMPLETE, true);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }


    private float calculateDistance(long time, float speed) {
        return time * speed / 1000;
    }

    private float calculateCalories(long time, float speed) {
        float cal = 0;

        if (speed < RUN_SLOW) {
            cal = (time * speed) / 1000;

        } else {
            cal = (time * speed) / 800;
        }
        return cal;
    }

    public int getCalories() {
        return (int) calories;
    }

    public int getDistance() {
        return (int) distance;
    }
}