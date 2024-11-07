package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurvePointRepository extends JpaRepository<CurvePointEntity, Integer> {

}
