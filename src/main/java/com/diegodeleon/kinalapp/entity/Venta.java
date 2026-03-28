package com.diegodeleon.kinalapp.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_venta")
    private Long codigoVenta;

    @Column
    private String fecha;

    @Column
    private double total;

    @Column
    private int estado;

    @Transient  // Esta anotación evita que se guarde en la base de datos
    private Long usuarioId;

    @Transient  // Esta anotación evita que se guarde en la base de datos
    private String clienteDpi;

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

    public Venta(Long codigoVenta, String fecha, double total, int estado, Long usuarioId, String clienteDpi, Usuario usuario, Cliente cliente, List<DetalleVenta> detalles) {
        this.codigoVenta = codigoVenta;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.usuarioId = usuarioId;
        this.clienteDpi = clienteDpi;
        this.usuario = usuario;
        this.cliente = cliente;
        this.detalles = detalles;
    }

    public Long getCodigoVenta() { return codigoVenta; }

    public void setCodigoVenta(Long codigoVenta) { this.codigoVenta = codigoVenta; }

    public String getFecha() { return fecha; }

    public void setFecha(String fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }

    public void setTotal(double total) { this.total = total; }

    public int getEstado() { return estado; }

    public void setEstado(int estado) { this.estado = estado; }

    public Long getUsuarioId() { return usuarioId; }

    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getClienteDpi() { return clienteDpi; }

    public void setClienteDpi(String clienteDpi) { this.clienteDpi = clienteDpi; }

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<DetalleVenta> getDetalles() { return detalles; }

    public void setDetalles(List<DetalleVenta> detalles) { this.detalles = detalles; }
}