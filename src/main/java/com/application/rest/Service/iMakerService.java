package com.application.rest.Service;

import com.application.rest.Entities.Maker;


import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface iMakerService {
    Optional<Maker> findById(Long id);
    Page<Maker> findAll(Pageable pageable);
    void save (Maker maker);
    void deleteById (Long id);
}
