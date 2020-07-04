package br.com.clickleitos.resources;

import br.com.clickleitos.domain.Usuario;
import br.com.clickleitos.message.request.LoginForm;
import br.com.clickleitos.message.request.SignUpForm;
import br.com.clickleitos.message.response.JwtResponse;
import br.com.clickleitos.repositories.UsuarioRepository;
import br.com.clickleitos.security.jwt.JwtProvider;
import br.com.clickleitos.services.UnidadeService;
import br.com.clickleitos.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
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

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest){
/*
        if(usuarioRepository.findByEmail(signUpRequest.getEmail())){
            return new ResponseEntity<String>("Fail --> Email is already in usuario!", HttpStatus.BAD_REQUEST);
        }*/
        Usuario usuario = new Usuario(null, signUpRequest.getNome(),signUpRequest.getCpf(),signUpRequest.getEmail(),passwordEncoder.encode(signUpRequest.getSenha()), signUpRequest.getUnidade());

        return ResponseEntity.ok().body("");
    }
}

