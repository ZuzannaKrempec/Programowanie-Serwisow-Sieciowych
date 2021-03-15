package psslab.pss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psslab.pss.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
