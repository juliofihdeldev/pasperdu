package com.pasperdu.app.wapjwennli;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.backendless.Backendless;

import static com.pasperdu.app.wapjwennli.utils.BackendlessSetting.APP_ID;
import static com.pasperdu.app.wapjwennli.utils.BackendlessSetting.SECRET_KEY;
import static com.pasperdu.app.wapjwennli.utils.BackendlessSetting.VERSION;

public class Splash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1500;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // HideNotificationBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash);
        Backendless.initApp(this, APP_ID, SECRET_KEY, VERSION);

        // FacebookSdk.sdkInitialize(getApplicationContext());
        // AppEventsLogger.activateApp(this);

        /*
        if(isNetworkAvailable() == true) {
            final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            final SharedPreferences.Editor editor = prefs.edit();

            String statusDevideId = prefs.getString("alreadyCreateDevice", "no");

            if(statusDevideId.equals("no")){
                //Messaging.DEVICE_ID = "EMULATOR90";
                Backendless.Messaging.registerDevice("151959423209", new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {
                        Log.i("dev", "Devide have been register");
                        Toast.makeText(getApplication(),"Devide have been register", Toast.LENGTH_LONG).show();

                        try {
                            DeviceRegistration devReg = Backendless.Messaging.getDeviceRegistration();
                            if(devReg.equals("")){}else{
                                editor.putString("alreadyCreateDevice", "yes");
                                editor.putString("deviceId", devReg.getDeviceId());

                                editor.commit();

                            }

                        }catch (ClassCastException e) {
                            e.printStackTrace();
                        }

                        PublishOptions publishOptions = new PublishOptions();
                        HashMap<String ,String> headers = new HashMap<String ,String>() ;

                        headers.put( "android-ticker-text", "Job.etrouve!" );
                        headers.put( "android-content-title", "Job.etrouve!" );
                        headers.put( "android-content-text", "Salut! Bienvenue à Job.Etrouve" );
                        publishOptions.setHeaders(headers);

                        DeliveryOptions deliveryOptions = new DeliveryOptions();
                        deliveryOptions.setPushPolicy(PushPolicyEnum.ONLY);
                        Backendless.Messaging.publish( " Salut! Bienvenue à Job.Etrouve ", publishOptions, deliveryOptions );

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.i("dev", "Devide don't register");
                    }
                });
            }else {
                Log.i("dev", "Deja creer ne l'envoi plus");
                // Toast.makeText(this, "Deja creer ne l'envoi plus", Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this, R.string.pas_internet, Toast.LENGTH_SHORT).show();
        }

        */

        if (isNetworkAvailable() == true) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                    checkLogStatusOnSite();
                }
            }, SPLASH_DISPLAY_LENGTH);

        } else {

            Toast.makeText(this, R.string.pas_internet, Toast.LENGTH_SHORT).show();
        }
    }


    public void checkLogStatusOnSite() {
        if (Backendless.UserService.loggedInUser() == "") {
            Intent i = new Intent(getApplicationContext(), Drawer.class);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(getApplicationContext(), Drawer.class);
            startActivity(i);
            finish();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
