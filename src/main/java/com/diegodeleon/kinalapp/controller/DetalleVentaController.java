package com.diegodeleon.kinalapp.controller;

import com.diegodeleon.kinalapp.entity.DetalleVenta;
import com.diegodeleon.kinalapp.service.IDetalleVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalle-ventas")
public class DetalleVentaController {

    private final IDetalleVentaService detalleVentaService;

    public DetalleVentaController(IDetalleVentaService detalleVentaService) {
        this.detalleVentaService = detalleVentaService;
    }

    //GET que lista todos los detalles de venta
    @GetMapping
    public ResponseEntity<List<DetalleVenta>> listarDetallesVentas() {
        List<DetalleVenta> detalles = detalleVentaService.listarDetallesVentas();
        return ResponseEntity.ok(detalles);
    }

    //GET que busca un detalle de venta mediante el codigo del mismo
    @GetMapping("/{codigoDetalleVenta}")
    public ResponseEntity<DetalleVenta> buscarPorCodigoDetalleVenta(@PathVariable Long codigoDetalleVenta) {
        return detalleVentaService.buscarPorCodigoVenta(codigoDetalleVenta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //POST crea un nuevo detalle de venta
    @PostMapping
    public ResponseEntity<?> guardarDetalleVenta(@RequestBody DetalleVenta detalleVenta) {
        try {
            DetalleVenta nuevoDetalle = detalleVentaService.guardarDetalleVenta(detalleVenta);
            return new ResponseEntity<>(nuevoDetalle, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //DELETE elimina un detalle de venta
    @DeleteMapping("/{codigoDetalleVenta}")
    public ResponseEntity<Void> eliminarDetalleVenta(@PathVariable Long codigoDetalleVenta) {
        try {
            detalleVentaService.eliminarDetalleVenta(codigoDetalleVenta);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //PUT actualizar detalle de venta mediante el codigo del mismo
    @PutMapping("/{codigoDetalleVenta}")
    public ResponseEntity<?> actualizarDetalleVenta(@PathVariable Long codigoDetalleVenta,
                                                    @RequestBody DetalleVenta detalleVenta){
        try{
            DetalleVenta detalleActualizado =
                    detalleVentaService.actualizarDetalleVenta(codigoDetalleVenta, detalleVenta);
            return ResponseEntity.ok(detalleActualizado);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}