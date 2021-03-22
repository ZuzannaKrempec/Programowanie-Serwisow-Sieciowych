package psslab.pss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import psslab.pss.model.Delegation;
import psslab.pss.model.User;
import psslab.pss.repository.DelegationRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class DelegationService {

    private final DelegationRepository repository;
    private final UserService userService;

    @Autowired
    public DelegationService(DelegationRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    Delegation getDelegation(long delegationId) {
        Optional<Delegation> delegation = repository.findById(delegationId);
        if (delegation.isPresent()) {
            return delegation.get();
        } else {
            throw new NullPointerException("Nie znaleziono szukanego użytkownika");
        }
    }

    public User addDelegation(long userId, Delegation delegation) {
        if (delegation != null && delegation.valid()) {
            delegation = repository.save(delegation);
        } else {
            throw new NullPointerException("Nie podano danych o użytkowniku");
        }
        return userService.addDelegation(userId, delegation);
    }

    public boolean removeDelegation(long userId, long delegationId) {
        Delegation delegation = getDelegation(delegationId);
        repository.delete(delegation);
        userService.removeDelegation(userId, delegation);
        return true;
    }

    public void changeDelegation(long delegationId, Delegation delegation) {
        if (delegation != null && delegation.valid()) {
            delegation.setDelegationId(delegationId);
            repository.save(delegation);
        } else {
            throw new NullPointerException("Nie podano danych do edycji");
        }
    }

    public List<Delegation> getAll() {
        return repository.findAll();
    }

    public List<Delegation> getAllByOrderByDateTimeStart() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "name"));
    }

    public List<Delegation> getAllByOrderByDateTimeStart(long userId) {
        List<Delegation> delegations = userService.getUser(userId).getDelegations();
        delegations.sort(Comparator.comparing(Delegation::getDateTimeStart).reversed());
        return delegations;
    }
}

