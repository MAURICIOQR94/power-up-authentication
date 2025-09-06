package co.com.pragma.r2dbc.helper;

import co.com.pragma.r2dbc.adapter.IUserRepository;
import co.com.pragma.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;
import java.util.function.Function;

import static org.mockito.Mockito.*;

class ReactiveAdapterOperationsTest {

    private IUserRepository repository;
    private UserAdapter operations;

    private final TestUser domain = new TestUser(UUID.randomUUID(), "maria@test.com");
    private final UserEntity data = Mockito.mock(UserEntity.class);

    @BeforeEach
    void setUp() {
        repository = mock(IUserRepository.class);

        Function<TestUser, UserEntity> toDataFn = e -> data;
        Function<UserEntity, TestUser> toEntityFn = d -> domain;

        operations = new UserAdapter(repository, toDataFn, toEntityFn);
    }

    @Test
    void toData_returnsData() {
        Assertions.assertSame(data, operations.toData(domain));
    }

    @Test
    void toEntity_returnsDomain() {
        Assertions.assertSame(domain, operations.toEntity(data));
    }

    @Test
    void save_returnsMappedDomain() {
        when(repository.save(data)).thenReturn(Mono.just(data));

        StepVerifier.create(operations.save(domain))
                .expectNext(domain)
                .verifyComplete();

        verify(repository).save(data);
    }

    @Test
    void findByEmail_returnsMappedDomain() {
        when(repository.findByEmail("maria@test.com")).thenReturn(Mono.just(data));

        StepVerifier.create(operations.findByEmail("maria@test.com"))
                .expectNext(domain)
                .verifyComplete();

        verify(repository).findByEmail("maria@test.com");
    }

    static final class TestUser {
        final UUID id;
        final String email;
        TestUser(UUID id, String email) { this.id = id; this.email = email; }
    }

    static final class UserAdapter extends ReactiveAdapterOperations<TestUser, UserEntity, UUID, IUserRepository> {
        UserAdapter(IUserRepository repo, Function<TestUser, UserEntity> toData, Function<UserEntity, TestUser> toEntity) {
            super(repo, toData, toEntity);
        }
        Mono<TestUser> findByEmail(String email) {
            return repository.findByEmail(email).map(this::toEntity);
        }
    }
}
