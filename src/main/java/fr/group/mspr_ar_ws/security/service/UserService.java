package fr.group.mspr_ar_ws.security.service;

import fr.group.mspr_ar_ws.models.User;
import fr.group.mspr_ar_ws.security.beans.EmailDetails;

public interface UserService {
    boolean doesUserExists(String token);
    User saveAndSendMail(User user);

}
