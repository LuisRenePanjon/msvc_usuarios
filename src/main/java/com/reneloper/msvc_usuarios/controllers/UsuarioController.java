package com.reneloper.msvc_usuarios.controllers;

import com.reneloper.msvc_usuarios.models.entities.Usuario;
import com.reneloper.msvc_usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll(){
        var users = this.usuarioService.findAll();
        if (users.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id){
        var user = this.usuarioService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> findByEmail(@PathVariable String email){
        var user = this.usuarioService.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Usuario usuario){
        try {
            var user = this.usuarioService.save(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }catch (Exception e){
            var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
            pd.setDetail("No se pudo crear el usuario");
            pd.setProperty("usuario", "No se pudo crear el usuario");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pd);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@RequestBody Usuario usuario, @PathVariable Long id){
        var user = this.usuarioService.update(usuario, id);
        return user.map(value -> ResponseEntity.status(HttpStatus.CREATED).body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteById(@PathVariable Long id){
        var user = this.usuarioService.deleteById(id);
        return user.map(stringStringMap -> ResponseEntity.ok().body(stringStringMap)).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
