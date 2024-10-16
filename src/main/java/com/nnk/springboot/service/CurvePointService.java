package com.nnk.springboot.service;


import com.nnk.springboot.domain.CurvePoint;
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


    public List<CurvePoint> getAll() {
        return curvePointRepository.findAll();
    }


    public CurvePoint save(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }


    public CurvePoint getById(int id) {
        return curvePointRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Specified curve point not found : {}", id);
                    return new EntityNotFoundException("Specified curve point not found");
                });
    }

    public CurvePoint update(int id, CurvePoint curvePoint) {

        CurvePoint curvePointToUpdate = getById(id);
        curvePointToUpdate.setCurveId(curvePoint.getCurveId());
        curvePointToUpdate.setTerm(curvePoint.getTerm());
        curvePointToUpdate.setValue(curvePoint.getValue());

        if (!curvePoint.equals(curvePointToUpdate)) return curvePointToUpdate;

        return curvePointRepository.save(curvePointToUpdate);
    }

    public void delete(int id) {

        if (!curvePointRepository.existsById(id)) {
            logger.warn("Curve point with id {} not found for deletion", id);
            throw new EntityNotFoundException("Specified curve point not found");
        }

        curvePointRepository.deleteById(id);
    }
}
