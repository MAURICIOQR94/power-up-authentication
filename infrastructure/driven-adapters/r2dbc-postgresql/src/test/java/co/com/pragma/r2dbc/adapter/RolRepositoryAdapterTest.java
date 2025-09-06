package co.com.pragma.r2dbc.adapter;

import co.com.pragma.model.role.Role;
import co.com.pragma.r2dbc.entity.RoleEntity;
import co.com.pragma.r2dbc.mapper.RoleMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class RolRepositoryAdapterTest {

    @InjectMocks
    private RolRepositoryAdapter repositoryAdapter;

    @Mock
    private RoleMapper mapper;

    @Mock
    private IRoleRepository repository;

    private Role role;
    private RoleEntity entity;

    @BeforeEach
    void setUp() {
        role = Role.builder().id(1L).name("ADMIN").build();
        entity = RoleEntity.builder().id(1L).name("ADMIN").build();
    }

    @Test
    void findByName() {
        when(repository.findByName(role.getName())).thenReturn(Mono.just(entity));
        when(mapper.toEntity(entity)).thenReturn(role);

        Mono<Role> result = repositoryAdapter.findByName(role.getName());

        StepVerifier.create(result)
                .expectNext(role)
                .verifyComplete();
    }

    @Test
    void findById() {
        when(repository.findById(role.getId())).thenReturn(Mono.just(entity));
        when(mapper.toEntity(entity)).thenReturn(role);

        Mono<Role> result = repositoryAdapter.findById(role.getId());

        StepVerifier.create(result)
                .expectNext(role)
                .verifyComplete();
    }
}