package net.example.ftpimitation;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final List<UserEntity> users;

    public UserRepository() {
        users = new ArrayList<>();
        users.add(UserEntity.builder().id(1L).username("user1").cleanPassword("pass1").build());
        users.add(UserEntity.builder().id(2L).username("user2").cleanPassword("pass2").build());
    }

    public UserEntity findByUsername(String usernameFromClient) {
        UserEntity userEntity = null;
        for (UserEntity user : users) {
            if (user.getUsername().equals(usernameFromClient)) {
                userEntity = user;
            }
        }
        return userEntity;
    }
}
