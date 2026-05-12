package com.diegodeleon.kinalapp.controller;

import com.diegodeleon.kinalapp.entity.Producto;
import com.diegodeleon.kinalapp.service.IProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/productos")
public class ProductoWebController {

    private final IProductoService productoService;

    public ProductoWebController(IProductoService productoService) {
        this.productoService = productoService;
    }

    //Listar productos
    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.listarProductos());
        model.addAttribute("titulo", "Gestión de Productos");
        return "productos/list";
    }

    //Mostrar formulario para nuevo producto
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("titulo", "Nuevo Producto");
        model.addAttribute("accion", "Crear");
        return "productos/form";
    }

    //Guardar producto
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto,
                                  RedirectAttributes flash) {
        try {
            if (producto.getEstado() == 0) {
                producto.setEstado(1); // Activo por defecto
            }
            productoService.guardarProducto(producto);
            flash.addFlashAttribute("success", "Producto guardado exitosamente");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/web/productos";
    }

    //Mostrar formulario para editar
    @GetMapping("/editar/{codigoProducto}")
    public String mostrarFormularioEditar(@PathVariable Long codigoProducto,
                                          Model model,
                                          RedirectAttributes flash) {
        return productoService.buscarPorCodigoProducto(codigoProducto)
                .map(producto -> {
                    model.addAttribute("producto", producto);
                    model.addAttribute("titulo", "Editar Producto");
                    model.addAttribute("accion", "Actualizar");
                    return "productos/form";
                })
                .orElseGet(() -> {
                    flash.addFlashAttribute("error", "Producto no encontrado");
                    return "redirect:/web/productos";
                });
    }

    // Eliminar producto
    @GetMapping("/eliminar/{codigoProducto}")
    public String eliminarProducto(@PathVariable Long codigoProducto,
                                   RedirectAttributes flash) {
        try {
            productoService.eliminarProducto(codigoProducto);
            flash.addFlashAttribute("success", "Producto eliminado exitosamente");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/web/productos";
    }
}