package com.example.CurrencyConverter.repositories;

import com.example.CurrencyConverter.entities.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity,Long> {

}
