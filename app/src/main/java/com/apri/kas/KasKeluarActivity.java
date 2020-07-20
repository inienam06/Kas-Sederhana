package com.apri.kas;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.apri.kas.Adapters.KasKeluarAdapter;
import com.apri.kas.Adapters.KasMasukAdapter;
import com.apri.kas.DB.DBAccess;
import com.apri.kas.Models.M_KasKeluar;
import com.apri.kas.Models.M_KasMasuk;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KasKeluarActivity extends AppCompatActivity implements KasKeluarAdapter.OnClickListener {

    @BindView(R.id.rvKasKeluar)
    RecyclerView rvKasKeluar;
    private DBAccess db;
    private KasKeluarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kas_keluar);
        ButterKnife.bind(this);
        db = new DBAccess(this);

        initToolbar();
        initView();
    }

    private void initView() {
        rvKasKeluar.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    private void getData() {
        List<M_KasKeluar> data = db.getAllKasKeluar();
        adapter = new KasKeluarAdapter(data);
        adapter.notifyDataSetChanged();
        rvKasKeluar.setAdapter(adapter);

        adapter.setOnClickListener(this);
    }

    private void initToolbar() {
        getSupportActionBar().setTitle("Kas Keluar");
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

    @Override
    public void OnRemoveKasKeluar(int position) {
        M_KasKeluar model = adapter.getModel(position);
        DecimalFormat formatter = new DecimalFormat("#,###,###,###");
        new MaterialAlertDialogBuilder(this)
                .setTitle("Konfirmasi")
                .setMessage("Anda yakin akan menghapus kas keluar sebesar Rp. " + formatter.format(model.getJumlah()) + " ini ?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    db.deletKasKeluar(model.getId());
                    getData();
                    Toast.makeText(this, "Kas keluar berhasil dihapus", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Tidak", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void OnUpdateKasKeluar(int position) {
        M_KasKeluar model = adapter.getModel(position);
        startActivity(new Intent(this, UpdateKasActivity.class)
                .putExtra("model", model)
                .putExtra("tipe", "keluar")
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}