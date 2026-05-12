package com.diegodeleon.kinalapp.service;

import com.diegodeleon.kinalapp.entity.Cliente;
import com.diegodeleon.kinalapp.entity.Usuario;
import com.diegodeleon.kinalapp.entity.Venta;
import com.diegodeleon.kinalapp.repository.ClienteRepository;
import com.diegodeleon.kinalapp.repository.UsuarioRepository;
import com.diegodeleon.kinalapp.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService implements IVentaService{
    private final VentaRepository ventaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;

    public VentaService(VentaRepository ventaRepository,
                        UsuarioRepository usuarioRepository,
                        ClienteRepository clienteRepository) {
        this.ventaRepository = ventaRepository;
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
    }

    //Listar ventas
    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    //Buscar por el codigo de la venta
    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> buscarPorCodigoVenta(Long codigoVenta) {
        return ventaRepository.findById(codigoVenta);
    }

    //Guardar una venta
    @Override
    public Venta guardarVenta(Venta venta) {
        validarVenta(venta);

        Usuario usuario = usuarioRepository.findById(venta.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + venta.getUsuarioId()));

        Cliente cliente = clienteRepository.findById(venta.getClienteDpi())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con DPI: " + venta.getClienteDpi()));

        venta.setUsuario(usuario);
        venta.setCliente(cliente);

        if (venta.getEstado() == 0) {
            venta.setEstado(1);
        }

        return ventaRepository.save(venta);
    }

    //Actualizar una venta
    @Override
    public Venta actualizarVenta(Long codigoVenta, Venta venta) {
        if(!ventaRepository.existsById(codigoVenta)){
            throw new RuntimeException("La venta no se encontró por el código: " + codigoVenta);
        }
        venta.setCodigoVenta(codigoVenta);
        validarVenta(venta);

        if (venta.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(venta.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + venta.getUsuarioId()));
            venta.setUsuario(usuario);
        }

        if (venta.getClienteDpi() != null) {
            Cliente cliente = clienteRepository.findById(venta.getClienteDpi())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado con DPI: " + venta.getClienteDpi()));
            venta.setCliente(cliente);
        }

        return ventaRepository.save(venta);
    }

    //Eliminar una venta
    @Override
    public void eliminarVenta(Long codigoVenta) {
        if(!ventaRepository.existsById(codigoVenta)){
            throw new RuntimeException("La venta no se encontró por el código: " + codigoVenta);
        }
        ventaRepository.deleteById(codigoVenta);
    }

    //Verificar si existe una venta por código
    @Override
    public boolean existePorCodigoVenta(Long codigoVenta) {
        return ventaRepository.existsById(codigoVenta);
    }

    //Listar ventas activas
    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarVentasActivos() {
        return ventaRepository.findByEstado(1);
    }

    //Metodo privado para validar venta
    private void validarVenta(Venta venta) {
        if (venta.getFecha() == null || venta.getFecha().trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }

        if (venta.getTotal() <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a 0");
        }

        if (venta.getUsuarioId() == null && venta.getUsuario() == null) {
            throw new IllegalArgumentException("El ID del usuario es obligatorio");
        }

        if (venta.getClienteDpi() == null && venta.getCliente() == null) {
            throw new IllegalArgumentException("El DPI del cliente es obligatorio");
        }
    }
}