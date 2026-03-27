package com.diegodeleon.kinalapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//a
@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @Column(name = "codigo_producto")
    private Long codigoProducto;
    @Column
    private String nombreProducto;
    @Column
    private double precio;
    @Column
    private int stock;
    @Column
    private int estado;

    public Producto(){
    }


    public Producto(Long codigoProducto, String nombreProducto, int stock, double precio, int estado) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.stock = stock;
        this.precio = precio;
        this.estado = estado;
    }

    public Long getCodigoProducto() { return codigoProducto; }

    public void setCodigoProducto(Long codigoProducto) { this.codigoProducto = codigoProducto; }

    public String getNombreProducto() { return nombreProducto; }

    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }

    public void setStock(int stock) { this.stock = stock; }

    public int getEstado() { return estado; }

    public void setEstado(int estado) { this.estado = estado; }
}
