package com.apri.kas.Models;

public class M_KasKeluar {
    private Integer id, jumlah;
    private String keterangan;
    private Long tgl;

    public M_KasKeluar(Integer id, Integer jumlah, String keterangan, Long tgl) {
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
