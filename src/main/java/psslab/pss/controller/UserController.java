package psslab.pss.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import psslab.pss.model.User;
import psslab.pss.service.RoleService;
import psslab.pss.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@Api(value = "User Controller", tags = {"PSS"})
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping
    public User registerUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PutMapping
    public User changePassword(@RequestParam(name = "userId") long userId,
                               @RequestParam(name = "password") String newPassword) {
        return userService.changePassword(userId, newPassword);
    }

    @DeleteMapping
    public boolean deleteUserById(@RequestParam(name = "userId") long userId) {
        return userService.deleteUserById(userId);
    }

    @GetMapping("role")
    public List<User> getAllUsers(@RequestParam(name = "roleName") String roleName) {
        return roleService.getAllUsers(roleName);
    }
}
