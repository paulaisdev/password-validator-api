package com.passwordvalidator.api.filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingFilter implements Filter {

    private final Map<String, RateLimiter> rateLimiters = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIp = httpRequest.getRemoteAddr();
        RateLimiter rateLimiter = rateLimiters.computeIfAbsent(clientIp, ip -> new RateLimiter(100, Duration.ofMinutes(1)));

        if (rateLimiter.isAllowed()) {
            chain.doFilter(request, response);
        } else {
            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResponse.getWriter().write("Too Many Requests");
        }
    }

    static class RateLimiter {
        private final long maxRequests;
        private final long windowSizeMillis;
        private long requests;
        private long windowStart;

        public RateLimiter(long maxRequests, Duration windowSize) {
            this.maxRequests = maxRequests;
            this.windowSizeMillis = windowSize.toMillis();
            this.requests = 0;
            this.windowStart = System.currentTimeMillis();
        }

        synchronized boolean isAllowed() {
            long now = System.currentTimeMillis();
            if (now - windowStart > windowSizeMillis) {
                windowStart = now;
                requests = 0;
            }
            if (requests < maxRequests) {
                requests++;
                return true;
            }
            return false;
        }
    }
}
