package com.diegodeleon.kinalapp.service;

import com.diegodeleon.kinalapp.entity.Usuario;
import com.diegodeleon.kinalapp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements IUsuarioService{
private final UsuarioRepository usuarioRepository;
public UsuarioService(UsuarioRepository usuarioRepository) { this.usuarioRepository = usuarioRepository; }

    //Listar usuarios
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    //Guardar usuario
    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        validarUsuario(usuario);
        if (usuario.getEstado()==0){
            usuario.setEstado(1);
        }
        return usuarioRepository.save(usuario);
    }

    //Buscar por el codigo del usuario
    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorCodigoUsuario(Long codigoUsuario) {
        return usuarioRepository.findById(codigoUsuario);
    }

    //Actualizar un usuario
    @Override
    public Usuario actualizarUsuario(Long codigoUsuario, Usuario usuario) {
        //Actualiza un usuario existente
        if(!usuarioRepository.existsById(codigoUsuario)){
            throw new RuntimeException("El usuario no se encontró mediante el código" +codigoUsuario);
        }
        usuario.setCodigoUsuario(codigoUsuario);
        validarUsuario(usuario);
        return usuarioRepository.save(usuario);
    }

    //Eliminar un usuario
    @Override
    public void eliminarUsuario(Long codigoUsuario) {
        if(!usuarioRepository.existsById(codigoUsuario)){
            throw new RuntimeException("El usuario no se encontró mediante el código" +codigoUsuario);
        }
        usuarioRepository.deleteById(codigoUsuario);
    }

    //Buscar por el codigo del usuario
    @Override
    @Transactional
    public boolean existePorCodigoUsuario(Long codigoUsuario) {
        return usuarioRepository.existsById(codigoUsuario);
    }

    //Metodo privado, validar usuarios
    private void validarUsuario(Usuario usuario){
        if (usuario.getCodigoUsuario() == 0) {
            throw new IllegalArgumentException("El Código del usuario es obligatorio");
        }

        if (usuario.getUsername()== null || usuario.getUsername().trim().isEmpty()){
            throw new IllegalArgumentException("El nombre es un dato obligatorio");
        }

        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()){
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()){
            throw new IllegalArgumentException("El email es obligatorio");
        }

        if (!usuario.getEmail().contains("@")){
            throw new IllegalArgumentException("El email no es válido");
        }

        if (usuario.getRol() == null || usuario.getRol().trim().isEmpty()){
            throw new IllegalArgumentException("El rol es obligatorio");
        }

    }

    //Listar usuarios activos
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuariosActivos() {
        return usuarioRepository.findByEstado(1);
    }

    //Buscar usuarios
    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
