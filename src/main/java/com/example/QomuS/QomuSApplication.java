package com.example.QomuS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@RestController
@CrossOrigin("*")
public class QomuSApplication {

	@Autowired
	private KamusService kamusService;

	public static void main(String[] args) {
		SpringApplication.run(QomuSApplication.class, args);
	}

	// Ubah 'String' menjadi 'Object'
	@GetMapping("/kamus")
	public Object terjemahkan(@RequestParam(value = "kata") String kata) {
		return kamusService.cari(kata);
	}
}