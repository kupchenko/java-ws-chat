package edu.dmitrii.wschat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @JsonIgnoreProperties({"conversations", "roles"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender")
    private User sender;
    @JsonIgnoreProperties({"participants", "messages"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "conversation")
    private Conversation conversation;
    private Timestamp timestamp;

}
