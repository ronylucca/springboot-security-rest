package concrete.desafio.java.services;

import concrete.desafio.java.models.Usuario;
import concrete.desafio.java.repositories.UsuarioRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * Created by rony on 09/01/17.
 */
@Service
public class SecurityServiceImpl implements SecurityService{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Override
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            logger.info(String.format("obtendo usuario logado. user: %s", ((UserDetails) userDetails).getUsername()));
            return ((UserDetails)userDetails).getUsername();
        }

        return null;
    }

    @Override
    public void autologin(String email, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        logger.info(String.format("Realizando login com usuario %s" , email));
        logger.info(String.format("UserDetails  usuario: %s ", userDetails.getUsername()));
        logger.info(String.format("UserDetails authorities size: %d", userDetails.getAuthorities().size()));

        authenticate(userDetails, password);
        usernamePasswordAuthenticationToken.setDetails(userDetails);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            atualizaLoginUsuario( email );
            logger.info(String.format("Login com usuario %s realizado com sucesso!", email));
        }
    }

    private void authenticate(UserDetails userDetails, String password) {
        if (!userDetails.getPassword().equals(password)){
            throw new BadCredentialsException("Usuário ou senha inválido.");
        }
    }


    public Long obtemTempoExpiracaoToken(){
        Calendar tempoExpiracao = Calendar.getInstance();
        tempoExpiracao.add(Calendar.MINUTE, 30);
        return tempoExpiracao.getTimeInMillis();
    }

    private void atualizaLoginUsuario(String email){
        Usuario usuarioLogado = usuarioRepositorio.findByEmail( email );
        usuarioLogado.getLogin().setUltimoAcesso( Calendar.getInstance().getTime() );
        usuarioLogado.getLogin().setDataExpiracaoToken( obtemTempoExpiracaoToken() );
        usuarioRepositorio.saveAndFlush( usuarioLogado );
    }
}
