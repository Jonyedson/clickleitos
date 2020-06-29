package br.com.clickleitos.config;

import br.com.clickleitos.security.jwt.JwtAuthenticationFilter;
import br.com.clickleitos.security.jwt.JwtAuthorizationFilter;
import br.com.clickleitos.security.jwt.JwtProvider;
import br.com.clickleitos.security.service.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final  PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private JwtProvider jwtProvider;
    @Qualifier("usuarioDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;


    private static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**",
            "/usuario/**",
            "/unidade/**"

    };

    private static final String[] PUBLIC_MATCHERS_POST = {
            "/usuario/**",
            "/unidade/**",
            "/auth/forgot/**",
    };

    @Override   
    protected void configure(HttpSecurity http)throws Exception{

        http.cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilterAfter(new JwtAuthorizationFilter(jwtProvider, (UsuarioDetailsService) userDetailsService), JwtAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated();


    }



    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedHeaders(Arrays.asList("authorization", "Origin", "X-Requested-With", "Accept","Content-type", "x-auth-token"));

        configuration.setAllowedMethods(Arrays.asList("POST","GET","PUT","DELETE","OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration );
        return source;
    }
}
