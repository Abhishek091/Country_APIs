package com.eleos.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eleos.app.db.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    
}
