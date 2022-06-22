package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class BirdController {

	@Autowired
	BirdRepository birds;

	@GetMapping(value = "/liveness")
	public String liveness() {
		return "BirdServiceLiveness";
	}

	@GetMapping(value = "/readiness")
	public String readiness() {
		return "BirdServiceReadiness";
	}

	@GetMapping(value = { "", "/", "/birds/v1/data" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public BirdSummary birds() {
		BirdSummary summary = new BirdSummary();
		try {
			if (birds.count() == 0) {
				return this.load();
			} else {
				for (Bird bird : birds.findAll()) {
					summary.addBird(bird);
				}
			}
		} catch (Exception e) {
			return this.load();
		}

		return summary.filter();
	}

	@GetMapping(value = "/birds/v1/data/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Bird bird(@PathVariable Long index) {
		return birds.findById(index).get();
	}

	@GetMapping(value = "/birds/v1/load", produces = MediaType.APPLICATION_JSON_VALUE)
	public BirdSummary load() {

		birds.save(new Bird("Tweeeety", "Yellow Canary", 2,
				"https://upload.wikimedia.org/wikipedia/en/0/02/Tweety.svg"));
		birds.save(new Bird("Hector", "African Grey Parrot", 5,
				"https://petkeen.com/wp-content/uploads/2020/11/African-Grey-Parrot.webp"));
		birds.save(new Bird("Micheline", "Budgerigar", 3,
				"https://petkeen.com/wp-content/uploads/2020/11/Budgerigar.webp"));

		birds.save(new Bird("Piplette", "Cockatoo", 1,
				"https://petkeen.com/wp-content/uploads/2020/11/Cockatoo.webp"));
		return this.birds();
	}

}