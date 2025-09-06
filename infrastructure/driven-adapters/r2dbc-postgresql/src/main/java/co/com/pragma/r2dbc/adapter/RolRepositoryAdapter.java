package co.com.pragma.r2dbc.adapter;

import co.com.pragma.model.common.exception.BusinessException;
import co.com.pragma.model.role.Role;
import co.com.pragma.model.role.gateways.RoleRepository;
import co.com.pragma.r2dbc.entity.RoleEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import co.com.pragma.r2dbc.mapper.RoleMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static co.com.pragma.model.common.enums.BusinessExceptionMessage.ROLE_NOT_FOUND;

@Repository
public class RolRepositoryAdapter extends ReactiveAdapterOperations<Role, RoleEntity, Long, IRoleRepository> implements RoleRepository {

    public RolRepositoryAdapter(IRoleRepository repository, RoleMapper mapper) {
        super(repository, mapper::toData, mapper::toEntity);
    }

    @Override
    public Mono<Role> findByName(String name) {
        return repository.findByName(name)
                .switchIfEmpty(Mono.error(new BusinessException(ROLE_NOT_FOUND)))
                .map(this::toEntity);
    }

    @Override
    public Mono<Role> findById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new BusinessException(ROLE_NOT_FOUND)))
                .map(this::toEntity);
    }
}
