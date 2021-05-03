package com.inverenlace.sbngblog.security.repository;

import com.inverenlace.sbngblog.security.entity.Rol;
import com.inverenlace.sbngblog.security.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
