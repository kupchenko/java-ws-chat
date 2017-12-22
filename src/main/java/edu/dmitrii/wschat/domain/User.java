package edu.dmitrii.wschat.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Table(name = "users")
@Entity
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Conversation.class)
    @JoinTable(name = "user_has_conversations",
            joinColumns = @JoinColumn(name = "participant", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "conversation", referencedColumnName = "id"))
    private Set<Conversation> conversations;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinTable(name = "user_has_roles",
            joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id"))
    private Set<Role> roles;
}
