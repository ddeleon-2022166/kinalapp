package com.diegodeleon.kinalapp.service;

import com.diegodeleon.kinalapp.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    //Metodo que lista todos los productos
    List<Producto> listarProductos();

    //Metodo que permite guardar productos
    Producto guardarProducto(Producto producto);
    Optional<Producto> buscarPorCodigoProducto(int codigoProducto);

    //Metodo que actualiza un producto
    Producto actualizarProducto(int codigoProducto, Producto producto);

    //Metodo que elimina un producto
    void eliminarProducto(int codigoProducto);

    //Metodo que busca un producto por su codigo
    boolean existePorCodigoProducto(int codigoProducto);

    //Metodo que devuelve los productos activos
    List<Producto> listarProductosActivos();
}
