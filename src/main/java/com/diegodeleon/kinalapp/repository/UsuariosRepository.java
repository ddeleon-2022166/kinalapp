package com.diegodeleon.kinalapp.repository;

import com.diegodeleon.kinalapp.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuariosRepository extends JpaRepository<Usuarios,Integer> {
    List<Usuarios> findByEstado(int estado);
}
