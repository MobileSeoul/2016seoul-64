package com.example.eun_a.daum_map;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Eun_A on 2016-10-04.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final String ROOT_DIR = "/data/data/com.example.eun_a.daum_map/databases";
    public SQLiteDatabase db;
    private Context mContext;
    public Cursor cursor = null;
    private static final String TableName = "medicaltour";
    private static final String DBName = "korea.db";
    public ArrayList<HP>hp_info = new ArrayList<>();
    public  int i=0;


    public DBHelper(Context mContext) {
        super(mContext, DBName, null, 1);

        this.mContext = mContext;
        setDB();
        db=mContext.openOrCreateDatabase("korea.db",mContext.MODE_PRIVATE,null);
        setCursor();
    }

    public void setDB() {
        File folder = new File(ROOT_DIR);

        if (folder.exists()) {
            Log.i("aa", "setDB: 존재하는 폴더");
        } else {
            folder.mkdirs();
            //Toast.makeText(this, "폴더생성", Toast.LENGTH_LONG).show();
        }

        File outfile = new File(ROOT_DIR + "/korea.db");
        InputStream is = null;
        FileOutputStream fo = null;
        long filesize = 0;

        try {
            // --asset 폴더 및 복사할 DB 지정
            AssetManager assetManager = mContext.getResources().getAssets();
            is = assetManager.open("korea.db", AssetManager.ACCESS_BUFFER);
            filesize = is.available(); //--사이즈 검증

            //파일이 없거나 패키지 폴더에 설치된 DB파일이 포함된 DB파일 보다 크기가 같지않을 경우 DB파일을 덮어 쓴다.
            if (outfile.length() <= 0) {
                byte[] tempdata = new byte[(int) filesize];
                is.read(tempdata);
                is.close();
                outfile.createNewFile();
                fo = new FileOutputStream(outfile);
                fo.write(tempdata);
                fo.close();
            } else {
                //Toast.makeText(this, "db있음", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            //Toast.makeText(this, "db이동실패", Toast.LENGTH_LONG).show();
        }

    }

    public void setCursor() {
        String enName;
        String Dept;
        double latitude;
        double longitude;
        String enLocation;
        String h_time;
        String url;
        String PN;
        String Intro;
        int id;

        db = getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + TableName, null);
        cursor.moveToFirst();

        if(cursor != null && cursor.getCount() !=0) {
            do {
                enName = cursor.getString(2);
                Dept = cursor.getString(1);
                latitude=Double.parseDouble(cursor.getString(8));
                longitude=Double.parseDouble(cursor.getString(9));
                enLocation = cursor.getString(3);
                Intro = cursor.getString(4);
                h_time = cursor.getString(5);
                url = cursor.getString(6);
                PN = cursor.getString(7);
                id = Integer.parseInt(cursor.getString(0));

                // /위도, 경도, 병원 영어이름, 카테고리
                hp_info.add(new HP(latitude, longitude, enName, Dept, enLocation, Intro, h_time, url,PN,id));
                i++;
            } while (cursor.moveToNext());
        }else {}

        cursor.close();
        db.close();
    }

    public ArrayList<HP> getHP(){
        return hp_info;
    }

    public int size(){
       return hp_info.size();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public class HP {
        double latitude;
        double longitude;
        String enName;
        String dept;
        String enLocation;
        String Intro;
        String h_time;
        String url;
        String PN;
        int id;

        public HP(double latitude, double longitude, String enName, String Dept,String enLocation, String Introduce, String Time, String Url, String PN, int id) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.enName = enName;
            this.dept = Dept;
            this.enLocation = enLocation;
            this.Intro = Introduce;
            this.h_time = Time;
            this.url = Url;
            this.PN = PN;
            this.id = id;
        }

       public double getLatitude(){
            return latitude;
        }
       public void setLatitude(double latitude){
            this.latitude=latitude;
        }
       public double getLongitude(){
            return longitude;
        }
       public void setLongitude(double longitude){
            this.longitude=longitude;
        }
       public String getEnName(){
            return enName;
        }
       public void setEnName(String enName) {
           this.enName=enName;
        }
       public void setDept(String Dept){this.dept=Dept;}
       public String getDept(){return dept;}
       public String getEnLocation(){return enLocation;}
       public String getIntro() {return Intro;}
       public String getH_time() {return h_time;}
       public String getUrl() {return url;}
       public String getPN() {return PN;}
    }

}
