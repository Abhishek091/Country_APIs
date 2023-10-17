package com.eleos.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eleos.app.db.Country;
import com.eleos.app.exception.CountryServiceException;
import com.eleos.app.exception.InvalidCountryDataException;
import com.eleos.app.service.CountryService;

@RestController
@RequestMapping("/api")
public class CountryController {

	@Autowired
	private CountryService countryService;

	@RequestMapping(value = "/countries", method = RequestMethod.GET)
	public ResponseEntity<List<Country>> getCountries(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        List<Country> countries = countryService.listCountries(page, size);
        return ResponseEntity.ok(countries);
    }
	@RequestMapping(value = "/countries", method = RequestMethod.POST)
	public ResponseEntity<Country> addCountry(@RequestBody Country country) throws CountryServiceException, InvalidCountryDataException {
		Country createdCountry = countryService.createCountry(country);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCountry);
    }

	@RequestMapping(value = "/country/{id}", method = RequestMethod.GET)
	public ResponseEntity<Country> getCountry(@PathVariable Integer id) {
		try {
			Country country = countryService.getCountryById(id);
			if (country != null) {
				return ResponseEntity.ok(country);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@RequestMapping(value = "/country/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCountry(@PathVariable Integer id) {
        boolean deleted = countryService.deleteCountry(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

	@RequestMapping(value = "/country/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Country> updateCountry(@PathVariable Integer id, @RequestBody Country updatedCountry) {
        Country country = countryService.updateCountry(id, updatedCountry);

        if (country != null) {
            return ResponseEntity.ok(country);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
