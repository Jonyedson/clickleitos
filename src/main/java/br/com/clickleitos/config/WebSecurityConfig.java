package br.com.clickleitos.config;

import br.com.clickleitos.domain.audit.AuditorAwareImpl;
import br.com.clickleitos.security.jwt.JwtAuthEntryPoint;
import br.com.clickleitos.security.jwt.JwtAuthorizationFilter;
import br.com.clickleitos.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableSwagger2
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final  PasswordEncoder passwordEncoder;

    @Autowired
    private Environment env;

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Autowired
    private JwtProvider jwtProvider;

    @Qualifier("usuarioDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**",
            "/api/auth/**",
            "/v2/api-docs",
            "/swagger-ui.html",
            "/webjars/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",

    };

    private static final String[] PUBLIC_MATCHERS_POST = {
            "/usuario/**",
            "/unidade/**",
            "/auth/forgot/**",
            "/"
    };

    @Override   
    protected void configure(HttpSecurity http)throws Exception{

        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        http.cors().and().csrf().disable()
                //.addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilterBefore(new JwtAuthorizationFilter(jwtProvider, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
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

    @Bean
    public Docket swaggerConfiguration(){

        return  new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage("br.com"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails(){
        return new ApiInfo(
                "Address book api",
                "API Clickleito",
                "1.0",
                "Free to use",
                new Contact("CiceroJonyedson", "https://github.com/Jonyedson","cicerojonyedison@gmail.com"),
                "API License",
                "https://github.com/Jonyedson",
                Collections.emptyList()
        );
    }
}
