package fr.group.mspr_ar_ws.security.service;

import fr.group.mspr_ar_ws.repository.UserRepository;
import fr.group.mspr_ar_ws.models.User;
import fr.group.mspr_ar_ws.security.beans.EmailDetails;
import fr.group.mspr_ar_ws.security.filter.MsprAuthFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

@Service
public class MsprUserDetailServiceImpl implements UserDetailsService, UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(MsprUserDetailServiceImpl.class);

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return MsprUserDetailsImpl.build(user);
    }
    @Override
    @Transactional
    public boolean doesUserExists(String token) {
        return userRepository.existsByToken(token);
    }

    @Transactional
    @Override
    public User saveAndSendMail(User user) {
        User userResult = null;
        try {
            userResult = userRepository.save(user);
        }catch (IllegalArgumentException e){
            logger.warn("an error occurred when saving user");
            return null;
        }finally {
            try {
                emailService.sendMailWithAttachment(getEmailDetail(userResult));
            } catch (MessagingException e) {
                logger.warn("Successful registration but Email not sent "+e.getMessage());
            }
        }
        return userResult ;
    }
    private EmailDetails getEmailDetail(User user) {
        EmailDetails e = new EmailDetails();
        e.setSubject("SIGNUP");
        e.setRecipient(user.getEmail());
        e.setMsgBody("successfully inscription");
        e.setAttachment(user.getToken());
        return e;
    }
}
