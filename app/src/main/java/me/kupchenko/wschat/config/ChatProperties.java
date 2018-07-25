package me.kupchenko.wschat.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@Getter
@Setter
@ConfigurationProperties(prefix = "chat")
public class ChatProperties {

    private int maxProfanityLevel;

    private Set<String> disallowedWords;

    private Destinations destinations;

    @Getter
    @Setter
    public static class Destinations {

        private String login;

        private String logout;

    }
}
