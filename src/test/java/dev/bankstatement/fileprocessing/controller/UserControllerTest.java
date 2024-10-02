package dev.bankstatement.fileprocessing.controller;

import dev.bankstatement.fileprocessing.model.User;
import dev.bankstatement.fileprocessing.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Should register user successfully")
    public void shouldRegisterUserSuccessfully() throws Exception {
        //Arrange and mock as user service return result
        Mockito.when(userService.registerUser(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(new User());
        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                        .param("username", "testUser")
                        .param("password", "password123")
                        .param("role", "ROLE_USER")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));

    }

    @Test
    @DisplayName("Should return 400 when user already exists")
    public void shouldReturnBadRequestWhenUserExists() throws Exception {
        //Arrange and mock as user service return result
        doThrow(new RuntimeException(("User already exists"))).when(userService).registerUser(anyString(), anyString(), anyString());

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                        .param("username", "testUser")
                        .param("password", "password123")
                        .param("role", "ROLE_USER")
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(("User already exists")));

    }

    @Test
    @DisplayName("Should return 400 when parameters are missing")
    public void shouldReturnBadRequestWhenParamsMissing() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .param("password", "password123")
                        .param("role", "ROLE_USER")
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }
}