package br.com.rafaelvieira.bytehub.infra.config;

import br.com.rafaelvieira.bytehub.api.exception.RestAccessDeniedHandler;
import br.com.rafaelvieira.bytehub.api.exception.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomHandlersConfig {

    @Bean
    public RestAccessDeniedHandler restAccessDeniedHandler() {
        return new RestAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }
}
