package com.nnk.springboot.service;


import com.nnk.springboot.domain.CurvePointEntity;
import com.nnk.springboot.repositories.CurvePointRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurvePointService {

    public final Logger logger = LoggerFactory.getLogger(CurvePointService.class);

    private final CurvePointRepository curvePointRepository;

    @Autowired
    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }


    public List<CurvePointEntity> getAll() {
        return curvePointRepository.findAll();
    }


    public CurvePointEntity save(CurvePointEntity curvePointEntity) {
        return curvePointRepository.save(curvePointEntity);
    }


    public CurvePointEntity getById(int id) {
        return curvePointRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Specified curve point not found : {}", id);
                    return new EntityNotFoundException("Specified curve point not found");
                });
    }

    public CurvePointEntity update(int id, CurvePointEntity curvePointEntity) {

        CurvePointEntity curvePointEntityToUpdate = getById(id);
        curvePointEntityToUpdate.setCurveId(curvePointEntity.getCurveId());
        curvePointEntityToUpdate.setTerm(curvePointEntity.getTerm());
        curvePointEntityToUpdate.setValue(curvePointEntity.getValue());

        if (!curvePointEntity.equals(curvePointEntityToUpdate)) return curvePointEntityToUpdate;

        return curvePointRepository.save(curvePointEntityToUpdate);
    }

    public void delete(int id) {

        if (!curvePointRepository.existsById(id)) {
            logger.warn("Curve point with id {} not found for deletion", id);
            throw new EntityNotFoundException("Specified curve point not found");
        }

        curvePointRepository.deleteById(id);
    }
}
