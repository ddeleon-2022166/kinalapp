package com.diegodeleon.kinalapp.service;
import com.diegodeleon.kinalapp.entity.Venta;

import java.util.List;
import java.util.Optional;

public interface IVentaService {
    //Metodo que lista todas las ventas a
    List<Venta> listarVentas();

    //Metodo que permite guardar ventas
    Venta guardarVenta(Venta venta);
    Optional<Venta> buscarPorCodigoVenta(Long codigoVenta);

    //Metodo que actualiza una venta
    Venta actualizarVenta(Long codigoVenta, Venta venta);

    //Metodo que elimina una venta a
    void eliminarVenta(Long codigoVenta);

    //Metodo que busca una venta por su codigo
    boolean existePorCodigoVenta(Long codigoVenta);

    //Metodo que devuelve las ventas activas
    List<Venta> listarVentasActivos();
}
