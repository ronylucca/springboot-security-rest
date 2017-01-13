package concrete.desafio.java.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by rony on 10/01/17.
 */

@Entity
@Table(name = "login")
public class Login {

    @Id
    @Column(name = "login_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Usuario usuario;
    private String token;
    private Long dataExpiracaoToken;

    public Login(){

    }

    public Login(Date ultimoAcesso, String token){
        this.setUltimoAcesso(ultimoAcesso);
        this.setToken(token);
    }

    public Long getDataExpiracaoToken() {
        return dataExpiracaoToken;
    }

    public void setDataExpiracaoToken(Long dataExpiracaoToken) {
        this.dataExpiracaoToken = dataExpiracaoToken;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(Date ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    private Date ultimoAcesso;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
