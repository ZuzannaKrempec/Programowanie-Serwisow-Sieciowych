package psslab.pss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psslab.pss.model.Delegation;

@Repository
public interface DelegationRepository extends JpaRepository<Delegation, Long> {

}
