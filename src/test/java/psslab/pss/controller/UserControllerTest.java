package psslab.pss.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import psslab.pss.model.User;
import psslab.pss.service.RoleService;
import psslab.pss.service.UserService;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    private static final ObjectMapper mapper = new ObjectMapper();

    private static <T> String toJson(T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void whenPostUser_thenCreateUser() throws Exception {
        User user1 = new User();
        user1.setTestData();
        given(userService.addUser(Mockito.any())).willReturn(user1);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(user1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("name")));
        verify(userService, VerificationModeFactory.times(1)).addUser(Mockito.any());
        reset(userService);
    }

    @Test
    public void whenGetAllUsers_thenReturnAllUsers() throws Exception {
        User user1 = new User();
        user1.setTestData();
        User user2 = new User();
        user2.setTestData();
        user2.setName("name2");
        given(userService.getAll()).willReturn(Arrays.asList(user1, user2));

        mvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(user1.getName())))
                .andExpect(jsonPath("$[1].name", is(user2.getName())));
        verify(userService, VerificationModeFactory.times(1)).getAll();
        reset(userService);
    }

    @Test
    public void whenPutUserIdAndPassword_thenChangePassword() throws Exception {
        User user1 = new User();
        user1.setTestData();
        user1.setUserId(0L);
        given(userService.changePassword(0L, "password")).willReturn(user1);


        mvc.perform(put("/user?userId=0&password=password")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password", is("password")));
        verify(userService, VerificationModeFactory.times(1)).changePassword(0L, "password");
        reset(userService);
    }

    @Test
    public void whenDeleteUserById_thenReturnTrue() throws Exception {
        given(userService.deleteUserById(Mockito.anyLong())).willReturn(true);

        mvc.perform(delete("/user?userId=0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
        verify(userService, VerificationModeFactory.times(1)).deleteUserById(0L);
        reset(userService);
    }

    @Test
    public void whenGetAllUsersByRole_thenReturnAllUsers() throws Exception {
        User user1 = new User();
        user1.setTestData();
        User user2 = new User();
        user2.setTestData();
        user2.setName("name2");
        given(roleService.getAllUsers(Mockito.any())).willReturn(Arrays.asList(user1, user2));

        mvc.perform(get("/user/role?roleName=roleName")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(user1.getName())))
                .andExpect(jsonPath("$[1].name", is(user2.getName())));
        verify(roleService, VerificationModeFactory.times(1)).getAllUsers(Mockito.any());
        reset(roleService);
    }
}
