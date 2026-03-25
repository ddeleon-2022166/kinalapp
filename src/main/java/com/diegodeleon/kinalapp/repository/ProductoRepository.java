package com.diegodeleon.kinalapp.repository;

import com.diegodeleon.kinalapp.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Integer> {
    List<Producto> findByEstado(int estado);
}
