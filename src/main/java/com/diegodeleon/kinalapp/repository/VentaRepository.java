package com.diegodeleon.kinalapp.repository;

import com.diegodeleon.kinalapp.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta,Integer> {
    List<Venta> findByEstado(int estado);
}
