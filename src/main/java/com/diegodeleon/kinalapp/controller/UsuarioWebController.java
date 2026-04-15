package com.diegodeleon.kinalapp.controller;

import com.diegodeleon.kinalapp.entity.Usuario;
import com.diegodeleon.kinalapp.service.IUsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web/usuarios")
public class UsuarioWebController {

    private final IUsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioWebController(IUsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        model.addAttribute("titulo", "Gestión de Usuarios");
        return "usuarios/list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("titulo", "Nuevo Usuario");
        model.addAttribute("accion", "Crear");
        return "usuarios/form";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario,
                                 RedirectAttributes flash) {
        try {
            //validar que el username no exista
            if (usuarioService.buscarPorUsername(usuario.getUsername()).isPresent()) {
                flash.addFlashAttribute("error", "El nombre de usuario ya existe");
                return "redirect:/web/usuarios/nuevo";
            }

            //encriptar contraseña (como el metodo main)
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

            if (usuario.getEstado() == 0) {
                usuario.setEstado(1);
            }
            usuarioService.guardarUsuario(usuario);
            flash.addFlashAttribute("success", "Usuario guardado exitosamente");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/web/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id,
                                          Model model,
                                          RedirectAttributes flash) {
        return usuarioService.buscarPorCodigoUsuario(id)
                .map(usuario -> {
                    // No mostrar la contraseña en el formulario
                    usuario.setPassword("");
                    model.addAttribute("usuario", usuario);
                    model.addAttribute("titulo", "Editar Usuario");
                    model.addAttribute("accion", "Actualizar");
                    return "usuarios/form";
                })
                .orElseGet(() -> {
                    flash.addFlashAttribute("error", "Usuario no encontrado");
                    return "redirect:/web/usuarios";
                });
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarUsuario(@PathVariable Long id,
                                    @ModelAttribute Usuario usuario,
                                    RedirectAttributes flash) {
        try {
            Usuario existingUser = usuarioService.buscarPorCodigoUsuario(id).get();

            //Verificar si el nuevo username ya existe, y no es el mismo usuario
            if (!existingUser.getUsername().equals(usuario.getUsername()) &&
                    usuarioService.buscarPorUsername(usuario.getUsername()).isPresent()) {
                flash.addFlashAttribute("error", "El nombre de usuario ya existe");
                return "redirect:/web/usuarios/editar/" + id;
            }

            //Si la contraseña está vacía, mantener la existente
            if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
                usuario.setPassword(existingUser.getPassword());
            } else {
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }

            usuarioService.actualizarUsuario(id, usuario);
            flash.addFlashAttribute("success", "Usuario actualizado exitosamente");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", "Error al actualizar: " + e.getMessage());
        }
        return "redirect:/web/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id,
                                  RedirectAttributes flash) {
        try {
            //No permitir eliminar el propio usuario
            usuarioService.eliminarUsuario(id);
            flash.addFlashAttribute("success", "Usuario eliminado exitosamente");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/web/usuarios";
    }
}