package com.example.QomuS;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class KamusService {

    private QomusRBTree tree;
    private final GimmickRegist gimmickRegistry;

    public KamusService(GimmickRegist gimmickRegistry) {
        this.tree = new QomusRBTree();
        this.gimmickRegistry = gimmickRegistry;
    }

    @PostConstruct
    public void isiDataAwal() {
        // --- 1. data kata standar aja
        tree.insert("sekolah", "مَدْرَسَة (Madrasah)", null);
        tree.insert("rumah", "بَيْت (Bayt)", null);
        tree.insert("guru", "مُدَرِّس (Mudarris)", null);
        tree.insert("buku", "كِتَابٌ (Kitabun)", null);
        tree.insert("pena", "قَلَمٌ (Qalamun)", null);

        // --- 2. gimmick berbasis string kode javascript / tailwindcss
        tree.insert("merah", "أَحْمَرُ (Ahmar)", "BG:bg-red-200");
        tree.insert("hijau", "أَخْضَرُ (Akhdhar)", "BG:bg-green-200");
        tree.insert("biru", "أَزْرَقُ (Azraq)", "BG:bg-blue-200");

        tree.insert("google", "الاِنْتِقَالُ إِلَى جُوجِل (Pindah ke Google)", "JS:window.location.href='https://google.com'");
        tree.insert("putar", "عَالَمٌ مَقْلُوبٌ (Dunia Terbalik)", "JS:document.body.style.transition='transform 1s'; document.body.style.transform='rotate(180deg)';");
        tree.insert("normal", "العَوْدَةُ لِلْوَضْعِ العَادِي (Kembali Normal)", "JS:document.body.style.transform='rotate(0deg)';");

        // --- 3. gimmick berbasis fungsi
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

            // Cek apakah Gimmick adalah Function?
            if (node.gimmick instanceof Supplier) {
                response.put("gimmick", ((Supplier<?>) node.gimmick).get());
            } else {
                response.put("gimmick", node.gimmick);
            }
        }
        return response;
    }
}