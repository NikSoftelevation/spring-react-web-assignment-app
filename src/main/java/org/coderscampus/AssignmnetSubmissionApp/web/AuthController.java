package org.coderscampus.AssignmnetSubmissionApp.web;

import io.jsonwebtoken.ExpiredJwtException;
import org.coderscampus.AssignmnetSubmissionApp.config.JwtUtil;
import org.coderscampus.AssignmnetSubmissionApp.dto.AuthCredentialsRequest;
import org.coderscampus.AssignmnetSubmissionApp.dto.AuthCredentialsResponse;
import org.coderscampus.AssignmnetSubmissionApp.model.User;
import org.coderscampus.AssignmnetSubmissionApp.service.impl.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8082", "https://assignments.coderscampus.com"}, allowCredentials = "true")
public class AuthController {

    private Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Value("${cookies.domain}")
    private String domain;

    @PostMapping("/login")
    public ResponseEntity<AuthCredentialsResponse> login(@RequestBody AuthCredentialsRequest request) throws Exception {
        try {
            authenticate(request.getUsername(), request.getPassword());
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new Exception("USER NOT FOUND");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthCredentialsResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        } catch (DisabledException e) {
            throw new Exception("USER IS DISABLED" + e.getMessage());

        } catch (BadCredentialsException e) {
            throw new Exception("INVALID CREDENTIALS" + e.getMessage());
        }
    }

    //localhost:8082/api/auth/validate?token=blablablabla
    @GetMapping("/validate")
    public ResponseEntity validateToken(@RequestParam String token, @AuthenticationPrincipal User user) {

        try {
            Boolean isValidToken = jwtUtil.validateToken(token, user);
            return ResponseEntity.ok(isValidToken);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .domain(domain)
                .path("/")
                .maxAge(0)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString()).body("ok");
    }
}