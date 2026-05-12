package com.diegodeleon.kinalapp.controller;

import com.diegodeleon.kinalapp.entity.Cliente;
import com.diegodeleon.kinalapp.service.IClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/clientes")
public class ClienteWebController {

    private final IClienteService clienteService;

    public ClienteWebController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("titulo", "Gestión de Clientes");
        return "clientes/list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("titulo", "Nuevo Cliente");
        model.addAttribute("accion", "Crear");
        return "clientes/form";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente,
                                 RedirectAttributes flash) {
        try {
            if (cliente.getEstado() == 0) {
                cliente.setEstado(1);
            }
            clienteService.guardar(cliente);
            flash.addFlashAttribute("success", "Cliente guardado exitosamente");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/web/clientes";
    }

    @GetMapping("/editar/{dpi}")
    public String mostrarFormularioEditar(@PathVariable String dpi,
                                          Model model,
                                          RedirectAttributes flash) {
        return clienteService.buscarPorDPI(dpi)
                .map(cliente -> {
                    model.addAttribute("cliente", cliente);
                    model.addAttribute("titulo", "Editar Cliente");
                    model.addAttribute("accion", "Actualizar");
                    return "clientes/form";
                })
                .orElseGet(() -> {
                    flash.addFlashAttribute("error", "Cliente no encontrado");
                    return "redirect:/web/clientes";
                });
    }

    @GetMapping("/eliminar/{dpi}")
    public String eliminarCliente(@PathVariable String dpi,
                                  RedirectAttributes flash) {
        try {
            clienteService.eliminar(dpi);
            flash.addFlashAttribute("success", "Cliente eliminado exitosamente");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/web/clientes";
    }
}