package concrete.desafio.java.repositories;

import concrete.desafio.java.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rony on 09/01/17.
 */
public interface RoleRepositorio extends JpaRepository<Role, Long> {
}