package concrete.desafio.java.producers;

import concrete.desafio.java.models.Role;
import concrete.desafio.java.models.Usuario;
import concrete.desafio.java.repositories.RoleRepositorio;
import concrete.desafio.java.services.UsuarioService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by rony on 09/01/17.
 */

@Component
public class UsuarioProducer {

    private Log logger = LogFactory.getLog(UsuarioProducer.class);

    private UsuarioService usuarioService;

    private Usuario usuarioDummyMaster;

    @Autowired
    public UsuarioProducer(UsuarioService usuarioService) {

        this.usuarioService = usuarioService;
    }
    @Autowired
    private RoleRepositorio roleRepositorio;

    @PostConstruct
    public void produceData() {
        adicionaRoles();
        adicionaUsuarioMaster();
        obtemUsuarioMaster();

    }

    private void adicionaUsuarioMaster() {
        logger.info(" Adicionando usuario master. ");
        usuarioDummyMaster = new Usuario("concrete@concrete.com.br", "concrete", "concrete");
        usuarioService.adicionaUsuario( usuarioDummyMaster );
    }

    private void obtemUsuarioMaster() {
        logger.info(" Obtendo usuario por credencial. ");
        Usuario usuario = usuarioService.obtemUsuario( usuarioDummyMaster );
        if(usuario == null) {
            logger.info(" Nenhum usuario cadastrado. ");
        } else {
            logger.info(String.format(" Usuario com id %d NomeUsuario %s e password %s encontrado:", usuario.getId(), usuario.getNomeUsuario(), usuario.getPassword()));
        }
        usuarioService.realizaLogin(usuario);
    }
    private void adicionaRoles(){
        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        roleRepositorio.save(roleAdmin);
    }

}
