package org.example.gestorapi.security.user;

public record User(String id,
                   String username,
                   String password,
                   String rol,
                   Boolean enabled) {}