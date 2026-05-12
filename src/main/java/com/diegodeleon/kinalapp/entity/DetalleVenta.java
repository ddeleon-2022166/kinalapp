package com.diegodeleon.kinalapp.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "detalle_venta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_detalle_venta")
    private Long codigoDetalleVenta;

    @Column
    private Integer cantidad;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    @Column
    private Double subtotal;

    @Transient
    private Long ventaId;

    @Transient
    private Long productoId;

    @ManyToOne
    @JoinColumn(name = "ventas_codigo_venta")
    @JsonIgnore
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "productos_codigo_producto")
    private Producto producto;

    public DetalleVenta() {}

    public DetalleVenta(Long codigoDetalleVenta, Integer cantidad, Double precioUnitario, Double subtotal, Long ventaId, Long productoId, Venta venta, Producto producto) {
        this.codigoDetalleVenta = codigoDetalleVenta;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.ventaId = ventaId;
        this.productoId = productoId;
        this.venta = venta;
        this.producto = producto;
    }

    public Long getCodigoDetalleVenta() { return codigoDetalleVenta; }

    public void setCodigoDetalleVenta(Long codigoDetalleVenta) { this.codigoDetalleVenta = codigoDetalleVenta; }

    public Integer getCantidad() { return cantidad; }

    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getPrecioUnitario() { return precioUnitario; }

    public void setPrecioUnitario(Double precioUnitario) { this.precioUnitario = precioUnitario; }

    public Double getSubtotal() { return subtotal; }

    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public Long getVentaId() { return ventaId; }

    public void setVentaId(Long ventaId) { this.ventaId = ventaId; }

    public Long getProductoId() { return productoId; }

    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Venta getVenta() { return venta; }

    public void setVenta(Venta venta) { this.venta = venta; }

    public Producto getProducto() { return producto; }

    public void setProducto(Producto producto) { this.producto = producto; }
}