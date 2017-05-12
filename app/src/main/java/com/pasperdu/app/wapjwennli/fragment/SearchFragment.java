package com.pasperdu.app.wapjwennli.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private ArrayList<Piece> ListPiece;
    private PieceAdapters pieceAdapters;
    ListView lvPiece;
    ProgressBar pd ;
    ProgressDialog progress;
    private Unbinder unbinder;
    private SwipeRefreshLayout swipeContainer;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    public String searchKey;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_perdu, container, false);

        unbinder = ButterKnife.bind(this, v);
        lvPiece = (ListView) v.findViewById(R.id.lvPiece);
        pd = (ProgressBar) v.findViewById(R.id.progressBar);
        ListPiece = new ArrayList<>();


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            searchKey = bundle.getString("searchKey");
            Toast.makeText(getContext(), "" + searchKey, Toast.LENGTH_SHORT).show();
        }

        // Inflate the layout for this fragment
        return v;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Setup refresh listener which triggers new data loading
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) view.findViewById(R.id.main_swipe);

        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                // Do work to refresh the list here.
                findPiece(searchKey);
                // new Task().execute();
                pieceAdapters.clear();
            }
        });
        findPiece(searchKey);
        // Configure the refreshing colors
    }

    // Des dialog
    public void showLoading(){
        progress = ProgressDialog.show(getContext(), "Deconnexion","Patientez...",false,false);
    }
    public void dimissLoading(){
        progress.dismiss();
    }

    public void findPiece(String s) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        pieceAdapters = new PieceAdapters(getContext(),ListPiece);
        lvPiece.setAdapter(pieceAdapters);

        StringBuilder whereClause = new StringBuilder();
        whereClause.append("etat='1'");
        whereClause.append( " and " );
        whereClause.append( "namePiece>='"+s+"'");
        final int PAGESIZE = 90;
        // BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause( whereClause.toString());

        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize(PAGESIZE);
        dataQuery.setWhereClause(String.valueOf(whereClause));
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

        // contexte menu
        registerForContextMenu(lvPiece);
        lvPiece.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Piece detailsPiece = ListPiece.get(position);
                //  Intent i = new Intent(getContext(), DetailsPiece.class);
                //i.putExtra("detailsJob", detailsPiece);
                // startActivity(i);
            }
        });

    }


}
