package fr.group.mspr_ar_ws.security.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

// Class
public class EmailDetails {

    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}