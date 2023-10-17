package com.eleos.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.eleos.app.db.Country;
import com.eleos.app.exception.CountryServiceException;
import com.eleos.app.exception.InvalidCountryDataException;
import com.eleos.app.repository.CountryRepository;

@Service
public class CountryService {
	
	@Autowired
	private CountryRepository countryRepository;

	public Country createCountry(Country country) throws CountryServiceException, InvalidCountryDataException {
        try {
            if (country == null) {
                throw new IllegalArgumentException("Country object cannot be null.");
            }
			return countryRepository.save(country);
		}
		catch (IllegalArgumentException ex) {
            // Handle illegal arguments or validation errors
            throw new InvalidCountryDataException(ex.getMessage());
        }catch (Exception ex) {
            // Handle other unexpected exceptions
            throw new CountryServiceException("Failed to create the country.");
        }
        
    }

    public List<Country> listCountries(int page, int size) {
        // Create a PageRequest for pagination
        PageRequest pageRequest = PageRequest.of(page, size);

        // Retrieve a Page of countries
        Page<Country> countryPage = countryRepository.findAll(pageRequest);

        // Extract the content from the Page
        List<Country> countries = countryPage.getContent();

        return countries;
    }

	public Country getCountryById(Integer id) {
		Optional<Country> optionalCountry = countryRepository.findById(id);
    		if (optionalCountry.isPresent()) {
			return optionalCountry.get();
		} else {
			return null;
		}
	}

	public Country updateCountry(Integer id, Country updatedCountry) {
        Optional<Country> optionalCountry = countryRepository.findById(id);

        if (optionalCountry.isPresent()) {
            Country existingCountry = optionalCountry.get();
            existingCountry.setName(updatedCountry.getName());
            return countryRepository.save(existingCountry);
        } else {
            return null;
        }
    }

	public boolean deleteCountry(Integer id) {
        if (countryRepository.existsById(id)) {
            countryRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
