package fr.group.mspr_ar_ws.controllers;

import fr.group.mspr_ar_ws.models.*;
import fr.group.mspr_ar_ws.security.beans.EmailDetails;
import fr.group.mspr_ar_ws.security.beans.SignupResponse;
import fr.group.mspr_ar_ws.security.beans.SignupRequest;
import fr.group.mspr_ar_ws.security.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import fr.group.mspr_ar_ws.repository.RoleRepository;
import fr.group.mspr_ar_ws.repository.UserRepository;
import fr.group.mspr_ar_ws.security.MsprJwtUtils;

import javax.mail.MessagingException;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private EmailService emailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    MsprJwtUtils jwtUtils;


    public String getTokenAuthenticationUser(User user) {
        return jwtUtils.generateJwtToken(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) throws MessagingException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new SignupResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new SignupResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleEnum.ROLE_RETAILER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "web_shop":
                        Role modRole = roleRepository.findByName(RoleEnum.ROLE_WEB_SHOP)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleEnum.ROLE_RETAILER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        user.setToken(getTokenAuthenticationUser(user));
        userRepository.save(user);
        SignupResponse messageResponse = new SignupResponse("User registered successfully!");
        messageResponse.setToken(user.getToken());
        messageResponse.addExtra(user.getUsername());
        emailService.sendMailWithAttachment(getEmailDetail(user));
        return ResponseEntity.ok(messageResponse);
    }

    private EmailDetails getEmailDetail(User user) {
        EmailDetails e = new EmailDetails();
        e.setSubject("SIGNUP");
        e.setRecipient(user.getEmail());
        e.setMsgBody("successfully inscription");
        e.setAttachment(user.getToken());
        return e;
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('WEB_SHOP') or hasRole('RETAILER') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/ws")
    @PreAuthorize("hasRole('WEB_SHOP')")
    public String moderatorAccess() {
        return "WEB_SHOP Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}
