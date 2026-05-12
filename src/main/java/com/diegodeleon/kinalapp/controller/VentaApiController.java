package com.diegodeleon.kinalapp.controller;

import com.diegodeleon.kinalapp.entity.Producto;
import com.diegodeleon.kinalapp.service.IProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaApiController {

    private final IProductoService productoService;

    public VentaApiController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> getProductosActivos() {
        return ResponseEntity.ok(productoService.listarProductosActivos());
    }
}