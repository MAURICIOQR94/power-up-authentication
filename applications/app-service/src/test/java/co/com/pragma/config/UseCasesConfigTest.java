package co.com.pragma.config;

import co.com.pragma.model.security.gateways.PasswordService;
import co.com.pragma.model.security.gateways.JwtUtilService;
import co.com.pragma.model.role.gateways.RoleRepository;
import co.com.pragma.model.user.gateways.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UseCasesConfigTest {

    @Test
    void testUseCaseBeansExist() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class)) {
            String[] beanNames = context.getBeanDefinitionNames();

            boolean useCaseBeanFound = false;
            for (String beanName : beanNames) {
                if (beanName.endsWith("UseCase")) {
                    useCaseBeanFound = true;
                    break;
                }
            }

            assertTrue(useCaseBeanFound, "No beans ending with 'Use Case' were found");
        }
    }

    @Configuration
    @Import(UseCasesConfig.class)
    static class TestConfig {

        @Bean
        public UserRepository userRepository() {
            return Mockito.mock(UserRepository.class);
        }

        @Bean
        public RoleRepository  roleRepository() {
            return Mockito.mock(RoleRepository.class);
        }

        @Bean
        public PasswordService passwordService() {
            return Mockito.mock(PasswordService.class);
        }

        @Bean
        public JwtUtilService jwtUtilService() {
            return Mockito.mock(JwtUtilService.class);
        }
    }
}