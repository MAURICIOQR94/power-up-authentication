package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.UserRequestDTO;
import co.com.pragma.api.dto.UserResponseDTO;
import co.com.pragma.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {

    @Mapping(target = "userId", ignore = true)
    User toEntity(UserRequestDTO userRequestDTO);

    UserResponseDTO toDto(User user);
}
