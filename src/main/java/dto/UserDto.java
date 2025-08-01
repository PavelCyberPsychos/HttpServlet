package dto;

import entity.Gender;
import entity.Role;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserDto {
    Integer id;
    String email;
    String name;
    LocalDate birthday;
    String image;
    Role role;
    Gender gender;
}
