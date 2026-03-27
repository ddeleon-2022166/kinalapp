package com.diegodeleon.kinalapp.repository;

import com.diegodeleon.kinalapp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    List<Usuario> findByEstado(int estado);
}
