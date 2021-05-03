package com.inverenlace.sbngblog.security.util;

import java.util.HashSet;
import java.util.Set;

import com.inverenlace.sbngblog.security.entity.Rol;
import com.inverenlace.sbngblog.security.entity.Usuario;
import com.inverenlace.sbngblog.security.enums.RolNombre;

import com.inverenlace.sbngblog.security.service.RolService;
import com.inverenlace.sbngblog.security.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CreateAdmin implements CommandLineRunner {

    @Autowired
    UsuarioService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RolService roleService;

    @Override
    public void run(String... args) throws Exception {
        Usuario user = new Usuario();
        String passwordEncoded = passwordEncoder.encode("admin");
        user.setNombre("admin");
        user.setNombreUsuario("admin");
        user.setPassword(passwordEncoded);
        user.setEmail("admin@email.com");
        Rol roleAdmin = roleService.getByRolNombre(RolNombre.ROLE_ADMIN).get();
        Rol roleUser = roleService.getByRolNombre(RolNombre.ROLE_USER).get();
        Set<Rol> roles = new HashSet<>();
        roles.add(roleAdmin);
        roles.add(roleUser);
        user.setRoles(roles);
        userService.save(user);
    }
}
