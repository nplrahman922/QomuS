package com.example.QomuS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser; // Import SpEL
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.Map;

@SpringBootApplication
@RestController
@CrossOrigin("*")
public class QomuSApplication {

	@Autowired
	private KamusService kamusService;

	public static void main(String[] args) {
		SpringApplication.run(QomuSApplication.class, args);
	}

	@GetMapping("/kamus")
	public Map<String, Object> terjemahkan(@RequestParam(value = "kata") String kata) {
		return kamusService.cari(kata);
	}

	@GetMapping("/hitung")
	public String hitung(@RequestParam(value = "ekspresi") String ekspresi) {
		try {
			if (!ekspresi.matches("[0-9+\\-*/(). ]+")) {
				return "Error: Karakter tidak valid";
			}

			ExpressionParser parser = new SpelExpressionParser();
			Object hasil = parser.parseExpression(ekspresi).getValue();
			return hasil.toString();

		} catch (Exception e) {
			return "Error";
		}
	}
}