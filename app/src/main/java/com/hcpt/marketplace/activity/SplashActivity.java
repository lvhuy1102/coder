package com.hcpt.marketplace.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.hcpt.marketplace.BaseActivity;
import com.hcpt.marketplace.R;
import com.hcpt.marketplace.activity.tabs.MainTabActivity;
import com.hcpt.marketplace.modelmanager.ErrorNetworkHandler;
import com.hcpt.marketplace.modelmanager.ModelManager;
import com.hcpt.marketplace.modelmanager.ModelManagerListener;
import com.hcpt.marketplace.network.ParserUtility;
import com.hcpt.marketplace.object.Category;
import com.hcpt.marketplace.object.City;
import com.hcpt.marketplace.util.GPSTracker;
import com.hcpt.marketplace.util.MySharedPreferences;
import com.hcpt.marketplace.util.NetworkUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends BaseActivity {

    private GPSTracker gps;
    private Handler handler = new Handler();
    private boolean isLoadCity = false, isLoadCategories = false;

    List<String> listPermission = new ArrayList<>();
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    //check load success
    Runnable r = new Runnable() {
        @Override
        public void run() {
            if (isLoadCity && isLoadCategories) {
                gotoActivity(MainTabActivity.class);
                finish();
            } else {
                handler.postDelayed(this, 1000);
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // enable strict mode
        if (Build.VERSION.SDK_INT>=23){
            if (checkAndRequestPermissions()){
                NetworkUtil.enableStrictMode();
                printKeyHash(this);
                gps = new GPSTracker(this);
                if (NetworkUtil.checkNetworkAvailable(this)) {
                    checkLocation();
                } else {
                    NetworkUtil.showSettingsAlert(this);
                }
            }
        }else {
            NetworkUtil.enableStrictMode();
            printKeyHash(this);
            gps = new GPSTracker(this);
            if (NetworkUtil.checkNetworkAvailable(this)) {
                checkLocation();
            } else {
                NetworkUtil.showSettingsAlert(this);
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }

    private void checkLocation() {
        if (!gps.canGetLocation()) {
            gps.showSettingsAlert();
        } else {
            loadStaticData();
        }
    }


    //load city & category list
    private void loadStaticData() {
        ModelManager.getListCity(SplashActivity.this, true, new ModelManagerListener() {

            @Override
            public void onSuccess(Object object) {
                String json = (String) object;
                if (ParserUtility.isSuccess(json))
                    new MySharedPreferences(self).setCacheCities(json);
                isLoadCity = true;
            }

            @Override
            public void onError(VolleyError error) {
                // TODO Auto-generated method stub
                isLoadCity = true;
            }
        });

        ModelManager.getListCategory(SplashActivity.this, true, new ModelManagerListener() {

            @Override
            public void onSuccess(Object object) {
                String json = (String) object;
                if (ParserUtility.isSuccess(json))
                    new MySharedPreferences(self).setCacheCategories(json);
                isLoadCategories = true;
            }

            @Override
            public void onError(VolleyError error) {
                isLoadCategories = true;
            }
        });

        //checked loading data
        handler.post(r);

    }


    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", "Key Hashes:" + context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    private boolean checkAndRequestPermissions() {
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        for ( String permission: permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                listPermission.add(permission);
            }
        }

        if (!listPermission.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermission.toArray
                    (new String[listPermission.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                Boolean allPermissionsGranted  = true;
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            allPermissionsGranted  = false;
                            break;
                        }
                    }
                    if (!allPermissionsGranted) {
                        boolean somePermissionsForeverDenied = false;
                        boolean checkAllPermissonAllAlowed= true;
                        for(String permission: permissions){
                            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
                                //denied
                                checkAndRequestPermissions();
                                checkAllPermissonAllAlowed = false;
                                Log.e("denied", permission);
                            }else{
                                if(ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED){
                                    //allowed
                                    Log.e("allowed", permission);
                                } else{
                                    //set to never ask again
                                    Log.e("set to never ask again", permission);
                                    somePermissionsForeverDenied = true;
                                    checkAllPermissonAllAlowed = false;
                                    break;
                                }
                            }
                        }

                        if(somePermissionsForeverDenied){
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                            alertDialogBuilder.setTitle("Permissions Required")
                                    .setMessage("You have forcefully denied some of the required permissions " +
                                            "for this action. Please open settings, go to permissions and allow them.")
                                    .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                    Uri.fromParts("package", getPackageName(), null));
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            System.exit(1);
                                        }
                                    })
                                    .setCancelable(false)
                                    .create()
                                    .show();
                        }
                        if (checkAllPermissonAllAlowed){
                            NetworkUtil.enableStrictMode();
                            printKeyHash(this);
                            gps = new GPSTracker(this);
                            if (NetworkUtil.checkNetworkAvailable(this)) {
                                checkLocation();
                            } else {
                                NetworkUtil.showSettingsAlert(this);
                            }
                        }
                    } else {
                        NetworkUtil.enableStrictMode();
                        printKeyHash(this);
                        gps = new GPSTracker(this);
                        if (NetworkUtil.checkNetworkAvailable(this)) {
                            checkLocation();
                        } else {
                            NetworkUtil.showSettingsAlert(this);
                        }
                    }
                }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkAndRequestPermissions();
    }

}
