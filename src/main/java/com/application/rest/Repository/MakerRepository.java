package com.application.rest.Repository;

import com.application.rest.Entities.Maker;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MakerRepository extends JpaRepository<Maker, Long> {
}
