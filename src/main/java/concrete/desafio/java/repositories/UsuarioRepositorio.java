package concrete.desafio.java.repositories;

import concrete.desafio.java.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by rony on 09/01/17.
 */
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {


    Usuario findByNomeUsuarioAndPassword
        (
            @Param("nomeUsuario") String nomeUsuario,
            @Param("password") String password
        );

    Usuario findByNomeUsuario(
            @Param("nomeUsuario") String nomeUsuario
    );

    Usuario findByEmail(
            @Param("email") String email
    );
}

