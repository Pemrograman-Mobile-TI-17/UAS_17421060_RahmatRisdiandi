package com.example.RahmatRisdiandi.pembeli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.RahmatRisdiandi.R;
import com.example.RahmatRisdiandi.server.BaseURL;
import com.squareup.picasso.Picasso;

public class DetailPembeli extends AppCompatActivity {

    EditText edtKodeSkincare, edtNamaSkincare, edtHargaSkincare;
    ImageView imgGambarBuku;

    String strKodeSkincare, strNamaSkincare, strHargaSkincare, strGambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembelian);

        edtKodeSkincare = (EditText) findViewById(R.id.edtKodeSkincare);
        edtNamaSkincare = (EditText) findViewById(R.id.edtNamaSkincare);
        edtHargaSkincare = (EditText) findViewById(R.id.edtHargaSkincare);

        imgGambarBuku = (ImageView) findViewById(R.id.gambar);

        Intent i = getIntent();
        strKodeSkincare = i.getStringExtra("kodeSkincare");
        strNamaSkincare = i.getStringExtra("namaSkincare");
        strHargaSkincare = i.getStringExtra("hargaSkincare");

        edtKodeSkincare.setText(strKodeSkincare);
        edtNamaSkincare.setText(strNamaSkincare);
        edtHargaSkincare.setText(strHargaSkincare);
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + strGambar)
                .into(imgGambarBuku);
    }
}
