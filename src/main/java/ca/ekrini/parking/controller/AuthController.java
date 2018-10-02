package ca.ekrini.parking.controller;

import ca.ekrini.parking.exception.AppException;
import ca.ekrini.parking.model.Role;
import ca.ekrini.parking.model.RoleName;
import ca.ekrini.parking.model.User;
import ca.ekrini.parking.payload.*;
import ca.ekrini.parking.repository.RoleRepository;
import ca.ekrini.parking.repository.UserRepository;
import ca.ekrini.parking.security.JwtTokenProvider;
import ca.ekrini.parking.util.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    public JavaMailSender emailSender;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @Transactional
    @PostMapping("/forgot")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotEmailRequest forgotEmailRequest) {
        String email = forgotEmailRequest.getEmail();
        Optional<User> result = userRepository.findByUsernameOrEmail("", email);
        if (!result.isPresent()) {
            return new ResponseEntity(new ApiResponse(false, "Email not found"),
                    HttpStatus.BAD_REQUEST);
        } else {
            User user = result.get();
            String newPassword = PasswordGenerator.generatePassword(6);
            user.setPassword(passwordEncoder.encode(newPassword));
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user.getEmail());
                message.setSubject("Ekrini your password");
                message.setText("Your password is: " + newPassword);
                emailSender.send(message);
                userRepository.save(user);
                return new ResponseEntity(new ApiResponse(true, "Password sent"),
                        HttpStatus.ACCEPTED);
            } catch (Error error) {
                return new ResponseEntity(new ApiResponse(false, "Password unsent"),
                        HttpStatus.BAD_REQUEST);
            }

        }
    }
}
