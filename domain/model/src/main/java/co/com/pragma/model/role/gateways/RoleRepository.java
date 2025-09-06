package co.com.pragma.model.role.gateways;

import co.com.pragma.model.role.Role;
import reactor.core.publisher.Mono;

public interface RoleRepository {

    Mono<Role> findByName(String name);
    Mono<Role> findById(Long id);

}
