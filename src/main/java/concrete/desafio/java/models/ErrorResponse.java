package concrete.desafio.java.models;

/**
 * Created by rony on 12/01/17.
 */
public class ErrorResponse {
    public ErrorResponse(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    String mensagem;
}
