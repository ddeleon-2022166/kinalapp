package com.diegodeleon.kinalapp.controller;

import com.diegodeleon.kinalapp.entity.Producto;
import com.diegodeleon.kinalapp.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;


    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    //GET que lista todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoService.listarProductos();
        return ResponseEntity.ok(productos);
    }

    //GET que busca un producto mediante el codigo del mismo
    @GetMapping("/{codigoProducto}")
    public ResponseEntity<Producto> buscarPorCodigoProducto(@PathVariable int codigoProducto) {
        return productoService.buscarPorCodigoProducto(codigoProducto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //POST crea un nuevo producto
    @PostMapping
    public ResponseEntity<?> guardarProducto(@RequestBody Producto producto) {
        try {
            Producto nuevoProducto = productoService.guardarProducto(producto);
            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //DELETE elimina un producto
    @DeleteMapping("/{codigoProducto}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable int codigoProducto) {
        try {
            productoService.eliminarProducto(codigoProducto);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //GET que lista productos activos
    @GetMapping("/activos")
    public ResponseEntity<List<Producto>> listarProductosActivos() {
        List<Producto> productos = productoService.listarProductosActivos();
        return ResponseEntity.ok(productos);
    }

    //PUT actualizar producto meditante el codigo del mismo
    @PutMapping("/{codigoProducto}")
    public ResponseEntity<?> actualizarProducto(@PathVariable int codigoProducto, @RequestBody Producto producto){
        try{
            Producto productoActualizado = productoService.actualizarProducto(codigoProducto, producto);
            return ResponseEntity.ok(productoActualizado);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}

