package com.reneloper.msvc_usuarios.services;

import com.reneloper.msvc_usuarios.models.entities.Usuario;
import com.reneloper.msvc_usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll(Sort.sort(Usuario.class).by(Usuario::getNombre).ascending());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return this.usuarioRepository.findById(id);
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        return this.usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public Optional<Usuario> update(Usuario usuario, Long id) {
        var user = this.usuarioRepository.findById(id);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        var userUpdate = user.get();
        if (usuario.getNombre() != null) {
            userUpdate.setNombre(usuario.getNombre());
        }
        if (usuario.getApellido() != null) {
            userUpdate.setApellido(usuario.getApellido());
        }
        if (usuario.getEmail() != null) {
            userUpdate.setEmail(usuario.getEmail());
        }
        if (usuario.getPassword() != null) {
            userUpdate.setPassword(usuario.getPassword());
        }
        return Optional.of(this.usuarioRepository.save(userUpdate));
    }

    @Override
    @Transactional
    public Optional<Map<String, String>> deleteById(Long id) {
        var user = this.usuarioRepository.findById(id);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        this.usuarioRepository.deleteById(id);
        return Optional.of(Map.of("message", "user deleted"));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByEmail(String email) {
        return this.usuarioRepository.findByEmail(email);
    }
}
