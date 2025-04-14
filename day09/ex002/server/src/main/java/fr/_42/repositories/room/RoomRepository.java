package fr._42.repositories.room;

import fr._42.models.Room;

import java.util.List;

public interface RoomRepository {
    Room createRoom(String roomName);
    List<Room> getAllRooms();
}
