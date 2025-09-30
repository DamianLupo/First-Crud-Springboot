package com.application.rest.Service.Impl;

import com.application.rest.Entities.Maker;
import com.application.rest.Persistence.iMakerDAO;
import com.application.rest.Service.iMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MakerServiceImpl implements iMakerService {

    @Autowired
    private iMakerDAO makerDAO;

    @Override
    public Optional<Maker> findById(Long id) {
        return makerDAO.findById(id);
    }

    @Override
    public List<Maker> findAll() {
        return makerDAO.findAll();
    }

    @Override
    public void save(Maker maker) {
        makerDAO.save(maker);
    }

    @Override
    public void deleteById(Long id) {
        makerDAO.deleteById(id);
    }
}
