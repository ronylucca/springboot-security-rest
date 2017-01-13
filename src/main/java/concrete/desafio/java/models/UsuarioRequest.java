package concrete.desafio.java.models;

import java.util.List;

/**
 * Created by rony on 12/01/17.
 */
public class UsuarioRequest {

    private String name;
    private String email;
    private String password;
    private List<PhonesRequest> phones;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PhonesRequest> getPhones() {
        return phones;
    }

    public void setPhones(List<PhonesRequest> phones) {
        this.phones = phones;
    }
}
