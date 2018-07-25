package me.kupchenko.wschat.event;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Data
public class LoginEvent {

    private String username;
    private Date time;

    public LoginEvent(String username) {
        this.username = username;
        time = new Date();
    }
}
