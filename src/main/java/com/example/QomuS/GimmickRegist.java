package com.example.QomuS;

import org.springframework.stereotype.Component;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.function.Supplier;

@Component
public class GimmickRegist {

    public void daftarkanGimmickKe(QomusRBTree tree) {

       // 1. fitur jam
        Supplier<Object> fungsiJam = () -> {
            String waktu = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            return "CLOCK:" + waktu;
        };
        tree.insert("jam", "السَّاعَةُ (As-Sa'ah)", fungsiJam);

        // 2. fitur DADU
        Supplier<Object> fungsiDadu = () -> {
            int angka = new Random().nextInt(6) + 1;
            return "DICE:" + angka;
        };
        tree.insert("dadu", "رَمْيُ النَّرْدِ (Lempar Dadu)", fungsiDadu);

        // 3. fitur kalkulator
        Supplier<Object> fungsiKalkulator = () -> "CALC:TRUE";
        tree.insert("kalkulator", "آلَةٌ حَاسِبَةٌ (Alat Hitung)", fungsiKalkulator);

        // 4. fitue diskon
        Supplier<Object> fungsiDiskon = () -> {
            int jam = LocalTime.now().getHour();
            if (jam < 12) {
                return "ALERT:Selamat Pagi! Diskon Pagi 50%!";
            } else {
                return "ALERT:Selamat Siang. Maaf diskon habis.";
            }
        };
        tree.insert("diskon", "Cek Promo", fungsiDiskon);
    }
}