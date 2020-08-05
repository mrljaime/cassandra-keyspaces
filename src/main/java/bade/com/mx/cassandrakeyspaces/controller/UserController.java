package bade.com.mx.cassandrakeyspaces.controller;

import bade.com.mx.cassandrakeyspaces.entity.domain.User;
import bade.com.mx.cassandrakeyspaces.repository.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * {@link UserController}
 */
@RestController
@RequestMapping("/api/1.0/users")
public class UserController extends AbstractController {

    /**
     * {@link UserRepository}
     */
    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @return {@link Map}
     */
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getUsers() {
        Iterable<User> users = userRepository.findAll();

        return ok(users);
    }

    /**
     *
     * @param user {@link User}
     * @return {@link Map}
     */
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> createUser(@RequestBody User user) {
        String id = UUID.randomUUID().toString();
        user.setId(id);

        userRepository.save(user);

        return ok(user);
    }

}
