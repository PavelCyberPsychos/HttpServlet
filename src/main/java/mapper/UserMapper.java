package mapper;

import dto.UserDto;
import entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper implements Mapper<User, UserDto> {

    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserDto mapFrom(User object) {
        return UserDto.builder()
                .id(object.getId())
                .email(object.getEmail())
                .name(object.getName())
                .birthday(object.getBirthday())
                .image(object.getImage())
                .gender(object.getGender())
                .role(object.getRole())
                .build();
    }

    public static UserMapper getINSTANCE() {
        return INSTANCE;
    }
}
