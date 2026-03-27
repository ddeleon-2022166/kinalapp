package com.diegodeleon.kinalapp.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "detalle_venta")
public class DetalleVenta {

    @Id
    @Column(name = "codigo_detalle_venta")
    private Long codigoDetalleVenta;

    @Column
    private int cantidad;

    @Column(name = "precio_unitario")
    private double precioUnitario;

    @Column
    private double subtotal;

    @ManyToOne
    @JoinColumn(name = "ventas_codigo_venta")
    @JsonIgnore
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "productos_codigo_producto")
    private Producto producto;

    public DetalleVenta() {}

    public DetalleVenta(Long codigoDetalleVenta, int cantidad, double precioUnitario, double subtotal, Venta venta, Producto producto) {
        this.codigoDetalleVenta = codigoDetalleVenta;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.venta = venta;
        this.producto = producto;
    }

    public Long getCodigoDetalleVenta() { return codigoDetalleVenta; }

    public void setCodigoDetalleVenta(Long codigoDetalleVenta) { this.codigoDetalleVenta = codigoDetalleVenta; }

    public int getCantidad() { return cantidad; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }

    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public double getSubtotal() { return subtotal; }

    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public Venta getVenta() { return venta; }

    public void setVenta(Venta venta) { this.venta = venta; }

    public Producto getProducto() { return producto; }

    public void setProducto(Producto producto) { this.producto = producto; }
}