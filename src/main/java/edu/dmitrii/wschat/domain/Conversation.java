package edu.dmitrii.wschat.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "conversations")
@Entity
@Getter
@Setter
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    //    @ManyToMany(fetch = FetchType.EAGER, targetEntity = User.class)
//    @JoinTable(name = "user_has_conversations",
//            joinColumns = @JoinColumn(name = "conversation", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "participant", referencedColumnName = "id"))
//    private Set<User> participants;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Set<Message> messages;

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", name=" + name +
//                ", participants=" + participants.size() +
                ", messages=" + messages.size() +
                '}';
    }
}
