package me.kupchenko.wschat.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogoutEvent {
	private String username;
}
