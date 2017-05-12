package com.pasperdu.app.wapjwennli;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.pasperdu.app.wapjwennli.adapters.SignalerAdapters;
import com.pasperdu.app.wapjwennli.models.Signaler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class ListeSignaler extends AppCompatActivity {

    private ArrayList<Signaler> ListPiece;
    private SignalerAdapters pieceAdapters;
    ListView lvPiece;
    ProgressBar pd ;
    ProgressDialog progress;
    private Unbinder unbinder;
    private SwipeRefreshLayout swipeContainer;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_signaler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.liste_ojet_signale);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        unbinder = ButterKnife.bind(this);
        lvPiece = (ListView) findViewById(R.id.lvPiece);
        pd = (ProgressBar) findViewById(R.id.progressBar);
        ListPiece = new ArrayList<>();

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);



        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                // Do work to refresh the list here.
                findPiece();
                // new Task().execute();
                pieceAdapters.clear();
            }
        });
        findPiece();

    }
    public void showLoading(){
        progress = ProgressDialog.show(ListeSignaler.this, "Deconnexion","Patientez...",false,false);
    }
    public void dimissLoading(){
        progress.dismiss();
    }

    public void findPiece() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        pieceAdapters = new SignalerAdapters(this, ListPiece);
        lvPiece.setAdapter(pieceAdapters);

        String whereClause = "etat ='1' ";
        final int PAGESIZE = 98;
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize(PAGESIZE);
        dataQuery.setWhereClause(whereClause);
        // backendless Categorie
        Backendless.Persistence.of(Signaler.class).find(dataQuery, new AsyncCallback<BackendlessCollection<Signaler>>() {
            @Override
            public void handleResponse(BackendlessCollection<Signaler> foundPiece) {
                Iterator<Signaler> PieceIterator = foundPiece.getCurrentPage().iterator();
                while (PieceIterator.hasNext()) {
                    Signaler newPiece = PieceIterator.next();
                    ListPiece.add(newPiece);
                }

                pieceAdapters.notifyDataSetChanged();
                pd.setVisibility(View.GONE);
                int size = foundPiece.getCurrentPage().size();

                if (size <= 0) {
                    /*
                    new AlertDialog.Builder(getApplicationContext())
                            .setTitle("Objet perdu")
                            .setMessage("Tous les objets perdus ont été signaler par leur propriétaire")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dimissLoading();
                                }
                            })
                            .setIcon(R.drawable.ic_action_signaler)
                            .show();
                    */
                    Toast.makeText(ListeSignaler.this, "Pas objet perdu a. signaler", Toast.LENGTH_SHORT).show();
                    finish();
                }
                mWaveSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                //progress.setVisibility(View.GONE);
                // Toast.makeText(getContext(), " No connexion internet  ", Toast.LENGTH_LONG).show();
                Toast.makeText(ListeSignaler.this, R.string.pas_internet, Toast.LENGTH_SHORT).show();
                if (fault.getCode().equals("3064")) {

                }
            }
        });

        /*
        * Voir la liste des canditats pour cette offre
        * */
        // contexte menu
        registerForContextMenu(lvPiece);
        lvPiece.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Signaler detailsPiece = ListPiece.get(position);
                Intent i = new Intent(ListeSignaler.this, DetailsPiece.class);
                i.putExtra("detailsJob", detailsPiece);
                startActivity(i);
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Modifier");
        menu.add(0, v.getId(), 0, "Supprimer");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Signaler detailsPiece;
        if (item.getTitle() == "Supprimer") {
            detailsPiece = ListPiece.get(info.position);
            // deleteExperience(experience);
            // Toast.makeText(getContext(), "  ", Toast.LENGTH_SHORT).show();

        }else if(item.getTitle() == "Modifier"){
            detailsPiece = ListPiece.get(info.position);
            // deleteExperience(experience);
            // Intent i = new Intent(getContext(), Edit_Piece.class);
            // i.putExtra("detailsJob", detailsPiece);
            // startActivity(i);
            Toast.makeText(ListeSignaler.this, "Change son etat si ce deja fait", Toast.LENGTH_SHORT).show();
        }
        else {

            return false;
        }
        return true;
    }

    public void fetchTimelineAsync(int page) {
        pieceAdapters.clear();

        findPiece();
        // jobAdapter.clear();

        swipeContainer.setRefreshing(false);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
