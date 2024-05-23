package com.security.analyzer.v1.user;

import com.security.analyzer.v1.Authentication.AuthenticationService;
import com.security.analyzer.v1.Authentication.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;

    private final UserService userService;
    private final UserRepository userRepository;


    @PostMapping("/user/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) throws Exception {
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    /**
     * {@code PUT /admin/users} : Updates an existing User.
     *
     * @param userDTO the user to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated user.
//     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already in use.
//     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already in use.
     */

    @PutMapping("/users/{login}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable(name = "login", required = false)  String login,@RequestBody UserDTO userDTO
    ) {
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.orElseThrow().getId().equals(userDTO.getId()))) {
//            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.orElseThrow().getId().equals(userDTO.getId()))) {
//            throw new LoginAlreadyUsedException();
        }
        return ResponseEntity.ok(userService.updateUser(userDTO).get());
    }



    /**
     * {@code GET /admin/users} : get all users with all the details - calling this are only allowed for the administrators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        final Page<UserDTO> page = userService.getAllPublicUsers(pageable);
        return ResponseEntity.ok(page.stream().toList());
    }



    /**
     * {@code DELETE /admin/users/:login} : delete the "login" User.
     *
     * @param login the login of the user to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/users/{login}")
    public ResponseEntity<Void> deleteUser(@PathVariable("login") String login) {
        System.out.println("REST request to delete User: {}"+ login);
        userService.deleteUser(login);
        return ResponseEntity.noContent().build();
    }

}
