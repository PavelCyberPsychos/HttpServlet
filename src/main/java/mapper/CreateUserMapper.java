package mapper;

import dto.CreateUserDto;
import entity.Gender;
import entity.Role;
import entity.User;
import util.LocalDateFormatter;

public class CreateUserMapper implements Mapper<CreateUserDto, User> {
    private static final String IMAGE_FOLDER = "users/";
    private final static CreateUserMapper INSTANCE = new CreateUserMapper();

    public static CreateUserMapper getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public User mapFrom(CreateUserDto object) {
        return User.builder()
                .name(object.getName())
                .email(object.getEmail())
                .password(object.getPassword())
                .gender(Gender.valueOf(object.getGender()))
                .image(IMAGE_FOLDER + object.getImage().getSubmittedFileName())
                .role(Role.valueOf(object.getRole()))
                .birthday(LocalDateFormatter.format(object.getBirthday()))
                .build();
    }
}
