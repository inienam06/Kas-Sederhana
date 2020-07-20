package com.apri.kas;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apri.kas.DB.DBAccess;
import com.apri.kas.Models.M_KasKeluar;
import com.apri.kas.Models.M_KasMasuk;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateKasActivity extends AppCompatActivity {

    @BindView(R.id.tieTambahKasJumlah)
    TextInputEditText tieTambahKasJumlah;
    @BindView(R.id.tieTambahKasKeterangan)
    TextInputEditText tieTambahKasKeterangan;
    @BindView(R.id.btnUpdateKasSimpan)
    Button btnUpdateKasSimpan;
    private M_KasMasuk modelMasuk;
    private M_KasKeluar modelKeluar;
    private String tipe;
    private DBAccess db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_kas);
        ButterKnife.bind(this);
        db = new DBAccess(this);

        initToolbar();
        initView();
    }

    private void initView() {
        getDataIntent();

        if(tipe.equals("masuk")) {
            tieTambahKasJumlah.setText(modelMasuk.getJumlah().toString());
            tieTambahKasKeterangan.setText(modelMasuk.getKeterangan());
        } else if(tipe.equals("keluar")) {
            tieTambahKasJumlah.setText(modelKeluar.getJumlah().toString());
            tieTambahKasKeterangan.setText(modelKeluar.getKeterangan());
        }
    }

    private void getDataIntent() {
        tipe = getIntent().getStringExtra("tipe");

        if(tipe.equals("masuk")) {
            modelMasuk = (M_KasMasuk) getIntent().getSerializableExtra("model");
        } else if (tipe.equals("keluar")) {
            modelKeluar = (M_KasKeluar) getIntent().getSerializableExtra("model");
        }
    }

    private void initToolbar() {
        getSupportActionBar().setTitle("Ubah Data Kas");
        getSupportActionBar().setHomeAsUpIndicator(getDrawable(R.drawable.ic_baseline_arrow_back_24_white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnUpdateKasSimpan)
    public void onViewClicked() {
        if(tieTambahKasJumlah.getText().toString().equals("")) {
            Toast.makeText(this, "Jumlah tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        if(tipe.equals("masuk")) {
            db.updateKasMasuk(Integer.parseInt(tieTambahKasJumlah.getText().toString()), tieTambahKasKeterangan.getText().toString(),
                    modelMasuk.getId());
        } else if(tipe.equals("keluar")) {
            db.updateKasKeluar(Integer.parseInt(tieTambahKasJumlah.getText().toString()), tieTambahKasKeterangan.getText().toString(),
                    modelKeluar.getId());
        }

        Toast.makeText(this, "Data kas berhasil diperbarui", Toast.LENGTH_SHORT).show();
        finish();
    }
}