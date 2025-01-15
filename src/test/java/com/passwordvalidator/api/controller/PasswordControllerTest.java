package com.passwordvalidator.api.controller;

import com.passwordvalidator.api.config.TestSecurityConfig;
import com.passwordvalidator.api.metrics.PasswordMetrics;
import com.passwordvalidator.api.service.CachedPasswordValidatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PasswordController.class)
@Import(TestSecurityConfig.class)
class PasswordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CachedPasswordValidatorService cachedPasswordValidatorService;

    @MockBean
    private PasswordMetrics passwordMetrics;

    @Test
    @WithMockUser
    void testValidPassword() throws Exception {
        when(cachedPasswordValidatorService.isValid("AbTp9!fok")).thenReturn(true);

        mockMvc.perform(post("/api/password/validate")
                        .content("AbTp9!fok")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(passwordMetrics, times(1)).increment(); // Verifica se a métrica foi incrementada
        verify(cachedPasswordValidatorService, times(1)).isValid("AbTp9!fok");
    }

    @Test
    @WithMockUser
    void testInvalidPassword() throws Exception {
        when(cachedPasswordValidatorService.isValid("abc")).thenReturn(false);

        mockMvc.perform(post("/api/password/validate")
                        .content("abc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        verify(passwordMetrics, times(1)).increment();
        verify(cachedPasswordValidatorService, times(1)).isValid("abc");
    }

    @Test
    void testInvalidContentType() throws Exception {
        mockMvc.perform(post("/api/password/validate")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("AbTp9!fok"))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    @WithMockUser
    void testValidateLongPassword() throws Exception {
        String longPassword = new String(new char[101]).replace('\0', 'a'); // 101 caracteres 'a'

        mockMvc.perform(post("/api/password/validate")
                        .content(longPassword)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("false"));

        verify(passwordMetrics, times(1)).increment();
        verify(cachedPasswordValidatorService, never()).isValid(anyString());
    }

    @Test
    @WithMockUser
    void testValidateInvalidCharacters() throws Exception {
        mockMvc.perform(post("/api/password/validate")
                        .content("AbTp9!fok@#€")
                        .content("<script>alert('test')</script>")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("false"));

        verify(passwordMetrics, times(1)).increment();
        verify(cachedPasswordValidatorService, never()).isValid(anyString());
    }
}
