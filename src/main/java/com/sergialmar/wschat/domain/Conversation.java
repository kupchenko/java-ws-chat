package com.sergialmar.wschat.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Conversation {
    private List<User> participants;
}
