package concrete.desafio.java.models;

import java.text.DateFormat;

/**
 * Created by rony on 12/01/17.
 */
public class UsuarioResponse {

    Long id;
    String usuario;
    String created;
    String modified;
    String last_login;
    String token;

    public UsuarioResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.usuario = usuario.getNomeUsuario();
        this.created = DateFormat.getInstance().format(usuario.getDataCriacao());
        this.modified = DateFormat.getInstance().format(usuario.getUltimaAtualizacao());
        this.last_login = DateFormat.getInstance().format(usuario.getLogin().getUltimoAcesso());
        this.token = usuario.getLogin().getToken();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }
}
