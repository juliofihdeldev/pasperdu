package com.pasperdu.app.wapjwennli.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.pasperdu.app.wapjwennli.DetailsPiece;
import com.pasperdu.app.wapjwennli.R;
import com.pasperdu.app.wapjwennli.adapters.PieceAdapters;
import com.pasperdu.app.wapjwennli.models.Piece;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class ListPerduFragment extends Fragment {
    public ListPerduFragment() {
        // Required empty public constructor
    }

    private ArrayList<Piece> ListPiece;
    private PieceAdapters pieceAdapters;
    ListView lvPiece;
    ProgressBar pd ;
    ProgressDialog progress;
    private Unbinder unbinder;
    private SwipeRefreshLayout swipeContainer;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_perdu, container, false);

        unbinder = ButterKnife.bind(this, v);
        lvPiece = (ListView) v.findViewById(R.id.lvPiece);
        pd = (ProgressBar) v.findViewById(R.id.progressBar);
        ListPiece = new ArrayList<>();

        return v ;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Setup refresh listener which triggers new data loading
        /*
            swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
            swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // Your code to refresh the list here.
                    // Make sure you call swipeContainer.setRefreshing(false)
                    // once the network request has completed successfully.
                    fetchTimelineAsync(0);
                }
            });
        */

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) view.findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                // Do work to refresh the list here.
                findPiece();
               // new Task().execute();
                pieceAdapters.clear();
            }
        });
        findPiece();
        // Configure the refreshing colors
    }

    /*
    private class Task extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            return new String[0];
        }

        @Override protected void onPostExecute(String[] result) {
            // Call setRefreshing(false) when the list has been refreshed.
            mWaveSwipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(result);
        }
    }
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    // Des dialog
    public void showLoading(){
        progress = ProgressDialog.show(getContext(), "Deconnexion","Patientez...",false,false);
    }
    public void dimissLoading(){
        progress.dismiss();
    }

    public void findPiece() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        pieceAdapters = new PieceAdapters(getContext(),ListPiece);
        lvPiece.setAdapter(pieceAdapters);

        String whereClause = "etat ='1' ";
        final int PAGESIZE = 98;
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize(PAGESIZE);
        dataQuery.setWhereClause( whereClause );
        // backendless Categorie
        Backendless.Persistence.of(Piece.class).find(dataQuery, new AsyncCallback<BackendlessCollection<Piece>>() {
            @Override
            public void handleResponse(BackendlessCollection<Piece> foundPiece) {
                Iterator<Piece> PieceIterator = foundPiece.getCurrentPage().iterator();
                while (PieceIterator.hasNext()) {
                    Piece newPiece = PieceIterator.next();
                    ListPiece.add(newPiece);
                }

                pieceAdapters.notifyDataSetChanged();
                pd.setVisibility(View.GONE);
                int size  = foundPiece.getCurrentPage().size();
                if (size<=0) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Objet perdu")
                            .setMessage("Tous les objets perdus ont été récupérer par leur propriétaire")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                   dimissLoading();
                                }
                            })
                            .setIcon(R.drawable.ic_action_signaler)
                            .show();
                }
                mWaveSwipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                //progress.setVisibility(View.GONE);
                // Toast.makeText(getContext(), " No connexion internet  ", Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), R.string.pas_internet, Toast.LENGTH_SHORT).show();
                if(fault.getCode().equals("3064")){

                }
            }
        });

        /*
        * Voir la liste des canditats pour cette offre
        * */
        // contexte menu
        registerForContextMenu(lvPiece);
        lvPiece.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Piece detailsPiece = ListPiece.get(position);
                Intent i = new Intent(getContext(), DetailsPiece.class);
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

        Piece detailsPiece;
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
            Toast.makeText(getContext(), "Change son etat si ce deja fait", Toast.LENGTH_SHORT).show();
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

}
