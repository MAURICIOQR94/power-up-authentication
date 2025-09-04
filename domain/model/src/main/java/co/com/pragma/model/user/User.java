package co.com.pragma.model.user;

import co.com.pragma.model.role.Role;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class User {

    private UUID userId;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String email;
    private String password;
    private String documentNumber;
    private LocalDate birthDate;
    private BigDecimal baseSalary;
    private Role role;

}
