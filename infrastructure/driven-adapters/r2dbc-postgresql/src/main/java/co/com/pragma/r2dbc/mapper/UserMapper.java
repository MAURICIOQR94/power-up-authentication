package co.com.pragma.r2dbc.mapper;

import co.com.pragma.model.role.Role;
import co.com.pragma.model.user.User;
import co.com.pragma.r2dbc.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", source = "idRole")
    User toEntity(UserEntity entity);

    @Mapping(target = "idRole", source = "role")
    UserEntity toData(User user);

    default Role mapRole(Long id) {
        return id != null ? Role.builder().id(id).build() : null;
    }

    default Long mapLoanType(Role role) {
        return role.getId();
    }
}
