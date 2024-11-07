package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.TradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<TradeEntity, Integer> {
}
