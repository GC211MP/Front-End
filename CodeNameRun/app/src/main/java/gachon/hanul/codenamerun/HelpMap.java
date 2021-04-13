package gachon.hanul.codenamerun;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
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

public class HelpMap extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;

    private LatLng lastLatLng ;
    private ArrayList<Polyline> polylines;
    private Context ctx;

    HelpMap(Context context, Location location){
        ctx = context;
        lastLatLng = location2LatLng(location);
        polylines = new ArrayList<Polyline>();
    }


    // 구글맵이 준비가 되면 이 함수가 시작됨
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(lastLatLng).title("Start"));

        // 현재위치 == 시작위치 를 알아봐서 카메라를 옮겨줘
        Toast.makeText(ctx, lastLatLng.toString(),Toast.LENGTH_SHORT).show();

        // 시작위치로 카메라 옮겨주기
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, 18));

    }

    // 위치가 변할때마 콜할 함수를 만들어줘야해
    // 업데이트 해줘여 하는건 카메라 위치랑 폴리라인
    public void updateMAP(Location location){
        // Location --> LtnLng로 변환해주고 경로 업데이트
        updatePolyLine(location2LatLng(location));

        // 카메라 옮겨 주고
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, 18));
    }

    /*
     * Location을 LatLng로 변환해주는 함수
     */
    private LatLng location2LatLng(Location loc){
         LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
         return latLng;
    }

    /*
     * 지도에 경로를 업데이트 해주는 함수
     */
    private void updatePolyLine(LatLng newLatLng){

        // 여기 옵션을 잘 손대면 그 속도별로 색깔을 다르게 할 수 있을 것 같아요
        PolylineOptions options = new PolylineOptions().add(lastLatLng).add(newLatLng).width(15).geodesic(true);
        polylines.add(mMap.addPolyline(options));

        // lastLatLng을 바꿔줘야해
        lastLatLng = newLatLng;
    }


}
