package psslab.pss.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import psslab.pss.model.User;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class RoleServiceTest {

    @Mock
    private RoleService roleService;

    @Before
    public void setUp() {
        User user = new User();
        user.setTestData();

        Mockito.when(roleService.getAllUsers("test")).thenReturn(Collections.singletonList(user));
    }

    @Test
    public void whenFindByName_thenReturnUser() {
        String name = "test";
        List<User> found = roleService.getAllUsers(name);
        User user = new User();
        user.setTestData();

        assertThat(found.get(0).getUserId()).isEqualTo(user.getUserId());
    }

    @Test
    public void whenFindByName_thenReturnNull() {
        String name = "test1";
        List<User> found = roleService.getAllUsers(name);

        assertThat(found.size()).isEqualTo(0);
    }

}
