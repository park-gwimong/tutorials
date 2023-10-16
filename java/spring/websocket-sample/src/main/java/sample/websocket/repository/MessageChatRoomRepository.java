package sample.websocket.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;
import sample.websocket.domain.ChatRoom;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MessageChatRoomRepository {

    private final Map<String, ChatRoom> chatRooms = new HashMap<>();

    public Map<String, ChatRoom> get() {
        return this.chatRooms;
    }

    public void add(String roomId) {
        ChatRoom room = new ChatRoom(roomId);
        this.chatRooms.put(roomId, room);
    }

    public void delete(String roomId) {
        this.chatRooms.remove(roomId);
    }

    public void join(WebSocketSession session, String roomId) {
        this.chatRooms.get(roomId).join(session);
    }

    public void leave(WebSocketSession session, String roomId) {
        this.chatRooms.get(roomId).leave(session);
    }

    public void leaveAll(WebSocketSession session) {
        this.chatRooms.forEach((roomId, chatRoom) -> chatRoom.leave(session));
    }

}