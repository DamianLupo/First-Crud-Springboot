package com.application.rest.Persistence.impl;

import com.application.rest.Entities.Maker;
import com.application.rest.Persistence.iMakerDAO;
import com.application.rest.Repository.MakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Component
public class MakerDAOImpl implements iMakerDAO {
    @Autowired
    private MakerRepository makerRepository;

    @Override
    public Optional<Maker> findById(Long id) {
        return makerRepository.findById(id);
    }

    @Override
    public Page<Maker> findAll(Pageable pageable) {
        return makerRepository.findAll(pageable);
    }

    @Override
    public void save(Maker maker) {
        makerRepository.save(maker);
    }

    @Override
    public void deleteById(Long id) {
        makerRepository.deleteById(id);
    }
}
