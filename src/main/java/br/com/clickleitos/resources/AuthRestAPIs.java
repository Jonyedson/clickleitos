package br.com.clickleitos.resources;

import br.com.clickleitos.domain.Usuario;
import br.com.clickleitos.message.request.GerenteForm;
import br.com.clickleitos.message.request.LoginForm;
import br.com.clickleitos.message.request.SignUpForm;
import br.com.clickleitos.message.response.JwtResponse;
import br.com.clickleitos.repositories.UsuarioRepository;
import br.com.clickleitos.security.jwt.JwtProvider;
import br.com.clickleitos.security.service.UsuarioDetails;
import br.com.clickleitos.services.UnidadeService;
import br.com.clickleitos.services.UsuarioService;
import br.com.clickleitos.services.exceptions.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UnidadeService unidadeService;

    public AuthRestAPIs(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getSenha()
                )
        );

        UsuarioDetails principal = (UsuarioDetails) authentication.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        String nome = principal.getNome();
        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();

        return ResponseEntity.ok(new JwtResponse(jwt, nome, authorities));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            throw new ConstraintException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Usuario obj = usuarioRepository.findByEmail(signUpRequest.getEmail());
        if(obj != null){
            return new ResponseEntity<>("Fail --> Email is already in usuario!", HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = new Usuario(null, signUpRequest.getNome(),signUpRequest.getCpf(),signUpRequest.getEmail(),signUpRequest.getSenha(), signUpRequest.getUnidade());
        usuario = usuarioService.insert(usuario);
        return  new ResponseEntity(usuario , HttpStatus.CREATED);
    }

    @PostMapping("/signup/gerente")
    public  ResponseEntity<Usuario> registerGerenteUnidade(@Valid @RequestBody GerenteForm  gerenteFormRequest, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            throw new ConstraintException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Usuario obj = usuarioRepository.findByEmail(gerenteFormRequest.getEmail());
        if(obj != null){
            return new ResponseEntity("Fail --> Email is already in usuario!", HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = new Usuario(null, gerenteFormRequest.getNome(), gerenteFormRequest.getCpf(), gerenteFormRequest.getEmail(), gerenteFormRequest.getSenha());
        usuario = usuarioService.insertGerente(usuario);
        return  new ResponseEntity<>(usuario , HttpStatus.CREATED);

    }
}

