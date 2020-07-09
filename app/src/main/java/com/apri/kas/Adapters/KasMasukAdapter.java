package com.apri.kas.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apri.kas.Models.M_KasMasuk;
import com.apri.kas.R;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KasMasukAdapter extends RecyclerView.Adapter<KasMasukAdapter.Holder> {
    private List<M_KasMasuk> data;
    private OnClickListener listener;

    public KasMasukAdapter(List<M_KasMasuk> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_kas, parent, false);

        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        M_KasMasuk model = getModel(position);
        DecimalFormat formatter = new DecimalFormat("#,###,###,###");

        holder.tvContentKasValue.setText("Rp. " + formatter.format(model.getJumlah()));
        holder.tvContentKasKeterangan.setText(model.getKeterangan());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public M_KasMasuk getModel(int position) {
        return data.get(position);
    }

    public interface OnClickListener {
        void OnRemoveKasMasuk(int position);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tvContentKasValue)
        TextView tvContentKasValue;
        @BindView(R.id.ivContentKasHapus)
        ImageView ivContentKasHapus;
        @BindView(R.id.tvContentKasKeterangan)
        TextView tvContentKasKeterangan;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            ivContentKasHapus.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.OnRemoveKasMasuk(getAdapterPosition());
        }
    }
}
