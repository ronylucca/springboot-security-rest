package concrete.desafio.java.repositories;

import concrete.desafio.java.models.Login;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rony on 10/01/17.
 */
public interface LoginRepositorio extends JpaRepository<Login, Long> {

}
