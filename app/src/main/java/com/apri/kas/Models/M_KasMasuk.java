package com.apri.kas.Models;

import java.io.Serializable;

public class M_KasMasuk implements Serializable {
    private Integer id, jumlah;
    private String keterangan;
    private Long tgl;

    public M_KasMasuk(Integer id, Integer jumlah, String keterangan, Long tgl) {
        this.id = id;
        this.jumlah = jumlah;
        this.keterangan = keterangan;
        this.tgl = tgl;
    }

    public Integer getId() {
        return id;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public Long getTgl() {
        return tgl;
    }
}
