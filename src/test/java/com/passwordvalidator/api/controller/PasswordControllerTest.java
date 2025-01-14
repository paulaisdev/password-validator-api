package com.passwordvalidator.api.controller;

import com.passwordvalidator.api.config.TestSecurityConfig;
import com.passwordvalidator.api.service.CachedPasswordValidatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
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

    @Test
    @WithMockUser
    void testValidatePasswordEndpoint_ValidPassword() throws Exception {
        when(cachedPasswordValidatorService.isValid("AbTp9!fok")).thenReturn(true);

        mockMvc.perform(post("/api/password/validate")
                        .content("AbTp9!fok")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
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
    }

//    @Test
//    @WithMockUser
//    void testValidatePasswordEndpoint_NullPassword() throws Exception {
//        mockMvc.perform(post("/api/password/validate")
//                        .contentType(MediaType.TEXT_PLAIN))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("false"));
//    }

    @Test
    @WithMockUser
    void testValidatePasswordEndpoint_LongPassword() throws Exception {
        String longPassword = new String(new char[101]).replace('\0', 'a');
        mockMvc.perform(post("/api/password/validate")
                        .content(longPassword)
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("false"));
    }

    @Test
    @WithMockUser
    void testValidatePasswordEndpoint_InvalidCharacters() throws Exception {
        mockMvc.perform(post("/api/password/validate")
                        .content("AbTp9!fok@#€")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("false"));
    }
}
