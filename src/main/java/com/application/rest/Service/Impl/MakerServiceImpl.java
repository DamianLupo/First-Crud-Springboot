package com.application.rest.Service.Impl;

import com.application.rest.Entities.Maker;
import com.application.rest.Persistence.iMakerDAO;
import com.application.rest.Service.iMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


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
    public Page<Maker> findAll(Pageable pageable) {
        return makerDAO.findAll(pageable);
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
