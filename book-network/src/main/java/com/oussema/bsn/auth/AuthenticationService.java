package com.oussema.bsn.auth;

import com.oussema.bsn.email.EmailService;
import com.oussema.bsn.email.EmailTemplateName;
import com.oussema.bsn.role.RoleRepository;
import com.oussema.bsn.security.JwtService;
import com.oussema.bsn.user.Token;
import com.oussema.bsn.user.TokenRepository;
import com.oussema.bsn.user.User;
import com.oussema.bsn.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {



    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;

    public void register(RegistrationRequest request) throws MessagingException {
       var userRole = roleRepository.findByName("USER").orElseThrow(()-> new IllegalStateException("Role User was not found"));
         var user = User.builder()
                 .email(request.getEmail())
                 .firstName(request.getFirstname())
                 .lastName(request.getLastname())
                 .password(passwordEncoder.encode(request.getPassword()))
                 .accountLocked(false)
                 .enabled(false)
                 .roles(List.of(userRole))
                 .build();

        userRepository.save(user);
        sendValidationEmail(user);

    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSendActivationToken(user);
        emailService.sendEmail(user.getEmail(),
                                user.getFullName(),
                                EmailTemplateName.ACTIVATE_ACCOUNT,
                                activationUrl,
                                newToken,
                        "Account activation"
                                );
    }

    private String generateAndSendActivationToken(User user) {
        String generatedToken= generateActivationCode();
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode() {
        String characters="0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
           int randomIndex = random.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String,Object>();
        var user = (User) auth.getPrincipal();
        claims.put("fullName", user.getFullName());

        var jwtToken= jwtService.generateToken(claims,user);
        return  AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Transactional
    public void activateAccount(String token) throws MessagingException {

        Token savedToken= tokenRepository.findByToken(token)
                // todo : exception has to be handled
                .orElseThrow(()-> new IllegalStateException("Token not found"));

        if( savedToken.getExpiresAt().isBefore(LocalDateTime.now())){
            sendValidationEmail(savedToken.getUser());
            throw new IllegalStateException("Token has expired, a new one has been sent to your email");
        }
        var user = savedToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}
