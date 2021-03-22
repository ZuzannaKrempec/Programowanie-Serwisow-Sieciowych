package psslab.pss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psslab.pss.model.Role;
import psslab.pss.model.User;
import psslab.pss.repository.RoleRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository repository;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
        init();
    }

    private void init() {
        Role role = new Role("test");
        role = repository.save(role);
        System.out.println(role.getRoleId() +" " + role.getRoleName() + " " + role.getUsers().size());
    }

    public List<User> getAllUsers(String roleName) {
        Optional<Role> role = repository.findByRoleName(roleName);
        if (role.isPresent()) {
            return role.get().getUsers();
        } else {
            throw new NullPointerException("Nie znaleziono szukanej roli");
        }
    }
}
