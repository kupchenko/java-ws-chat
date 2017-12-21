package com.sergialmar.wschat.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Sergi Almar
 */
@ToString
@Getter
@Setter
public class LoginEvent {

    private String username;
    private Date time;

    public LoginEvent(String username) {
        this.username = username;
        time = new Date();
    }
}
