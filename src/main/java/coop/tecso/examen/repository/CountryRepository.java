package coop.tecso.examen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import coop.tecso.examen.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

}
