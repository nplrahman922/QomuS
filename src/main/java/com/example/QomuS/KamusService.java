package com.example.QomuS;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class KamusService {

    private QomusRBTree tree;

    public KamusService() {
        this.tree = new QomusRBTree();
    }

    @PostConstruct
    public void isiDataAwal() {

        String artiArab = "آلَةٌ حَاسِبَةٌ (Alat Hitung)";
        String kodeRahasia = "GIMMICK_CALC";

        // Kamus Biasa 
        tree.insert("sekolah", "مَدْرَسَة (Madrasah)");
        tree.insert("rumah", "بَيْت (Bayt)");
        tree.insert("guru", "مُدَرِّس (Mudarris)");
        tree.insert("belajar", "تَعَلَّمَ (Ta'allama)");

        // Gimmick 
        tree.insert("pi", 3.1415926535);
        tree.insert("e", 2.71828);
        tree.insert("kalkulator", artiArab + "#" + kodeRahasia);
        tree.insert("rahasia", "🎉 Selamat! Kamu menemukan Secret Key!");
        tree.insert("halo", "Halo juga! Semangat ngodingnya ya!");
    }

    // Cari kata dalam kamus
    public Object cari(String kata) {
        Object hasil = tree.search(kata);

        if (hasil == null) {
            return "Maaf, kata '" + kata + "' belum ada di kamus.";
        }

        // Gimmick "belajar"
        if (kata.equalsIgnoreCase("belajar")) {

            Map<String, Object> response = new HashMap<>();

            response.put("kata", "belajar");
            response.put("arti", hasil.toString());

            List<String> tips = Arrays.asList(
                "Perbanyak kosakata sedikit demi sedikit setiap hari.",
                "Dengarkan percakapan Arab asli untuk membiasakan telinga.",
                "Coba membaca teks Arab mulai dari yang sederhana.",
                "Konsisten lebih penting daripada durasi belajar!"
            );

            response.put("tips", tips);
            response.put("motivasi", "🔥 Semangat! أنت تستطيع‎ — Kamu pasti bisa!");

            return response;
        }

        // Hasil Normal
        String artiText = hasil.toString();

        
        String artiBersih = artiText.contains("#")
                ? artiText.substring(0, artiText.indexOf("#"))
                : artiText;

       
        String gimmickCode = artiText.contains("#")
                ? artiText.substring(artiText.indexOf("#") + 1)
                : null;

        Map<String, Object> normal = new HashMap<>();
        normal.put("kata", kata);
        normal.put("arti", artiBersih);

        if (gimmickCode != null) {
            normal.put("gimmick", gimmickCode);
        }

        return normal;
    }
}
