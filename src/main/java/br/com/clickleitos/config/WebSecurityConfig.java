package br.com.clickleitos.config;

import br.com.clickleitos.security.JWTAuthenticationFilter;
import br.com.clickleitos.security.JWTAuthorizationFilter;
import br.com.clickleitos.security.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    private static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**",
            "/usuario/**",
            "/unidade/**"

    };

    private static final String[] PUBLIC_MATCHERS_POST = {
            "/users",
            "/auth/forgot/**",
    };

    @Override
    protected void configure(HttpSecurity httpSecurity)throws Exception{

        //entrar no banco de dados teste
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            httpSecurity.headers().frameOptions().disable();
        }
        httpSecurity.cors().and().csrf().disable();
                httpSecurity.authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
                .anyRequest().authenticated();

        httpSecurity.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtProvider));
        httpSecurity.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtProvider, userDetailsService));

        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder());
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

    @Bean
    public BCryptPasswordEncoder  bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
