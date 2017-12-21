package edu.dmitrii.wschat.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Sergi Almar
 */
@Getter
@Setter
@ToString
public class ChatMessage {
	private String message;
	private String username;
}
