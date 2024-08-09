package com.lastfarewells.backend.controller;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastfarewells.backend.dto.SignupDto;
import com.lastfarewells.backend.service.UserService;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc     mvc;

    @Test
    public void signUp() throws Exception {
        // given
        SignupDto signupDto = new SignupDto("abc@test.in", "pass1234", "FNAME", "LNAME", new Date(),
            false, null);

        doNothing().when(userService).registerUser(signupDto);

        // when
        mvc.perform(
                post("/api/v1/auth/signup").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .content(
                        asJsonString(signupDto)
                    ))
            // then
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
