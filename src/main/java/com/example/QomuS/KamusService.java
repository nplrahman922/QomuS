package com.example.QomuS;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class KamusService {

    private QomusRBTree tree;
    private final GimmickRegist gimmickRegistry; // Registry untuk fitur canggih

    // Hapus ObjectMapper dari constructor, sisakan GimmickRegist
    public KamusService(GimmickRegist gimmickRegistry) {
        this.tree = new QomusRBTree();
        this.gimmickRegistry = gimmickRegistry;
    }

    @PostConstruct
    public void isiDataAwal() {
        // --- 1. DATA KATA STANDAR (Manual di sini) ---
        tree.insert("sekolah", "مَدْرَسَة (Madrasah)", null);
        tree.insert("rumah", "بَيْت (Bayt)", null);
        tree.insert("guru", "مُدَرِّس (Mudarris)", null);
        tree.insert("buku", "كِتَابٌ (Kitabun)", null);
        tree.insert("pena", "قَلَمٌ (Qalamun)", null);

        // --- 2. DATA GIMMICK SEDERHANA (String Only) ---
        // Gimmick yang cuma butuh kode teks, kita taruh sini saja
        tree.insert("merah", "أَحْمَرُ (Ahmar)", "BG:bg-red-200");
        tree.insert("hijau", "أَخْضَرُ (Akhdhar)", "BG:bg-green-200");
        tree.insert("biru", "أَزْرَقُ (Azraq)", "BG:bg-blue-200");

        tree.insert("google", "Pindah ke Google", "JS:window.location.href='https://google.com'");
        tree.insert("putar", "Dunia Terbalik", "JS:document.body.style.transition='transform 1s'; document.body.style.transform='rotate(180deg)';");
        tree.insert("normal", "Kembali Normal", "JS:document.body.style.transform='rotate(0deg)';");

        // --- 3. LOAD FITUR CERDAS DARI FILE LAIN ---
        // (Jam, Dadu, Kalkulator, Diskon) dipanggil dari GimmickRegist.java
        gimmickRegistry.daftarkanGimmickKe(this.tree);
    }

    public Map<String, Object> cari(String kata) {
        QomusRBnode node = tree.searchNode(kata);
        Map<String, Object> response = new HashMap<>();

        if (node == null) {
            response.put("arti", "Maaf, kata tidak ditemukan.");
            response.put("gimmick", null);
        } else {
            response.put("arti", node.value);

            // Cek apakah Gimmick adalah Function? (Dari Registry)
            if (node.gimmick instanceof Supplier) {
                response.put("gimmick", ((Supplier<?>) node.gimmick).get());
            } else {
                // String biasa (Dari Manual di atas)
                response.put("gimmick", node.gimmick);
            }
        }
        return response;
    }
}