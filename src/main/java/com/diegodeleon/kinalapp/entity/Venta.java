package com.diegodeleon.kinalapp.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_venta")
    private int codigoVenta;

    @Column
    private String fecha;

    @Column
    private double total;

    @Column
    private int estado;

    @ManyToOne
    @JoinColumn(name = "usuarios_codigo_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "clientes_dpi_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalles;

    public Venta() {
    }

    public Venta(int codigoVenta, String fecha, double total, int estado, Usuario usuario, Cliente cliente, List<DetalleVenta> detalles) {
        this.codigoVenta = codigoVenta;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.usuario = usuario;
        this.cliente = cliente;
        this.detalles = detalles;
    }

    public int getCodigoVenta() { return codigoVenta; }

    public void setCodigoVenta(int codigoVenta) { this.codigoVenta = codigoVenta; }

    public String getFecha() { return fecha; }

    public void setFecha(String fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }

    public void setTotal(double total) { this.total = total; }

    public int getEstado() { return estado; }

    public void setEstado(int estado) { this.estado = estado; }

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<DetalleVenta> getDetalles() { return detalles; }

    public void setDetalles(List<DetalleVenta> detalles) { this.detalles = detalles; }
}

