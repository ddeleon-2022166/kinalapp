package com.diegodeleon.kinalapp.service;

import com.diegodeleon.kinalapp.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    //Metodo que lista todos los productos
    List<Producto> listarProductos();

    //Metodo que permite guardar productos
    Producto guardarProducto(Producto producto);
    Optional<Producto> buscarPorCodigoProducto(Long codigoProducto);

    //Metodo que actualiza un producto
    Producto actualizarProducto(Long codigoProducto, Producto producto);

    //Metodo que elimina un producto
    void eliminarProducto(Long codigoProducto);

    //Metodo que busca un producto por su codigo
    boolean existePorCodigoProducto(Long codigoProducto);

    //Metodo que devuelve los productos activos
    List<Producto> listarProductosActivos();
}
