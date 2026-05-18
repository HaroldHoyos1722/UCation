package co.com.polijic.ucation.configuration;

import co.com.polijic.ucation.domain.repositories.SesionRepositoryPort;
import co.com.polijic.ucation.filters.JwtFilter;
import co.com.polijic.ucation.usecases.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class JwtConfiguration {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiracion}")
    private long expiracionMs;

    @Value("${urlexcluirfilter}")
    private List<String> urlsExcluidas;

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(secret, expiracionMs);
    }

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter(JwtUtil jwtUtil, SesionRepositoryPort sesionRepositoryPort) {
        FilterRegistrationBean<JwtFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new JwtFilter(jwtUtil, urlsExcluidas, sesionRepositoryPort));
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }
}
