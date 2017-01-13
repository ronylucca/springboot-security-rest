package concrete.desafio.java.services;

import concrete.desafio.java.models.Role;
import concrete.desafio.java.models.Usuario;
import concrete.desafio.java.repositories.UsuarioRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rony on 09/01/17.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(email);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : usuario.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            logger.info(String.format("Adicionando Role: %s para usuario.", role.getName()));

        }

        return new org.springframework.security.core.userdetails.User(usuario.getNomeUsuario(), usuario.getPassword(), grantedAuthorities);
    }

    public UserDetails getUserDetails(Usuario usuario){

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : usuario.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(usuario.getNomeUsuario(), usuario.getPassword(), grantedAuthorities);
    }
}