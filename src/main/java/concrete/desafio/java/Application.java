package concrete.desafio.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by rony on 06/01/17.
 */
@SpringBootApplication
public class Application {

    //@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class)
    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }
}
