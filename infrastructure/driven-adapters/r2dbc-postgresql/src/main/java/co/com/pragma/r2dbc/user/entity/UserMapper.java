package co.com.pragma.r2dbc.user.entity;

import co.com.pragma.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserEntity entity);
    UserEntity toData(User user);
}
