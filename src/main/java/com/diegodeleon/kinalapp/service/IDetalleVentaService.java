package com.diegodeleon.kinalapp.service;
import com.diegodeleon.kinalapp.entity.DetalleVenta;

import java.util.List;
import java.util.Optional;

public interface IDetalleVentaService {
    //Metodo que lista todos los detalles de venta
    List<DetalleVenta> listarDetallesVentas();

    //Metodo que permite guardar detalles de venta
    DetalleVenta guardarDetalleVenta(DetalleVenta detalleVenta);
    Optional<DetalleVenta> buscarPorCodigoVenta(Long codigoDetalleVenta);

    //Metodo que actualiza un detalle de venta
    DetalleVenta actualizarDetalleVenta(Long codigoDetalleVenta, DetalleVenta detalleVenta);

    //Metodo que elimina un detalle de venta
    void eliminarDetalleVenta(Long codigoDetalleVenta);

    //Metodo que busca un detalle de venta por su id
    boolean existePorCodigoDetalleVenta(Long codigoDetalleVenta);
}
