package org.coderscampus.web;

import io.jsonwebtoken.ExpiredJwtException;
import org.coderscampus.config.JwtUtil;
import org.coderscampus.dto.AuthCredentialsRequest;
import org.coderscampus.dto.AuthCredentialsResponse;
import org.coderscampus.model.User;
import org.coderscampus.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

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
}