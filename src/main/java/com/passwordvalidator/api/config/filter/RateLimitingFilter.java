package com.passwordvalidator.api.config.filter;

import org.springframework.beans.factory.annotation.Value;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

@Component
public class RateLimitingFilter implements Filter {

    private final Map<String, RateLimiter> rateLimiters = new ConcurrentHashMap<>();

    @Value("${ratelimiting.max-requests:100}")
    public long maxRequests = 100;

    @Value("${ratelimiting.window-size:60000}")
    public long windowSizeMillis = 60000;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIp = httpRequest.getRemoteAddr();
        RateLimiter rateLimiter = rateLimiters.computeIfAbsent(clientIp, ip -> new RateLimiter(maxRequests, windowSizeMillis));

        if (rateLimiter.isAllowed()) {
            chain.doFilter(request, response);
        } else {
            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResponse.getWriter().write("Too Many Requests");
        }
    }

    static class RateLimiter {
        private final Semaphore semaphore;
        private final long windowSizeMillis;
        private long lastReset;

        public RateLimiter(long maxRequests, long windowSizeMillis) {
            this.semaphore = new Semaphore((int) maxRequests);
            this.windowSizeMillis = windowSizeMillis;
            this.lastReset = System.currentTimeMillis();
        }

        synchronized boolean isAllowed() {
            long now = System.currentTimeMillis();
            if (now - lastReset > windowSizeMillis) {
                semaphore.drainPermits();
                semaphore.release(semaphore.getQueueLength());
                lastReset = now;
            }
            return semaphore.tryAcquire();
        }
    }
}
