package psslab.pss.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import psslab.pss.model.Role;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository repository;

    @Before
    public void addTestRole() {
        Role role = new Role("Test");
        entityManager.persistAndFlush(role);
    }

    @Test
    public void whenFindByName_thenReturnRole() {
        Optional<Role> found = repository.findByRoleName("Test");
        assertThat(found.get().getRoleName()).isEqualTo("Test");
    }

    @Test
    public void whenFindByName_thenReturnNull() {
        Optional<Role> found = repository.findByRoleName("NieIstniejacaRola");
        assertThat(found.isPresent()).isFalse();
    }

}
