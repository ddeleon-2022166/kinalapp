    package com.diegodeleon.kinalapp.service;

    import com.diegodeleon.kinalapp.entity.DetalleVenta;
    import com.diegodeleon.kinalapp.entity.Producto;
    import com.diegodeleon.kinalapp.entity.Venta;
    import com.diegodeleon.kinalapp.repository.DetalleVentaRepository;
    import com.diegodeleon.kinalapp.repository.ProductoRepository;
    import com.diegodeleon.kinalapp.repository.VentaRepository;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.List;
    import java.util.Optional;

    @Service
    @Transactional
    public class DetalleVentaService implements IDetalleVentaService {

        private final DetalleVentaRepository detalleVentaRepository;
        private final VentaRepository ventaRepository;
        private final ProductoRepository productoRepository;

        public DetalleVentaService(DetalleVentaRepository detalleVentaRepository,
                                   VentaRepository ventaRepository,
                                   ProductoRepository productoRepository) {
            this.detalleVentaRepository = detalleVentaRepository;
            this.ventaRepository = ventaRepository;
            this.productoRepository = productoRepository;
        }

        @Override
        @Transactional(readOnly = true)
        public List<DetalleVenta> listarDetallesVentas() {
            return detalleVentaRepository.findAll();
        }

        @Override
        public DetalleVenta guardarDetalleVenta(DetalleVenta detalleVenta) {
            validarDetalleVenta(detalleVenta);

            //No buscar venta por ID si ya tiene la venta asignada
            if (detalleVenta.getVenta() == null && detalleVenta.getVentaId() != null) {
                Venta venta = ventaRepository.findById(detalleVenta.getVentaId())
                        .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + detalleVenta.getVentaId()));
                detalleVenta.setVenta(venta);
            }

            //No buscar producto por ID si ya tiene el producto asignado
            if (detalleVenta.getProducto() == null && detalleVenta.getProductoId() != null) {
                Producto producto = productoRepository.findById(detalleVenta.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalleVenta.getProductoId()));
                detalleVenta.setProducto(producto);
            }

            //calcular de nuevo subtotal
            double cantidad = detalleVenta.getCantidad() != null ? detalleVenta.getCantidad() : 0;
            double precioUnitario = detalleVenta.getPrecioUnitario() != null ? detalleVenta.getPrecioUnitario() : 0;
            detalleVenta.setSubtotal(cantidad * precioUnitario);

            return detalleVentaRepository.save(detalleVenta);
        }

        @Override
        @Transactional(readOnly = true)
        public Optional<DetalleVenta> buscarPorCodigoVenta(Long codigoDetalleVenta) {
            return detalleVentaRepository.findById(codigoDetalleVenta);
        }

        @Override
        public DetalleVenta actualizarDetalleVenta(Long codigoDetalleVenta, DetalleVenta detalleVenta) {
            if (!detalleVentaRepository.existsById(codigoDetalleVenta)) {
                throw new RuntimeException("El detalle de venta no se encontró por el código " + codigoDetalleVenta);
            }

            detalleVenta.setCodigoDetalleVenta(codigoDetalleVenta);
            validarDetalleVenta(detalleVenta);

            if (detalleVenta.getVentaId() != null) {
                Venta venta = ventaRepository.findById(detalleVenta.getVentaId())
                        .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + detalleVenta.getVentaId()));
                detalleVenta.setVenta(venta);
            }

            if (detalleVenta.getProductoId() != null) {
                Producto producto = productoRepository.findById(detalleVenta.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalleVenta.getProductoId()));
                detalleVenta.setProducto(producto);
            }

            // Recalcular subtotal
            double cantidad = detalleVenta.getCantidad() != null ? detalleVenta.getCantidad() : 0;
            double precioUnitario = detalleVenta.getPrecioUnitario() != null ? detalleVenta.getPrecioUnitario() : 0;
            detalleVenta.setSubtotal(cantidad * precioUnitario);

            return detalleVentaRepository.save(detalleVenta);
        }

        @Override
        public void eliminarDetalleVenta(Long codigoDetalleVenta) {
            if (!detalleVentaRepository.existsById(codigoDetalleVenta)) {
                throw new RuntimeException("El detalle de venta no se encontró por el código " + codigoDetalleVenta);
            }
            detalleVentaRepository.deleteById(codigoDetalleVenta);
        }

        @Override
        @Transactional
        public boolean existePorCodigoDetalleVenta(Long codigoDetalleVenta) {
            return detalleVentaRepository.existsById(codigoDetalleVenta);
        }

        private void validarDetalleVenta(DetalleVenta detalleVenta) {
            if (detalleVenta.getCantidad() == null || detalleVenta.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
            }

            if (detalleVenta.getPrecioUnitario() == null || detalleVenta.getPrecioUnitario() <= 0) {
                throw new IllegalArgumentException("El precio unitario debe ser mayor a 0");
            }

            if (detalleVenta.getVentaId() == null) {
                throw new IllegalArgumentException("El ID de la venta es obligatorio");
            }

            if (detalleVenta.getProductoId() == null) {
                throw new IllegalArgumentException("El ID del producto es obligatorio");
            }
        }
    }