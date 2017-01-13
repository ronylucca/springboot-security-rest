package concrete.desafio.java.services;

/**
 * Created by rony on 09/01/17.
 */
public interface SecurityService {

    String findLoggedInUsername();

    void autologin(String username, String password);
}

