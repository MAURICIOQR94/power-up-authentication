package co.com.pragma.r2dbc.adapter;

import co.com.pragma.r2dbc.entity.RoleEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IRoleRepository  extends ReactiveCrudRepository<RoleEntity, Long>, ReactiveQueryByExampleExecutor<RoleEntity> {

    public Mono<RoleEntity> findByName(String name);

}
