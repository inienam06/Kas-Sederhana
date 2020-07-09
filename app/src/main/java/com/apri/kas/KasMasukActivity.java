package com.apri.kas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.apri.kas.Adapters.KasMasukAdapter;
import com.apri.kas.DB.DBAccess;
import com.apri.kas.Models.M_KasMasuk;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KasMasukActivity extends AppCompatActivity implements KasMasukAdapter.OnClickListener {

    @BindView(R.id.rvKasMasuk)
    RecyclerView rvKasMasuk;
    private DBAccess db;
    private KasMasukAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kas_masuk);
        ButterKnife.bind(this);
        db = new DBAccess(this);

        initToolbar();
        initView();
    }

    private void initView() {
        rvKasMasuk.setHasFixedSize(true);
        getData();
    }

    private void getData() {
        List<M_KasMasuk> data = db.getAllKasMasuk();
        adapter = new KasMasukAdapter(data);
        adapter.notifyDataSetChanged();
        rvKasMasuk.setAdapter(adapter);

        adapter.setOnClickListener(this);
    }

    private void initToolbar() {
        getSupportActionBar().setTitle("Kas Masuk");
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
    public void OnRemoveKasMasuk(int position) {
        M_KasMasuk model = adapter.getModel(position);
        DecimalFormat formatter = new DecimalFormat("#,###,###,###");
        new MaterialAlertDialogBuilder(this)
                .setTitle("Konfirmasi")
                .setMessage("Anda yakin akan menghapus kas masuk sebesar Rp. " + formatter.format(model.getJumlah()) + " ini ?")
                .setPositiveButton("Ya", (dialog, which) -> {
                    db.deletKasMasuk(model.getId());
                    getData();
                    Toast.makeText(this, "Kas masuk berhasil dihapus", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Tidak", (dialog, which) -> dialog.dismiss())
                .show();
    }
}