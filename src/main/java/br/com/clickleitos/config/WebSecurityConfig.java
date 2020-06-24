package br.com.clickleitos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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

    @Autowired
    private Environment env;

    private final  PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Qualifier("usuarioDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;
    /*


    @Autowired
    private JWTProvider jwtProvider;



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
*/
    @Override   
    protected void configure(HttpSecurity http)throws Exception{

        //entrar no banco de dados teste
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        http
                .cors().and()
                .authorizeRequests().antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();

        /*httpSecurity.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtProvider))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtProvider, userDetailsService))
                .authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .antMatchers(PUBLIC_MATCHERS_POST).permitAll()
                .anyRequest().authenticated();

         */
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
