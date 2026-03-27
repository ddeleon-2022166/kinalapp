package com.diegodeleon.kinalapp.controller;

import com.diegodeleon.kinalapp.entity.Usuario;
import com.diegodeleon.kinalapp.service.IUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) { this.usuarioService = usuarioService; }

    //GET que lista todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    //GET que busca un usuario mediante el codigo del mismo
    @GetMapping("/{codigoUsuario}")
    public ResponseEntity<Usuario> buscarPorCodigoUsuario(@PathVariable int codigoUsuario) {
        return usuarioService.buscarPorCodigoUsuario(codigoUsuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //POST crea un nuevo usuario
    @PostMapping
    public ResponseEntity<?> guardarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.guardarUsuario(usuario);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //DELETE elimina un usuario
    @DeleteMapping("/{codigoUsuario}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable int codigoUsuario) {
        try {
            usuarioService.eliminarUsuario(codigoUsuario);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //GET que lista usuarios activos
    @GetMapping("/activos")
    public ResponseEntity<List<Usuario>> listarUsuariosActivos() {
        List<Usuario> usuarios = usuarioService.listarUsuariosActivos();
        return ResponseEntity.ok(usuarios);
    }

    //PUT actualizar usuario mediante el codigo del mismo
    @PutMapping("/{codigoUsuario}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable int codigoUsuario, @RequestBody Usuario usuario){
        try{
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(codigoUsuario, usuario);
            return ResponseEntity.ok(usuarioActualizado);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}