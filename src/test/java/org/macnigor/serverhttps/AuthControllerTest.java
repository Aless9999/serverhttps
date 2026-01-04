package org.macnigor.serverhttps;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.macnigor.serverhttps.controller.AuthController;
import org.macnigor.serverhttps.model.User;
import org.macnigor.serverhttps.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    // Тест для проверки аутентификации
    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testCheckAuthAuthenticated() throws Exception {
        mockMvc.perform(get("/api/auth/check"))
                .andExpect(status().isOk())
                .andExpect(content().string("Authenticated as: testUser"));
    }

    @Test
    public void testCheckAuthAnonymous() throws Exception {
        mockMvc.perform(get("/api/auth/check"))
                .andExpect(status().isOk())
                .andExpect(content().string("Authenticated as: anonymous"));
    }

    // Тест для получения погоды
    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testGetWeatherNowSuccess() throws Exception {
        // Имитация вызова userService
        when(userService.getWeatherNow()).thenReturn("Sunny, 25°C");

        mockMvc.perform(get("/api/auth/weathernow"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sunny, 25°C"));

        verify(userService, times(1)).getWeatherNow();
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testGetWeatherNowError() throws Exception {
        // Имитация ошибки при получении погоды
        when(userService.getWeatherNow()).thenThrow(new RuntimeException("Weather service is down"));

        mockMvc.perform(get("/api/auth/weathernow"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Weather service is down"));

        verify(userService, times(1)).getWeatherNow();
    }

    // Тест для регистрации нового пользователя
    @Test
    public void testRegisterSuccess() throws Exception {
        // Создание объекта запроса
        RegisterRequest request = new RegisterRequest("newUser", "password123");

        // Имитация вызова userService
        when(userService.register("newUser", "password123")).thenReturn(Optional.of(new User("newUser", "apiKey")));

        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content("{\"username\":\"newUser\",\"password\":\"password123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.apiKey").value("apiKey"))
                .andExpect(jsonPath("$.error").value((String) null));

        verify(userService, times(1)).register("newUser", "password123");
    }

    @Test
    public void testRegisterUserAlreadyExists() throws Exception {
        // Создание объекта запроса
        RegisterRequest request = new RegisterRequest("existingUser", "password123");

        // Имитация того, что пользователь уже существует
        when(userService.register("existingUser", "password123")).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/auth/register")
                        .contentType("application/json")
                        .content("{\"username\":\"existingUser\",\"password\":\"password123\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.error").value("User already exists"));

        verify(userService, times(1)).register("existingUser", "password123");
    }
}

