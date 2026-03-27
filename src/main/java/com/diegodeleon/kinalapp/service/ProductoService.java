package com.diegodeleon.kinalapp.service;

import com.diegodeleon.kinalapp.entity.Producto;
import com.diegodeleon.kinalapp.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoService implements IProductoService{
private final ProductoRepository productoRepository;
public ProductoService(ProductoRepository productoRepository) { this.productoRepository = productoRepository; }

    //Listar productos
    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    //Guardar producto
    @Override
    public Producto guardarProducto(Producto producto) {
        validarProducto(producto);
        if (producto.getEstado()==0){
            producto.setEstado(1);
        }
        return productoRepository.save(producto);
    }

    //Buscar por el codigo del producto
    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> buscarPorCodigoProducto(int codigoProducto) {
        return productoRepository.findById(codigoProducto);
    }

    //Actualizar un producto
    @Override
    public Producto actualizarProducto(int codigoProducto, Producto producto) {
        //Actualiza un cliente existente
        if(!productoRepository.existsById(codigoProducto)){
            throw new RuntimeException("El producto no se encontró mediante el código" +codigoProducto);
        }
        producto.setCodigoProducto(codigoProducto);
        validarProducto(producto);
        return productoRepository.save(producto);
    }

    //Eliminar un producto
    @Override
    public void eliminarProducto(int codigoProducto) {
        //Eliminar un cliente
        if(!productoRepository.existsById(codigoProducto)){
            throw new RuntimeException("El producto no se encontró mediante el código" +codigoProducto);
        }
        productoRepository.deleteById(codigoProducto);
    }

    //Buscar por el codigo del producto
    @Override
    @Transactional
    public boolean existePorCodigoProducto(int codigoProducto) {
        return productoRepository.existsById(codigoProducto);
    }

    //Metodo privado, validar productos
    private void validarProducto(Producto producto){
        if (producto.getCodigoProducto() == 0) {
            throw new IllegalArgumentException("El Código del Producto es obligatorio");
        }

        if (producto.getNombreProducto()== null || producto.getNombreProducto().trim().isEmpty()){
            throw new IllegalArgumentException("El nombre es un dato obligatorio");
        }

        if (producto.getPrecio() == 0 || producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        if (producto.getStock() == 0 || producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }

    }

    //Listar productos activos
    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarProductosActivos() {
        return productoRepository.findByEstado(1);
    }
}
