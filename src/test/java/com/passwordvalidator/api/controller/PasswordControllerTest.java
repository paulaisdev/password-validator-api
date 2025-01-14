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
    void testValidatePasswordEndpoint_ValidPassword() throws Exception {
        when(cachedPasswordValidatorService.isValid("AbTp9!fok")).thenReturn(true);

        mockMvc.perform(post("/api/password/validate")
                        .content("AbTp9!fok")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(passwordMetrics, times(1)).increment(); // Verifica se a métrica foi incrementada
        verify(cachedPasswordValidatorService, times(1)).isValid("AbTp9!fok");
    }

    @Test
    @WithMockUser
    void testValidatePasswordEndpoint_InvalidPassword() throws Exception {
        when(cachedPasswordValidatorService.isValid("abc")).thenReturn(false);

        mockMvc.perform(post("/api/password/validate")
                        .content("abc")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        verify(passwordMetrics, times(1)).increment();
        verify(cachedPasswordValidatorService, times(1)).isValid("abc");
    }

//    @Test
//    @WithMockUser
//    void testValidatePasswordEndpoint_NullPassword() throws Exception {
//        mockMvc.perform(post("/api/password/validate")
//                        .content("")
//                        .contentType(MediaType.TEXT_PLAIN))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("false"));
//
//        verify(passwordMetrics, times(1)).increment(); // Métrica é chamada mesmo para erro
//        verify(cachedPasswordValidatorService, never()).isValid(anyString()); // Serviço não é chamado
//    }

    @Test
    @WithMockUser
    void testValidatePasswordEndpoint_LongPassword() throws Exception {
        String longPassword = "a".repeat(101); // Senha longa com mais de 100 caracteres

        mockMvc.perform(post("/api/password/validate")
                        .content(longPassword)
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("false"));

        verify(passwordMetrics, times(1)).increment();
        verify(cachedPasswordValidatorService, never()).isValid(anyString());
    }

    @Test
    @WithMockUser
    void testValidatePasswordEndpoint_InvalidCharacters() throws Exception {
        mockMvc.perform(post("/api/password/validate")
                        .content("AbTp9!fok@#€")
                        .content("<script>alert('test')</script>")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("false"));

        verify(passwordMetrics, times(1)).increment();
        verify(cachedPasswordValidatorService, never()).isValid(anyString());
    }
}
