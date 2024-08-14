package rs.raf.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.requests.LoginRequest;
import rs.raf.demo.responses.LoginResponse;
import rs.raf.demo.services.UserService;
import rs.raf.demo.utils.JwtUtil;

import java.util.Collection;
//import rs.edu.raf.spring_project.model.AuthReq;
//import rs.edu.raf.spring_project.model.AuthRes;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){

        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
        //UserDetails userDetails = userService.loadUserByEmail(loginRequest.getUsername());
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));



        } catch (Exception   e){
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }
        //return ResponseEntity;
        return ResponseEntity.ok(new LoginResponse(jwtUtil.generateToken(loginRequest.getUsername(), authorities)));
    }

}
