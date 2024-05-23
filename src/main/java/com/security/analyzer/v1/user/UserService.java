package com.security.analyzer.v1.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    User registerUser(UserDTO userDTO, String password);

    void deleteUser(String login);

    Optional<UserDTO> updateUser(UserDTO userDTO);

    public Page<UserDTO> getAllPublicUsers(Pageable pageable);

}
