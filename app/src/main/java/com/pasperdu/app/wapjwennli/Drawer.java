package com.pasperdu.app.wapjwennli;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.pasperdu.app.wapjwennli.add.AjouterObjetRetrouve;
import com.pasperdu.app.wapjwennli.add.SignalerObjetPerdu;
import com.pasperdu.app.wapjwennli.fragment.ListPerduFragment;
import com.pasperdu.app.wapjwennli.fragment.SearchFragment;
import com.squareup.picasso.Picasso;

import static com.pasperdu.app.wapjwennli.utils.BackendlessSetting.APP_ID;
import static com.pasperdu.app.wapjwennli.utils.BackendlessSetting.SECRET_KEY;
import static com.pasperdu.app.wapjwennli.utils.BackendlessSetting.VERSION;

public class Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView tvName,tvEmail;
    ImageView imHeader;
    NavigationView nvDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Backendless.initApp(this, APP_ID, SECRET_KEY, VERSION);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer=(NavigationView)findViewById(R.id.nav_view);

        // Setup drawer view
        tvName = (TextView) nvDrawer.getHeaderView(0).findViewById(R.id.tvName);
        tvEmail= (TextView) nvDrawer.getHeaderView(0).findViewById(R.id.tvEmail);
        imHeader= (ImageView) nvDrawer.getHeaderView(0).findViewById(R.id.imHeader);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String valueName = extras.getString("completName");
            String valueId = extras.getString("id");
            String Valueimage = extras.getString("image");
            //The key argument here must match that used in the other activity
            tvName.setText(valueName);
            tvEmail.setText(valueId);
            Picasso.with(Drawer.this).load(Valueimage).resize(200 ,200).centerCrop().placeholder(R.drawable.users_icon).into(imHeader);

        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (findViewById(R.id.flContent) != null) {
            if (savedInstanceState != null) {
                return;
            }
            ListPerduFragment listPerduFragment = new ListPerduFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.flContent, listPerduFragment).commit();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // searchItem.expandActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here

                String close = query;

                Fragment fragment = null;
                Class fragmentClass = null;

                fragment = new SearchFragment();
                Bundle search = new Bundle();
                search.putString("searchKey",close);
                fragment.setArguments(search);
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        /*
            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }
        */
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        /*
        Fragment fragment = null;
        Class fragmentClass = null;
        */
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
           // fragmentClass = AddDoc.class;
            Intent intent =  new Intent(Drawer.this, SignalerObjetPerdu.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            // fragmentClass = AddDoc.class;
            Intent add =  new Intent(Drawer.this, AjouterObjetRetrouve.class);
            startActivity(add);

        } else if (id == R.id.nav_slideshow) {
           // fragmentClass = AddDoc.class;
        } else if (id == R.id.nav_share) {
           // fragmentClass = AddDoc.class;
        } else if (id == R.id.nav_send) {
           // fragmentClass = AddDoc.class;
        } else if (id == R.id.list_signaler) {
            // fragmentClass = AddDoc.class;
            Intent seeList =  new Intent(Drawer.this, ListeSignaler.class);
            startActivity(seeList);
        }

    /*
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
