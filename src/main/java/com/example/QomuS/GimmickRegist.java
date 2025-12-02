package com.example.QomuS;

import org.springframework.stereotype.Component;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.function.Supplier;

@Component
public class GimmickRegist {

    public void daftarkanGimmickKe(QomusRBTree tree) {

        // 1. Gimmick JAM (Kirim format CLOCK:HH:mm:ss)
        Supplier<Object> fungsiJam = () -> {
            String waktu = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            return "CLOCK:" + waktu; // Contoh: "CLOCK:14:05:30"
        };
        tree.insert("jam", "Waktu Server", fungsiJam);

        // 2. Gimmick DADU (Kirim format DICE:Angka)
        Supplier<Object> fungsiDadu = () -> {
            int angka = new Random().nextInt(6) + 1;
            return "DICE:" + angka; // Contoh: "DICE:5"
        };
        tree.insert("dadu", "Lempar Dadu", fungsiDadu);

        // ... kode kalkulator & diskon biarkan tetap sama ...
        Supplier<Object> fungsiKalkulator = () -> "CALC:TRUE";
        tree.insert("kalkulator", "آلَةٌ حَاسِبَةٌ (Alat Hitung)", fungsiKalkulator);

        Supplier<Object> fungsiDiskon = () -> {
            int jam = LocalTime.now().getHour();
            return (jam < 12) ? "Selamat Pagi#ALERT:Diskon 50%!" : "Selamat Siang#ALERT:Maaf habis.";
        };
        tree.insert("diskon", "Cek Promo", fungsiDiskon);
    }
}