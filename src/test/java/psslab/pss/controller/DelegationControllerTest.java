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
import psslab.pss.model.Delegation;
import psslab.pss.model.User;
import psslab.pss.service.DelegationService;

import java.time.LocalDateTime;
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
@WebMvcTest(value = DelegationController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class DelegationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DelegationService delegationService;

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
    public void whenPostRegisterUser_thenAddDelegation() throws Exception {
        Delegation delegation = new Delegation();
        delegation.setTestData();
        User user = new User();
        user.setTestData();
        user.getDelegations().add(delegation);
        given(delegationService.addDelegation(0L, delegation)).willReturn(user);

        mvc.perform(post("/delegation?userId=0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.delegations[0]", is(toJson(delegation))));
        verify(delegationService, VerificationModeFactory.times(1)).addDelegation(Mockito.anyLong(), Mockito.any());
        reset(delegationService);
    }

    @Test
    public void whenDeleteDelegation_thenReturnTrue() throws Exception {
        given(delegationService.removeDelegation(Mockito.anyLong(), Mockito.anyLong())).willReturn(true);

        mvc.perform(delete("/delegation?userId=0&delegationId=0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
        verify(delegationService, VerificationModeFactory.times(1)).removeDelegation(0L, 0L);
        reset(delegationService);
    }

    // todo
    // change

    @Test
    public void whenGetAllDelegations_thenReturnAllDelegations() throws Exception {
        Delegation delegation1 = new Delegation();
        delegation1.setTestData();
        Delegation delegation2 = new Delegation();
        delegation2.setTestData();
        delegation2.setDelegationId(1L);
        given(delegationService.getAll()).willReturn(Arrays.asList(delegation1, delegation2));

        mvc.perform(get("/delegation")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].delegationId", is(0)))
                .andExpect(jsonPath("$[1].delegationId", is(1)));
        verify(delegationService, VerificationModeFactory.times(1)).getAll();
        reset(delegationService);
    }

    @Test
    public void whenGetAllByOrderByDateTimeStart_thenReturnAllByOrderByDateTimeStart() throws Exception {
        Delegation delegation1 = new Delegation();
        delegation1.setTestData();
        Delegation delegation2 = new Delegation();
        delegation2.setTestData();
        delegation2.setDelegationId(1L);
        delegation1.setDateTimeStart(LocalDateTime.now());
        given(delegationService.getAllByOrderByDateTimeStart()).willReturn(Arrays.asList(delegation2, delegation1));

        mvc.perform(get("/delegation/date-start")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].delegationId", is(1)))
                .andExpect(jsonPath("$[1].delegationId", is(0)));
        verify(delegationService, VerificationModeFactory.times(1)).getAllByOrderByDateTimeStart();
        reset(delegationService);
    }
}
