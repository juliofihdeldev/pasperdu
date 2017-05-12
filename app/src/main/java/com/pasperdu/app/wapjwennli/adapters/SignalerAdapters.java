package com.pasperdu.app.wapjwennli.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.pasperdu.app.wapjwennli.R;
import com.pasperdu.app.wapjwennli.models.Signaler;
import com.pasperdu.app.wapjwennli.utils.SIMADateFormat;

import java.util.ArrayList;

import static com.pasperdu.app.wapjwennli.R.id.btnDesign;

/**
 * Created by Utilisateur on 2017-04-28.
 */

public class SignalerAdapters extends ArrayAdapter<Signaler> {

    public SignalerAdapters(Context context, ArrayList<Signaler> pieces) {
        super(context, android.R.layout.simple_list_item_1, pieces);
    }
    public static class ViewHolder{
        TextView tvName;
        TextView tvAdresse;
        TextView tvDateAjout;
        TextView tvType;
        TextView tvTelephone;
        Button btnDesign;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Signaler piece = getItem(position);

        if(convertView == null){
            viewHolder= new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_list_perdu, parent, false);
            /*
                find my image view
                viewHolder.movieImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
                //clear out image vier
                viewHolder.movieImage.setImageResource(0);
            */
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvAdresse = (TextView) convertView.findViewById(R.id.tvAdresse);
            viewHolder.tvDateAjout = (TextView) convertView.findViewById(R.id.tvDateAjout);
            viewHolder.tvType = (TextView) convertView.findViewById(R.id.tvType);
            viewHolder.tvTelephone = (TextView) convertView.findViewById(R.id.tvTelephone);
            viewHolder.btnDesign = (Button) convertView.findViewById(btnDesign);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder)convertView.getTag();
        }
        // Picasso.with(context).load("image url").into(ImageView);
        viewHolder.tvName.setText(piece.getNomProprietaire());
        viewHolder.tvAdresse.setText(piece.getLieuRetrouve());
        viewHolder.tvType.setText(piece.getNomObjet());
        viewHolder.tvTelephone.setText(piece.getNumeroAcontacter());
        // set datetime, use created
        String strDate;
        strDate = SIMADateFormat.formatCollecteDate(piece.getDateAjout());
        viewHolder.tvDateAjout.setText(strDate);
        String name = piece.getNomObjet();
        String firstLetter = String.valueOf(name.charAt(0));
        viewHolder.btnDesign.setText(firstLetter.toString());
        viewHolder.btnDesign.setBackgroundColor(Color.parseColor("#"+piece.getColor().toString()));
        return convertView;
    }
}
