package me.kupchenko.wschat.event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ParticipantRepository {

    private Map<String, LoginEvent> activeSessions = new ConcurrentHashMap<>();

    public void add(String sessionId, LoginEvent event) {
        activeSessions.put(sessionId, event);
    }

    public LoginEvent getParticipant(String sessionId) {
        return activeSessions.get(sessionId);
    }

    public void removeParticipant(String sessionId) {
        activeSessions.remove(sessionId);
    }

    public Map<String, LoginEvent> getActiveSessions() {
        return activeSessions;
    }

    public void setActiveSessions(Map<String, LoginEvent> activeSessions) {
        this.activeSessions = activeSessions;
    }

    public boolean isLogged(String string) {
        activeSessions.entrySet().forEach(System.out::println);
        return activeSessions.entrySet().stream().anyMatch(e -> e.getValue().getUsername().equals(string));
    }
}
