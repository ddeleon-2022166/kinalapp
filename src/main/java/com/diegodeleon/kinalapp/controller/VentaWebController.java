package com.diegodeleon.kinalapp.controller;

import com.diegodeleon.kinalapp.entity.*;
import com.diegodeleon.kinalapp.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/web/ventas")
public class VentaWebController {

    private final IVentaService ventaService;
    private final IClienteService clienteService;
    private final IProductoService productoService;
    private final IDetalleVentaService detalleVentaService;
    private final IUsuarioService usuarioService;

    public VentaWebController(IVentaService ventaService,
                              IClienteService clienteService,
                              IProductoService productoService,
                              IDetalleVentaService detalleVentaService,
                              IUsuarioService usuarioService) {
        this.ventaService = ventaService;
        this.clienteService = clienteService;
        this.productoService = productoService;
        this.detalleVentaService = detalleVentaService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listarVentas(Model model) {
        model.addAttribute("ventas", ventaService.listarVentas());
        model.addAttribute("titulo", "Gestión de Ventas");
        return "ventas/list";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioNueva(Model model) {
        model.addAttribute("venta", new Venta());
        model.addAttribute("clientes", clienteService.listarActivos());
        model.addAttribute("productos", productoService.listarProductosActivos());
        model.addAttribute("titulo", "Nueva Venta");
        return "ventas/form";
    }

    @PostMapping("/guardar")
    public String guardarVenta(@ModelAttribute Venta venta,
                               @RequestParam(value = "productosIds", required = false) List<Long> productosIds,
                               @RequestParam(value = "cantidades", required = false) List<Integer> cantidades,
                               @RequestParam(value = "precios", required = false) List<Double> precios,
                               Authentication authentication,
                               RedirectAttributes flash) {
        try {
            System.out.println("=== GUARDANDO VENTA ===");
            System.out.println("Cliente DPI recibido: " + venta.getClienteDpi());

            // Validar que haya productos
            if (productosIds == null || productosIds.isEmpty()) {
                flash.addFlashAttribute("error", "Debe agregar al menos un producto");
                return "redirect:/web/ventas/nueva";
            }

            // Fecha actual
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            venta.setFecha(LocalDate.now().format(formatter));
            venta.setEstado(1);

            // Calcular total
            double totalCalculado = 0;
            for (int i = 0; i < productosIds.size(); i++) {
                totalCalculado += cantidades.get(i) * precios.get(i);
            }
            venta.setTotal(totalCalculado);
            System.out.println("Total calculado: " + totalCalculado);

            // Usuario autenticado - SETEAR EL ID DEL USUARIO
            String username = authentication.getName();
            Usuario usuario = usuarioService.buscarPorUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
            venta.setUsuarioId(usuario.getCodigoUsuario());  // ← IMPORTANTE: Setear el ID
            venta.setUsuario(usuario);  // También setear el objeto

            // El cliente DPI ya viene del formulario, pero asegurémonos
            System.out.println("Cliente DPI antes de guardar: " + venta.getClienteDpi());

            // Guardar venta
            Venta ventaGuardada = ventaService.guardarVenta(venta);
            System.out.println("Venta guardada con ID: " + ventaGuardada.getCodigoVenta());

            // Guardar detalles
            for (int i = 0; i < productosIds.size(); i++) {
                DetalleVenta detalle = new DetalleVenta();
                detalle.setVentaId(ventaGuardada.getCodigoVenta());  // Setear el ID de la venta
                detalle.setProductoId(productosIds.get(i));          // Setear el ID del producto
                detalle.setCantidad(cantidades.get(i));
                detalle.setPrecioUnitario(precios.get(i));
                detalle.setSubtotal(cantidades.get(i) * precios.get(i));

                detalleVentaService.guardarDetalleVenta(detalle);
                System.out.println("Detalle guardado - Producto: " + productosIds.get(i));

                // Actualizar stock
                Producto producto = productoService.buscarPorCodigoProducto(productosIds.get(i)).get();
                producto.setStock(producto.getStock() - cantidades.get(i));
                productoService.guardarProducto(producto);
            }

            flash.addFlashAttribute("success", "Venta registrada exitosamente");
            return "redirect:/web/ventas/ver/" + ventaGuardada.getCodigoVenta();

        } catch (Exception e) {
            e.printStackTrace();
            flash.addFlashAttribute("error", "Error al guardar la venta: " + e.getMessage());
            return "redirect:/web/ventas/nueva";
        }
    }

    @GetMapping("/ver/{id}")
    public String verVenta(@PathVariable Long id, Model model, RedirectAttributes flash) {
        return ventaService.buscarPorCodigoVenta(id)
                .map(venta -> {
                    model.addAttribute("venta", venta);
                    model.addAttribute("detalles", detalleVentaService.listarDetallesVentas().stream()
                            .filter(d -> d.getVenta() != null && d.getVenta().getCodigoVenta().equals(id))
                            .toList());
                    model.addAttribute("titulo", "Detalle de Venta");
                    return "ventas/view";
                })
                .orElseGet(() -> {
                    flash.addFlashAttribute("error", "Venta no encontrada");
                    return "redirect:/web/ventas";
                });
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarVenta(@PathVariable Long id, RedirectAttributes flash) {
        try {
            ventaService.eliminarVenta(id);
            flash.addFlashAttribute("success", "Venta eliminada exitosamente");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/web/ventas";
    }
}