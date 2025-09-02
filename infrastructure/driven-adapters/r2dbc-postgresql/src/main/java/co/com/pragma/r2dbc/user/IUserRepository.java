package co.com.pragma.r2dbc.user;

import co.com.pragma.r2dbc.user.entity.UserEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IUserRepository extends ReactiveCrudRepository<UserEntity, UUID>, ReactiveQueryByExampleExecutor<UserEntity> {

    public Mono<UserEntity> findByEmail(String email);

}
