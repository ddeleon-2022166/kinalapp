package com.diegodeleon.kinalapp.repository;

import com.diegodeleon.kinalapp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //busca usuarios por su username durange el login, sin el, security no puede autenticar a los usuarios
    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);

    // Para listar solo usuarios activos
    List<Usuario> findByEstado(int estado);

}