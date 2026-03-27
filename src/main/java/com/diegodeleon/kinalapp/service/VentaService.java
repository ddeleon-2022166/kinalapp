package com.diegodeleon.kinalapp.service;

import com.diegodeleon.kinalapp.entity.Venta;
import com.diegodeleon.kinalapp.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService implements IVentaService{
    private final VentaRepository ventaRepository;
    public VentaService(VentaRepository ventaRepository) { this.ventaRepository = ventaRepository; }

    //Listar ventas
    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    //Guardar una venta
    @Override
    public Venta guardarVenta(Venta venta) {
        validarVenta(venta);
        if (venta.getEstado()==0){
            venta.setEstado(1);
        }
        return ventaRepository.save(venta);
    }

    //Buscar por el codigo de la venta
    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> buscarPorCodigoVenta(Long codigoVenta) {
        return ventaRepository.findById(codigoVenta);
    }

    //Actualizar una venta
    @Override
    public Venta actualizarVenta(Long codigoVenta, Venta venta) {
        //Actualiza un usuario existente
        if(!ventaRepository.existsById(codigoVenta)){
            throw new RuntimeException("La venta no se encontró por el código" +codigoVenta);
        }
        venta.setCodigoVenta(codigoVenta);
        validarVenta(venta);
        return ventaRepository.save(venta);
    }

    //Eliminar una venta
    @Override
    public void eliminarVenta(Long codigoVenta) {
        if(!ventaRepository.existsById(codigoVenta)){
            throw new RuntimeException("La venta no se encontró por el código" +codigoVenta);
        }
        ventaRepository.deleteById(codigoVenta);
    }

    //Buscar por el codigo de la venta
    @Override
    @Transactional
    public boolean existePorCodigoVenta(Long codigoVenta) {
        return ventaRepository.existsById(codigoVenta);
    }

    //Metodo privado, validar ventas
    private void validarVenta(Venta venta){
        if (venta.getCodigoVenta() == 0) {
            throw new IllegalArgumentException("El Código de la venta es obligatorio");
        }

        if (venta.getFecha() == null || venta.getFecha().trim().isEmpty()){
            throw new IllegalArgumentException("La fecha es obligatoria");
        }

        if (venta.getTotal() <= 0){
            throw new IllegalArgumentException("El total debe ser mayor a 0");
        }

        if (venta.getUsuario() == null){
            throw new IllegalArgumentException("El usuario es obligatorio");
        }

        if (venta.getCliente() == null){
            throw new IllegalArgumentException("El cliente es obligatorio");
        }

    }

    //Listar ventas activas
    @Override
    @Transactional(readOnly = true)
    public List<Venta> listarVentasActivos() {
        return ventaRepository.findByEstado(1);
    }
}
