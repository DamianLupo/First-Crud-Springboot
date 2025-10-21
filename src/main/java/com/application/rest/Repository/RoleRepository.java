package com.application.rest.Repository;

import com.application.rest.Entities.Role.UserRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<UserRol,Long> {
}
