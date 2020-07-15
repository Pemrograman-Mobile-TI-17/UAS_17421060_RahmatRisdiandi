package com.example.RahmatRisdiandi.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.RahmatRisdiandi.R;
import com.example.RahmatRisdiandi.model.ModelSkincare;
import com.example.RahmatRisdiandi.server.BaseURL;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterSkincare extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelSkincare> item;

    public AdapterSkincare(Activity activity, List<ModelSkincare> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_skincare, null);


        TextView nama = (TextView) convertView.findViewById(R.id.txtnamaSkincare);
        TextView harga         = (TextView) convertView.findViewById(R.id.txthargaSkincare);
        ImageView gambarSkincare         = (ImageView) convertView.findViewById(R.id.gambar);

        nama.setText(item.get(position).getNamaSkincare());
        harga.setText("Rp." + item.get(position).getHargaSkincare());
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + item.get(position).getGambar())
                .resize(40, 40)
                .centerCrop()
                .into(gambarSkincare);
        return convertView;
    }

}

