package com.apri.kas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.apri.kas.DB.DBAccess;
import com.apri.kas.DB.DBManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tvMainKasMasukValue)
    TextView tvMainKasMasukValue;
    @BindView(R.id.btnMainKasMasuk)
    MaterialButton btnMainKasMasuk;
    @BindView(R.id.tvMainKasKeluarValue)
    TextView tvMainKasKeluarValue;
    @BindView(R.id.btnMainKasKeluar)
    MaterialButton btnMainKasKeluar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private DBAccess db;
    private Integer kas_masuk = 0;
    private Integer kas_keluar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        db = new DBAccess(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_tentang) {
            new MaterialAlertDialogBuilder(this)
                    .setMessage("Aplikasi Kas sederhana by Siti Nur Aprianti")
                    .setPositiveButton("OKE", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btnMainKasMasuk, R.id.btnMainKasKeluar, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnMainKasMasuk:
                startActivity(new Intent(this, KasMasukActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.btnMainKasKeluar:
                startActivity(new Intent(this, KasKeluarActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            case R.id.fab:
                startActivity(new Intent(this, TambahKasActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        kas_masuk = db.getSumKasMasuk();
        kas_keluar = db.getSumKasKeluar();
        setKas();
    }

    private void setKas() {
        tvMainKasMasukValue.setText(formatMoney(kas_masuk));
        tvMainKasKeluarValue.setText(formatMoney(kas_keluar));
    }

    private String formatMoney(Integer value) {
        String result;
        DecimalFormat formatter = new DecimalFormat("#,###.###");

        if (value.toString().split("").length >= 7) {
            result = formatter.format((float) value / 1000000) + "jt";
        } else if(value.toString().split("").length >= 10) {
            result = formatter.format((float) value / 1000000000 )+ "m";
        } else {
            result = formatter.format(value);
        }

        return result;
    }
}