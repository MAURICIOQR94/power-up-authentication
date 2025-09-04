package co.com.pragma.model.role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Role {

    private Long id;
    private String name;
    private String description;

}
