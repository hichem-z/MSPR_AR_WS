package fr.group.mspr_ar_ws.controllers;

import com.sun.net.httpserver.Headers;
import fr.group.mspr_ar_ws.models.*;
import fr.group.mspr_ar_ws.security.beans.EmailDetails;
import fr.group.mspr_ar_ws.security.beans.SignupResponse;
import fr.group.mspr_ar_ws.security.beans.SignupRequest;
import fr.group.mspr_ar_ws.security.service.EmailService;
import fr.group.mspr_ar_ws.security.service.UserService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.web.servlet.headers.HeadersSecurityMarker;
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
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    MsprJwtUtils jwtUtils;
    @Autowired
    UserService userService;


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
        SignupResponse signupResponse = new SignupResponse("User registered successfully!");
        if(userService.saveAndSendMail(user) != null){
            signupResponse.setToken(user.getToken());
            signupResponse.addExtra(user.getUsername());
        }else {
            signupResponse.setMessage("User registration failed!");
            signupResponse.setToken(null);
        }
        return ResponseEntity.ok(signupResponse);

    }


    @GetMapping("/retailer/login")
    @PreAuthorize("hasRole('RETAILER')")
    public ResponseEntity<?> retailerAccess(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return checkAccess(token);
    }
    @GetMapping("/webshop/login")
    @PreAuthorize("hasRole('WEB_SHOP')")
    public ResponseEntity<?> webShopAccess(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return checkAccess(token);
    }

    @GetMapping("/admin/login")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminAccess(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return checkAccess(token);
    }
    private ResponseEntity<?> checkAccess(String token) {
        if (userService.doesUserExists(token.substring(7))){
            return new ResponseEntity<>(HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
