package com.pasperdu.app.wapjwennli;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;


public class FBLogin extends AppCompatActivity {
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    private ProfileTracker profileTracker;
    public ImageView profilFacebook;
    public Button btnContinuer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fblogin);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        profilFacebook = (ImageView) findViewById(R.id.profilFacebook);
        btnContinuer = (Button) findViewById(R.id.btnContinuer);
        //btnContinuer.setVisibility(View.GONE);
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("public_profile");
        loginButton.setReadPermissions("email");
        loginButton.setReadPermissions("user_birthday");
        callbackManager = CallbackManager.Factory.create();
        // If the access token is available already assign it.

        accessToken = AccessToken.getCurrentAccessToken();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.d("Informations",loginResult.toString());

                        //Toast.makeText(FBLogin.this, "Deja inscrit", Toast.LENGTH_SHORT).show();
                        info = (TextView)findViewById(R.id.info);
                        /*

                            info.setText(
                                "User ID: "
                                        + loginResult.getAccessToken().getUserId()
                                        + "\n" +
                                        "Auth Token: "
                                        + loginResult.getAccessToken().getToken()
                            );
                        */

                        accessTokenTracker = new AccessTokenTracker() {
                            @Override
                            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

                            }
                        };

                        profileTracker = new ProfileTracker() {
                            @Override
                            protected void onCurrentProfileChanged(Profile oldProfile, final Profile currentProfile) {

                                final String img = String.valueOf(currentProfile.getProfilePictureUri(400,400));
                                Picasso.with(FBLogin.this).load(img).resize(400 ,400).centerCrop().placeholder(R.drawable.users_icon).into(profilFacebook);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(FBLogin.this, Drawer.class);
                                        i.putExtra("id",currentProfile.getId());
                                        i.putExtra("completName",currentProfile.getFirstName()+currentProfile.getLastName());
                                        i.putExtra("image",img);

                                        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(FBLogin.this);
                                        // save the pair into shared preferences
                                        final SharedPreferences.Editor editor = settings.edit();
                                        editor.putString("id",currentProfile.getId());
                                        editor.putString("completName",currentProfile.getFirstName()+currentProfile.getLastName());
                                        editor.putString("imageProfil",img);
                                        editor.commit();
                                        startActivity(i);
                                        finish();
                                    }
                                }, 1000);

                            }
                        };

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onNext(View view){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(FBLogin.this);
        String id = settings.getString("id"," noId");
        String name = settings.getString("completName","noName");
        String img = settings.getString("imageProfil","noImage");

        Intent i = new Intent(FBLogin.this, Drawer.class);
        i.putExtra("id",id);
        i.putExtra("completName",name);
        i.putExtra("image",img);
        startActivity(i);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //accessTokenTracker.stopTracking();
    }
}
