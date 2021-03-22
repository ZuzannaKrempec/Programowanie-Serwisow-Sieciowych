package psslab.pss.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import psslab.pss.model.Delegation;
import psslab.pss.model.User;
import psslab.pss.service.DelegationService;

import java.util.List;

@RestController
@RequestMapping("/delegation")
@Api(value = "Delegation Controller", tags = {"PSS"})
public class DelegationController {

    private final DelegationService service;

    @Autowired
    public DelegationController(DelegationService service) {
        this.service = service;
    }

    @PostMapping
    public User registerUser(@RequestParam(name = "userId") long userid,
                             @RequestBody Delegation delegation) {
        return service.addDelegation(userid, delegation);
    }

    @DeleteMapping
    public boolean removeDelegation(@RequestParam(name = "userId") long userId,
                                    @RequestParam(name = "delegationId") long delegationId) {
        return service.removeDelegation(userId, delegationId);
    }

    @PutMapping
    public void changeDelegation(@RequestParam(name = "userId") long delegationId,
                                 @RequestBody Delegation delegation) {
        service.changeDelegation(delegationId, delegation);
    }

    @GetMapping
    public List<Delegation> getAllDelegations() {
        return service.getAll();
    }

    @GetMapping("date-start")
    public List<Delegation> getAllByOrderByDateTimeStart() {
        return service.getAllByOrderByDateTimeStart();
    }

    @GetMapping("user")
    public List<Delegation> getAllByOrderByDateTimeStart(@RequestParam(name = "userId") long userId) {
        return service.getAllByOrderByDateTimeStart(userId);
    }

}
