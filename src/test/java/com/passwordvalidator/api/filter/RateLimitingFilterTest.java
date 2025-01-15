package com.passwordvalidator.api.filter;

import com.passwordvalidator.api.config.filter.RateLimitingFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

class RateLimitingFilterTest {

    private RateLimitingFilter rateLimitingFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    @BeforeEach
    void setUp() {
        rateLimitingFilter = new RateLimitingFilter();

        rateLimitingFilter.maxRequests = 100;
        rateLimitingFilter.windowSizeMillis = 60000;

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
    }

    @Test
    void testRequestAllowed() throws Exception {
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        rateLimitingFilter.doFilter(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        verify(response, never()).setStatus(429);
    }
//
//    @Test
//    void testRequestBlocked() throws Exception {
//        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
//
//        for (int i = 0; i < 100; i++) {
//            rateLimitingFilter.doFilter(request, response, chain);
//        }
//
//        rateLimitingFilter.doFilter(request, response, chain);
//
//        verify(response, times(1)).setStatus(429);
//        verify(chain, times(100)).doFilter(request, response);
//    }
}
