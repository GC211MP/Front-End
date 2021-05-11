package gachon.hanul.codenamerun;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.text.style.StyleSpan;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.ArrayList;

/*
 * HelpMap이 해야할 역할
 * 1. 실시간으로 지도에 경로를 표시해줘야함
 * 2. 위치 추적(스테이지)이 끝나면 경로를 지워줘야함
 * 3. 현재 위치를 표시해줘야함
 * 4. 데이터베이스에 있는 정보만 넘겨줘도 경로를 그려줘야함
 * 하면좋은거 -> 속도마다 경로 색을 다르게 하면 좋을 듯
 *
 */
public class HelpMap extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private LatLng lastLatLng;
    private Context ctx;

    HelpMap(Context context, Location location) {
        ctx = context;
        lastLatLng = location2LatLng(location);
    }


    // 구글맵이 준비가 되면 이 함수가 시작됨
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(lastLatLng).title("Start"));

        // 현재위치 == 시작위치 를 알아봐서 카메라를 옮겨줘
        Toast.makeText(ctx, lastLatLng.toString(), Toast.LENGTH_SHORT).show();

        // 시작위치로 카메라 옮겨주기
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, 18));


        Intent intent = new Intent("gachon.hanul.codenamerun.local");
        intent.putExtra("onMapReady", true);
        LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent);

    }

    // 위치가 변할때마 콜할 함수를 만들어줘야해
    // 업데이트 해줘여 하는건 카메라 위치랑 폴리라인
    public void updateMAP(Location location, float speed) {
        // Location --> LtnLng로 변환해주고 경로 업데이트
        updatePolyLine(location2LatLng(location), speed);

        // 카메라 옮겨 주고
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, 18));
    }

    /*
     * Location을 LatLng로 변환해주는 함수
     */
    private LatLng location2LatLng(Location loc) {
        LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
        return latLng;
    }

    /*
     * 지도에 경로를 업데이트 해주는 함수
     */
    private void updatePolyLine(LatLng newLatLng, float speed) {
        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(lastLatLng, newLatLng)
                .width(15)
                .endCap(new RoundCap())
                .jointType(JointType.ROUND)
                .color(getSpeedColor(speed)));

        // lastLatLng을 바꿔줘야해
        lastLatLng = newLatLng;
    }

    public void clearMap() {
        mMap.clear();
    }

    public int getSpeedColor(float speed) {
        if (HelpGPS.WALK_SLOW >= speed) { // 시속 3km 이하
            return ContextCompat.getColor(ctx, R.color.very_slow);
        }
        if (HelpGPS.WALK_FAST >= speed) { // 시속 4km 이하
            return ContextCompat.getColor(ctx, R.color.slow);
        }
        if (HelpGPS.RUN_SLOW >= speed) { // 시속 6km 이하
            return ContextCompat.getColor(ctx, R.color.soso);
        }
        if (HelpGPS.RUN_AVG >= speed) { // 시속 8km 이하
            return ContextCompat.getColor(ctx, R.color.fast);
        } else {
            return ContextCompat.getColor(ctx, R.color.very_fast);
        }
    }

}
