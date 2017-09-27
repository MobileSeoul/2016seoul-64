package com.example.eun_a.daum_map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;


public class Map extends AppCompatActivity implements MapView.MapViewEventListener, MapView.CurrentLocationEventListener, MapView.POIItemEventListener {

    private static final String DAUM_API_KEY = "40acec30eb1aa622faec53f649ff77fc";

    boolean isGPSEnabled = false;
    LocationManager locationManager;
    private GPSInfo gps;

    private Context mContext = this;

    DBHelper helper;
    MainActivity mainActivity;

    MapView mapView;
    MapPoint.GeoCoordinate geoCoordinate;

    String hospital;
    public static int count = 0;
    public static ArrayList<MarkerItem> markerItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.example.eun_a.daum_map.R.layout.activity_map);

        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        gps = new GPSInfo(this);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(!isGPSEnabled){
            gps.showGPSSettingsAlert();
        }

        mapView = new MapView(this);
        geoCoordinate = mapView.getMapCenterPoint().getMapPointGeoCoord();  //지리좌표로 변환해주는 기능
        mapView.setDaumMapApiKey(DAUM_API_KEY);
        ViewGroup viewGroup = (ViewGroup) findViewById(com.example.eun_a.daum_map.R.id.map_view);
        viewGroup.addView(mapView);

        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationEventListener(this);
        mapView.setPOIItemEventListener(this);
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());

        mapView.setMapTilePersistentCacheEnabled(true);

        mainActivity = new MainActivity();
        hospital = mainActivity.getInfo();
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

        mapView.setDefaultCurrentLocationMarker();

        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);//현재위치
        mapView.setCurrentLocationEventListener(this);

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onCurrentLocationUpdate(final MapView mapView, MapPoint mapPoint, float v) {

        int count = 0;

        markerItems = new ArrayList<>();

        MapPointBounds mapPointBounds = new MapPointBounds();
        MapPoint.GeoCoordinate geoCoordinate = mapPoint.getMapPointGeoCoord();

        helper = new DBHelper(mContext);

        for (int i = 0; i < helper.size(); i++) {
            markerItems.add(new MarkerItem(helper.hp_info.get(i).latitude, helper.hp_info.get(i).longitude, helper.hp_info.get(i).enName, helper.hp_info.get(i).dept, helper.hp_info.get(i).enLocation, helper.hp_info.get(i).Intro, helper.hp_info.get(i).h_time, helper.hp_info.get(i).url, helper.hp_info.get(i).PN));
        }

        Location locationA = new Location("ponit A");   //DB에서 불러온 경위도
        Location locationB = new Location("ponit B");


        //DB에서 불러온 위치 마커로 찍기
        for (int i = 0; i < markerItems.size(); i++) {
            final double distance;
            final MapPOIItem mapPOIItem = new MapPOIItem();
            final MarkerItem markerItem = markerItems.get(i);

            locationA.setLatitude(markerItem.latitude);
            locationA.setLongitude(markerItem.longitude);

            locationB.setLatitude(geoCoordinate.latitude);
            locationB.setLongitude(geoCoordinate.longitude);


            distance = locationB.distanceTo(locationA);

            final MapPointBounds finalMapPointBounds = mapPointBounds;

            if (markerItem.dept.equals(hospital)) { //선택한 항목만 띄우기
                //반경 내 띄우기
                //if (distance <= 3000) {    //반경 3000미터 이내로 검색
                // Log.i("dd", "DEPT" + markerItem.dept);
                mapPOIItem.setItemName(markerItem.enName);
                mapPOIItem.setTag(i);
                MapPoint point = MapPoint.mapPointWithGeoCoord(markerItem.latitude, markerItem.longitude);
                mapPOIItem.setMapPoint(point);
                finalMapPointBounds.add(point);
                mapPOIItem.setMarkerType(MapPOIItem.MarkerType.CustomImage);
                mapPOIItem.setCustomImageResourceId(R.drawable.placeholder);
                mapPOIItem.setCustomImageAutoscale(false);
                mapPOIItem.setCustomImageAnchor(0.5f, 1.0f);
                mapPOIItem.setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage);
                mapPOIItem.setCustomImageResourceId(R.drawable.placeholder);
                mapPOIItem.setCustomImageAutoscale(false);
                mapPOIItem.setCustomImageAnchor(0.5f, 1.0f);
                mapView.addPOIItem(mapPOIItem);
                // }
                //  else{
                if (count < 3) {
                    Toast.makeText(this, "No Hospital found around 3km radius", Toast.LENGTH_SHORT).show();
                    count++;
                }
                // }

            }
            helper.close();
        }

        MapCircle circle;

        mapView.removeAllCircles();

        circle = new MapCircle(MapPoint.mapPointWithGeoCoord(geoCoordinate.latitude, geoCoordinate.longitude), 500, Color.argb(128, 96, 149, 198), Color.argb(30, 96, 149, 198));
        mapPointBounds = circle.getBound();

        circle.setTag(0);

        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, 50));

        mapView.addCircle(circle);

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    //말풍선 터치했을때 이벤트 실행
    public static String hp_name, hp_pn, hp_intro, hp_location, hp_time, hp_url;

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

        hp_name = markerItems.get(mapPOIItem.getTag()).enName;
        hp_intro = markerItems.get(mapPOIItem.getTag()).Intro;
        hp_location = markerItems.get(mapPOIItem.getTag()).enLocation;
        hp_pn = markerItems.get(mapPOIItem.getTag()).PN;
        hp_time = markerItems.get(mapPOIItem.getTag()).h_time;
        hp_url = markerItems.get(mapPOIItem.getTag()).url;

        if (hospital.equals("Dermatology")) {
            Intent intent = new Intent(getApplicationContext(), DermatologyActivity.class);
            startActivity(intent);
        }
        if (hospital.equals("Dental")) {
            Intent intent = new Intent(getApplicationContext(), DentalActivity.class);
            startActivity(intent);
        }
        if (hospital.equals("Eye")) {
            Intent intent = new Intent(getApplicationContext(), EyeActivity.class);
            startActivity(intent);
        }
        if (hospital.equals("Hospital")) {
            Intent intent = new Intent(getApplicationContext(), HospitalActivity.class);
            startActivity(intent);
        }
        if (hospital.equals("Oriental Medicine")) {
            Intent intent = new Intent(getApplicationContext(), OrientalMedicineActivity.class);
            startActivity(intent);
        }
        if (hospital.equals("Plastic Surgery")) {
            Intent intent = new Intent(getApplicationContext(), PlasticSurgeryActivity.class);
            startActivity(intent);
        }
        if (hospital.equals("Radiology")) {
            Intent intent = new Intent(getApplicationContext(), RadiologyActivity.class);
            startActivity(intent);
        }
        if (hospital.equals("Urology")) {
            Intent intent = new Intent(getApplicationContext(), UrologyActivity.class);
            startActivity(intent);
        }
        if (hospital.equals("Womans")) {
            Intent intent = new Intent(getApplicationContext(), WomansActivity.class);
            startActivity(intent);
        }
        if (hospital.equals("Clinic")) {
            Intent intent = new Intent(getApplicationContext(), ClinicActivity.class);
            startActivity(intent);
        }

    }

    private void showGpsOption() {
        Intent gpsI = new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(gpsI);
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {

        @Override //말풍선 바꾸기
        public View getCalloutBalloon(MapPOIItem mapPOIItem) {
            if (mapPOIItem == null) {
                return null;
            }

            return null;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem mapPOIItem) {
            return null;
        }

    }

    public class MarkerItem {
        public double latitude;
        public double longitude;
        String enName;
        String dept;
        String enLocation;
        String Intro;
        String h_time;
        String url;
        String PN;

        public MarkerItem(double latitude, double longitude, String enName, String Dept, String enLocation, String Introduce, String Time, String Url, String PN) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.enName = enName;
            this.dept = Dept;
            this.enLocation = enLocation;
            this.Intro = Introduce;
            this.h_time = Time;
            this.url = Url;
            this.PN = PN;
        }
    }
}