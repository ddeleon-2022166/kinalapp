package com.diegodeleon.kinalapp.controller;

import com.diegodeleon.kinalapp.entity.Venta;
import com.diegodeleon.kinalapp.service.IVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final IVentaService ventaService;

    public VentaController(IVentaService ventaService) {
        this.ventaService = ventaService;
    }

    //GET que lista todas las ventas
    @GetMapping
    public ResponseEntity<List<Venta>> listarVentas() {
        List<Venta> ventas = ventaService.listarVentas();
        return ResponseEntity.ok(ventas);
    }

    //GET que busca una venta mediante el codigo de la misma
    @GetMapping("/{codigoVenta}")
    public ResponseEntity<Venta> buscarPorCodigoVenta(@PathVariable Long codigoVenta) {
        return ventaService.buscarPorCodigoVenta(codigoVenta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //POST crea una nueva venta
    @PostMapping
    public ResponseEntity<?> guardarVenta(@RequestBody Venta venta) {
        try {
            Venta nuevaVenta = ventaService.guardarVenta(venta);
            return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //DELETE elimina una venta
    @DeleteMapping("/{codigoVenta}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long codigoVenta) {
        try {
            ventaService.eliminarVenta(codigoVenta);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //GET que lista ventas activas
    @GetMapping("/activos")
    public ResponseEntity<List<Venta>> listarVentasActivos() {
        List<Venta> ventas = ventaService.listarVentasActivos();
        return ResponseEntity.ok(ventas);
    }

    //PUT actualizar venta mediante el codigo de la misma
    @PutMapping("/{codigoVenta}")
    public ResponseEntity<?> actualizarVenta(@PathVariable Long codigoVenta, @RequestBody Venta venta){
        try{
            Venta ventaActualizada = ventaService.actualizarVenta(codigoVenta, venta);
            return ResponseEntity.ok(ventaActualizada);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}