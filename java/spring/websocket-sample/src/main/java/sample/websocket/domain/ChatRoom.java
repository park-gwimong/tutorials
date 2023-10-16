package sample.websocket.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoom {
    String id;
    Set<WebSocketSession> sessions = new HashSet<>();

    public ChatRoom(String id) {
        this.id = id;
    }

    public void join(WebSocketSession session) {
        sessions.add(session);
    }

    public <T> void send(T messageObject, ObjectMapper objectMapper) throws JsonProcessingException {
        TextMessage message = new TextMessage(objectMapper.writeValueAsString(messageObject));

        sessions.parallelStream().forEach(session -> {
            try {
                session.sendMessage(message);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    public void leave(WebSocketSession target) {
        String targetId = target.getId();
        sessions.removeIf(session -> session.getId().equals(targetId));
    }

}