package co.com.pragma.r2dbc.adapter;

import co.com.pragma.model.role.Role;
import co.com.pragma.model.role.gateways.RoleRepository;
import co.com.pragma.r2dbc.entity.RoleEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import co.com.pragma.r2dbc.mapper.RoleMapper;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class RolRepositoryAdapter extends ReactiveAdapterOperations<Role, RoleEntity, Long, IRoleRepository> implements RoleRepository {

    public RolRepositoryAdapter(IRoleRepository repository, RoleMapper mapper, R2dbcEntityTemplate r2dbcEntityTemplate) {
        super(repository, mapper::toData, mapper::toEntity);
    }

    @Override
    public Mono<Role> findByName(String name) {
        return repository.findByName(name)
                .map(this::toEntity);
    }

    @Override
    public Mono<Role> findById(Long id) {
        return repository.findById(id)
                .map(this::toEntity);
    }
}
