package me.kupchenko.wschat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "messages")
@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @JsonIgnoreProperties({"conversations", "roles"})
    @JoinColumn(name = "sender")
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    private User sender;
    @JsonIgnoreProperties({"participants", "messages"})
    @JoinColumn(name = "conversation")
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Conversation.class)
    private Conversation conversation;
    private Timestamp timestamp;

}
