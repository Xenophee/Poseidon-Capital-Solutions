package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidListRepository extends JpaRepository<BidListEntity, Integer> {

}
