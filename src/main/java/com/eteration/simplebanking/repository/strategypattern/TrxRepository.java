package com.eteration.simplebanking.repository.strategypattern;

import com.eteration.simplebanking.model.strategypattern.Trx;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrxRepository extends JpaRepository<Trx, Long> {
}
