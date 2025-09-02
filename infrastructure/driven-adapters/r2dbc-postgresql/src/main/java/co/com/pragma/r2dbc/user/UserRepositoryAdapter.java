package co.com.pragma.r2dbc.user;

import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.model.user.User;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import co.com.pragma.r2dbc.user.entity.UserEntity;
import co.com.pragma.r2dbc.user.entity.UserMapper;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class UserRepositoryAdapter extends ReactiveAdapterOperations<User, UserEntity, UUID, IUserRepository> implements UserRepository {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public UserRepositoryAdapter(IUserRepository repository, UserMapper mapper, R2dbcEntityTemplate r2dbcEntityTemplate) {
        super(repository, mapper::toData, mapper::toEntity);
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    @Override
    public Mono<User> save(User user) {
        return Mono.just(user)
                .map(u -> {
                    u.setUserId(UUID.randomUUID());
                            return u;
                })
                .map(this::toData)
                .flatMap(r2dbcEntityTemplate::insert)
                .map(this::toEntity);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(this::toEntity);
    }

}
