package edu.dmitrii.wschat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
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
    @Column(name = "last_activity")
    private Timestamp lastActivity;

    @JsonIgnoreProperties({"conversations", "roles"})
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinTable(name = "user_has_conversations",
            joinColumns = @JoinColumn(name = "conversation", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "participant", referencedColumnName = "id"))
    private Set<User> participants;

    @JoinColumn(name = "sender")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Message> messages;

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", name=" + name +
                ", participants=" + participants.size() +
                ", messages=" + messages.size() +
                '}';
    }
}
