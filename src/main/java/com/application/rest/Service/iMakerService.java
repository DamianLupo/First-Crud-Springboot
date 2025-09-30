package com.application.rest.Service;

import com.application.rest.Entities.Maker;

import java.util.List;
import java.util.Optional;

public interface iMakerService {
    Optional<Maker> findById(Long id);
    List<Maker> findAll();
    void save (Maker maker);
    void deleteById (Long id);
}
