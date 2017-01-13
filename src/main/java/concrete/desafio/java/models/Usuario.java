package concrete.desafio.java.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


/**
 * Created by rony on 09/01/17.
 */
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @Column(name = "usuario_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String email;
    private String nomeUsuario;
    private String password;

    @OneToOne
    private Login login;
    private Date dataCriacao;
    private Date ultimaAtualizacao;

    @ManyToMany
    @JoinTable(name = "usuario_role", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public Usuario() {
    }

    public Usuario(String email, String nome, String password) {
        this.email = email;
        this.nomeUsuario = nome;
        this.password = password;
    }


    public Usuario(UsuarioRequest usuario) {
        this.nomeUsuario = usuario.getName();
        this.email = usuario.getEmail();
        this.password = usuario.getPassword();
    }

    public Long getId() {
        return id;
    }

    public Usuario setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public Usuario setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Usuario setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Date getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
