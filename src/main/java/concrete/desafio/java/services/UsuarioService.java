package concrete.desafio.java.services;

import concrete.desafio.java.models.Login;
import concrete.desafio.java.models.Usuario;
import concrete.desafio.java.repositories.LoginRepositorio;
import concrete.desafio.java.repositories.RoleRepositorio;
import concrete.desafio.java.repositories.UsuarioRepositorio;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.HashSet;

/**
 * Created by rony on 09/01/17.
 */

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private LoginRepositorio loginRepositorio;

    @Autowired
    private RoleRepositorio roleRepositorio;

    private Log logger = LogFactory.getLog(UsuarioService.class);

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SecurityServiceImpl securityService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public Usuario obtemUsuario( Usuario usuario ) {
        return usuarioRepositorio.findByNomeUsuarioAndPassword(usuario.getNomeUsuario(), usuario.getPassword());
    }

    public Usuario adicionaUsuario(Usuario usuario) {

        Usuario usuarioExistente = usuarioRepositorio.findByEmail(usuario.getEmail());
        if(usuarioExistente != null){
            throw new EntityExistsException();
        }
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        usuario.setRoles(new HashSet<>(roleRepositorio.findAll()));
        Login login = new Login( null, obtemTokenUsuario(usuario) );
        login.setUltimoAcesso(Calendar.getInstance().getTime());
        usuario.setLogin(login);
        usuario.setDataCriacao(Calendar.getInstance().getTime());
        usuario.setUltimaAtualizacao(Calendar.getInstance().getTime());
        loginRepositorio.saveAndFlush(login);
        usuarioRepositorio.save(usuario);
        logger.info(String.format("Usuario cadastrado com sucesso. usuario: %s , password: %s, roles: %d", usuario.getNomeUsuario(), usuario.getPassword(), usuario.getRoles().size()));
        return usuario;
    }

    public void realizaLogin( Usuario usuario ){
        securityService.autologin( usuario.getEmail(), usuario.getPassword() );
    }

    public String obtemTokenUsuario( Usuario usuario ){
        return bCryptPasswordEncoder.encode(getUserDetails(usuario).toString());
    }

    public UserDetails getUserDetails( Usuario usuario ){
        return userDetailsService.getUserDetails( usuario );
    }

    public Usuario obtemUsuarioPorId( String id ) {
        return usuarioRepositorio.getOne( new Long(id) );
    }

    public Usuario validaCredenciais( String email, String password ) {
        Usuario usuario = usuarioRepositorio.findByEmail( email);
        if (usuario == null || password == null){
            throw new EntityNotFoundException();
        }else{
            usuario.setPassword( bCryptPasswordEncoder.encode( password ) );
            realizaLogin( usuario );
            return usuario;
        }
    }
}
