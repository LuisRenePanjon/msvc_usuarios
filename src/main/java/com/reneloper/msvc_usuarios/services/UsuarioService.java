package com.reneloper.msvc_usuarios.services;

import com.reneloper.msvc_usuarios.models.entities.Usuario;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);

    Usuario save(Usuario usuario);

    Optional<Usuario> update(Usuario usuario, Long id);

    Optional<Map<String, String>> deleteById(Long id);
    Optional<Usuario> findByEmail(String email);

}
