package com.sergialmar.wschat.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table
@Entity
@Getter
@Setter
@ToString
public class User {
    private String username;
    private String password;
}
