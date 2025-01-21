package org.example.gestorapi.security.user.mongodb;


import org.example.gestorapi.security.user.User;

public class UserMapper {

    private UserMapper() {}

    public static User mapToModel(UserDocument userDocument) {
        return new User(userDocument.getId(),
                userDocument.getUsername(),
                userDocument.getPassword(),
                userDocument.getRol(),
                userDocument.getEnabled());
    }

    public static UserDocument mapToModel(User user) {
        return new UserDocument(user.id(), user.username(), user.password(), user.rol(), user.enabled());
    }
}