package sample.websocket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import sample.websocket.domain.ChatRoom;
import sample.websocket.domain.MessageChat;
import sample.websocket.repository.MessageChatRoomRepository;


import javax.validation.constraints.NotNull;
import java.util.Map;

@Component
public class MessageWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final MessageChatRoomRepository messageChatRoomRepository;

    public MessageWebSocketHandler(MessageChatRoomRepository messageChatRoomRepository, ObjectMapper objectMapper) {
        this.messageChatRoomRepository = messageChatRoomRepository;
        this.objectMapper = objectMapper;

        this.messageChatRoomRepository.add("test");
    }

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) {
        messageChatRoomRepository.join(session, "test");
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) {
        messageChatRoomRepository.leaveAll(session);
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) throws JsonProcessingException {
        String payload = message.getPayload();
        MessageChat messageChat = objectMapper.readValue(payload, MessageChat.class);
        Map<String, ChatRoom> rooms = messageChatRoomRepository.get();
        ChatRoom room = rooms.get(messageChat.getChatRoomId());
        room.send(messageChat, this.objectMapper);
    }
}