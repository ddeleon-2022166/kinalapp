package com.diegodeleon.kinalapp.service;
import com.diegodeleon.kinalapp.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    //Metodo que lista todos los usuarios
    List<Usuario> listarUsuarios();

    //Metodo que permite guardar usuarios
    Usuario guardarUsuario(Usuario usuario);
    Optional<Usuario> buscarPorCodigoUsuario(Long codigoUsuario);

    //Metodo que actualiza un usuario
    Usuario actualizarUsuario(Long codigoUsuario, Usuario usuario);

    //Metodo que elimina un usuario
    void eliminarUsuario(Long codigoUsuario);

    //Metodo que busca un usuario por su codigo
    boolean existePorCodigoUsuario(Long codigoUsuario);

    //Metodo que devuelve los usuarios activos
    List<Usuario> listarUsuariosActivos();

    //Metodo que busca usuarios por su username
    Optional<Usuario> buscarPorUsername(String username);
}
