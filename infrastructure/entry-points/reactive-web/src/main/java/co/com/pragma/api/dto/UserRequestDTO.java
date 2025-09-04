package co.com.pragma.api.dto;

import co.com.pragma.api.util.Age;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Value
@Builder
@Jacksonized
public class UserRequestDTO {

    @Size(min = 1, max = 100)
    @NotBlank(message = "First name is required")
    String firstName;

    @Size(min = 1, max = 100)
    @NotBlank(message = "Last name is required")
    String lastName;

    @Age(min = 18, max = 100, message = "Must be between 18 and 100 years old")
    @Past(message = "Birth date must be in the past")
    @NotNull(message = "Birth date is required")
    LocalDate birthDate;

    @Size(max = 200, message = "Address must not exceed 200 characters")
    String address;

    @Pattern(regexp = "^\\+?[0-9\\s\\-\\(\\)]{10,20}$", message = "Phone number format is invalid")
    String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    String password;

    @Size(min = 6, max = 60)
    @NotBlank(message = "Document number is required")
    String documentNumber;

    @NotNull(message = "Base salary is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Base salary must be greater than or equal to 0")
    @DecimalMax(value = "15000000.0", inclusive = true, message = "Base salary must not exceed 15,000,000")
    Double baseSalary;

}
