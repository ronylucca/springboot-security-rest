package concrete.desafio.java.controllers;

import concrete.desafio.java.models.*;
import concrete.desafio.java.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.validation.Valid;

/**
 * Created by rony on 06/01/17.
 */
@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<?> efetuaLogin(@RequestBody @Valid final LoginRequest login) {
        ErrorResponse errors = null;
        try {
            Usuario usuario = usuarioService.validaCredenciais(login.getEmail(), login.getPassword());
            return new ResponseEntity<UsuarioResponse>(new UsuarioResponse(usuario), HttpStatus.OK);

        } catch (BadCredentialsException bce){
            return credenciaisInvalidas(errors);
        }
        catch(EntityNotFoundException enfe){
            return emailNaoEncontrado(errors);
        }
        catch (Exception e){
            return erroGenerico(errors);
        }
    }

    @RequestMapping(value = "/usuario/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> obtemPerfilUsuario(@PathVariable("id") String id) {
        ErrorResponse errors = null;
        try {
            Usuario usuario = usuarioService.obtemUsuarioPorId(id);
            return new ResponseEntity<UsuarioResponse>(new UsuarioResponse(usuario), HttpStatus.OK);
        }catch(EntityNotFoundException enfe){
            return entidadeNaoEncontrada( errors );
        }
        catch (BadCredentialsException bce){
            return tokenInvalido(errors);
        }
        catch (Exception e){
            return erroGenerico( errors );
        }
    }

    @RequestMapping(value = "/usuario/adicionar", method = RequestMethod.POST)
    public ResponseEntity<?> adicionar(@RequestBody @Valid final UsuarioRequest usuario) {
        ErrorResponse errors = null;
        try {
            Usuario usuarioCadastrado = usuarioService.adicionaUsuario( new Usuario(usuario));
                return new ResponseEntity<UsuarioResponse>( new UsuarioResponse(usuarioCadastrado), HttpStatus.OK);
        }
        catch(EntityExistsException eee){
            return emailExistente( errors );
        }
        catch(PersistenceException pe){
            return entidadeNaoEncontrada( errors );
        }
        catch (BadCredentialsException bce){
            return tokenInvalido(errors);
        }
        catch (Exception e){
            return erroGenerico(errors);
        }
    }

    @RequestMapping("/acessoRestrito")
    @ResponseBody
    String acessoNegado() {
        return "Acesso restrito. Favor realizar o login.";
    }

    public ResponseEntity<?> entidadeNaoEncontrada( ErrorResponse errors ){
        errors = new ErrorResponse("Usuário não encontrado.");
        return new ResponseEntity<ErrorResponse>( errors, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> tokenInvalido( ErrorResponse errors ){
        errors = new ErrorResponse("Não autorizado.");
        return new ResponseEntity<ErrorResponse>( errors, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> erroGenerico( ErrorResponse errors ){
        errors = new ErrorResponse("Ocorreu um erro durante o processo.");
        return new ResponseEntity<ErrorResponse>( errors, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> credenciaisInvalidas( ErrorResponse errors ){
        errors = new ErrorResponse("Usuário e/ou senha inválidos.");
        return new ResponseEntity<ErrorResponse>( errors, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> emailNaoEncontrado( ErrorResponse errors ){
        errors = new ErrorResponse("Usuário e/ou senha inválidos.");
        return new ResponseEntity<ErrorResponse>( errors, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> emailExistente( ErrorResponse errors ){
        errors = new ErrorResponse("E-mail já existente.");
        return new ResponseEntity<ErrorResponse>( errors, HttpStatus.BAD_REQUEST);
    }

}
