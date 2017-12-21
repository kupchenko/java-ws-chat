package edu.dmitrii.wschat.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

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
    private List<Conversation> conversations;

//    @JoinTable(name = "user_has_roles",
//            joinColumns = @JoinColumn(name = "users", referencedColumnName = "iduser"),
//            inverseJoinColumns = @JoinColumn(name = "roles", referencedColumnName = "idrole"))
//    private List<Role> role;
}
