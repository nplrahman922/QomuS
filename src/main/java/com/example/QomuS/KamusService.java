package com.example.QomuS;

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

        // kamus biasa aja T_T
        tree.insert("sekolah", "مَدْرَسَة (Madrasah)");
        tree.insert("rumah", "بَيْت (Bayt)");
        tree.insert("guru", "مُدَرِّس (Mudarris)");
        // sedikit Gimmick
        tree.insert("pi", 3.1415926535);
        tree.insert("e", 2.71828);
        tree.insert("kalkulator", artiArab + "#" + kodeRahasia);
        tree.insert("rahasia", "🎉 Selamat! Kamu menemukan Secret Key!");
        tree.insert("halo", "Halo juga! Semangat ngodingnya ya!");
        tree.insert("cinta", "حُبٌّ#GIMMICK_LOVE");
        tree.insert("hantu", "شَيْطَانٌ#GIMMICK_HORROR");
        tree.insert("seram", "مُخِيف#GIMMICK_HORROR");
        tree.insert("jumpscare", "مُفَاجِئ#GIMMICK_HORROR");
    }

    // kalo kata belum ada di database RBtree kamus <:
    public Object cari(String kata) {
        Object hasil = tree.search(kata);

        if (hasil == null) {
            return "Maaf, kata '" + kata + "' belum ada di kamus.";
        }

        return hasil;
    }
}