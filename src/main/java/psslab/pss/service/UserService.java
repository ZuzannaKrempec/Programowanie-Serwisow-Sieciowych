package psslab.pss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import psslab.pss.model.Delegation;
import psslab.pss.model.User;
import psslab.pss.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    User getUser(long userId) {
        Optional<User> user = repository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new NullPointerException("Nie znaleziono szukanego użytkownika");
        }
    }
    public User addUser(User user) {
        if (user != null && user.valid()) {
            return repository.save(user);
        } else {
            throw new NullPointerException("Nie podano danych o użytkowniku");
        }
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User changePassword(long userId, String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            throw new NullPointerException("Nie podano danych do edycji");
        }
        User user = getUser(userId);
        user.setPassword(newPassword);
        return repository.save(user);
    }

    public boolean deleteUserById(long userId) {
        User user = getUser(userId);
        repository.delete(user);
        return true;
    }

    User addDelegation(long userId, Delegation delegation) {
        User user = getUser(userId);
        if (user.getDelegations() == null) {
            user.setDelegations(new ArrayList<>());
        }
        user.getDelegations().add(delegation);
        return repository.save(user);
    }

    void removeDelegation(long userId, Delegation delegation) {
        User user = getUser(userId);
        if (user.getDelegations() != null) {
            user.getDelegations().remove(delegation);
        }
        repository.save(user);
    }
}
