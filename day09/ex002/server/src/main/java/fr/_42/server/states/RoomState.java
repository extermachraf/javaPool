package fr._42.server.states;

import fr._42.jsonMessages.Instruction;
import fr._42.jsonMessages.SerializeDeserializeMessage;
import fr._42.models.Room;
import fr._42.repositories.room.RoomRepositoryImpl;
import fr._42.server.ClientHandler;
import lombok.Locked;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoomState implements  ConnectionState{
    @Override
    public void handle(ClientHandler context, PrintWriter writer, BufferedReader reader) throws IOException{
        while(true) {
            writer.println(SerializeDeserializeMessage.serialize(new Instruction("roomState")));
            String ClientMessage = Objects.requireNonNull(SerializeDeserializeMessage.deserialize(reader.readLine(), Instruction.class)).getMessage();
            if(ClientMessage == null){
                ClientHandler.getAuthenticatedUsers().remove(context);
                System.out.println("connexion exited " + context.getClientSocket().getInetAddress());
                break;
            }
            else if(ClientMessage.equals("1")){
                writer.println(SerializeDeserializeMessage.serialize(new Instruction("create-room")));
                String roomName = Objects.requireNonNull(SerializeDeserializeMessage.deserialize(reader.readLine(), Instruction.class)).getMessage();
                Room createdRoom = context.getRoomRepository().createRoom(roomName);
                if(createdRoom == null){
                    writer.println(SerializeDeserializeMessage.serialize("room-already-exist"));
                    context.setState(new RoomState());
                    break;
                }
                writer.println(SerializeDeserializeMessage.serialize(new Instruction("room-created")));
                context.setState(new RoomState());
                break;
            }else if(ClientMessage.equals("2")){
                writer.println(SerializeDeserializeMessage.serialize(new Instruction("join-room")));
                List<Room> rooms =  context.getRoomRepository().getAllRooms();
                writer.println(SerializeDeserializeMessage.serialize(rooms));
                String roomChoice =  Objects.requireNonNull(SerializeDeserializeMessage.deserialize(reader.readLine(), Instruction.class)).getMessage();
                try{
                    int roomIndex = Integer.parseInt(roomChoice) - 1;
                    if(roomIndex >= 0 && roomIndex < rooms.size()){
                        Room room = rooms.get(roomIndex);
                        context.setCurrentRoom(room);
                        ClientHandler.getRoomUsers()
                                .computeIfAbsent(room.getId(), k -> new CopyOnWriteArrayList<>())
                                .add(context);
                        writer.println(SerializeDeserializeMessage.serialize(new Instruction("index-is-good")));
                        context.setState(new ChatState());
                        break;
                    }else{
                        writer.println(SerializeDeserializeMessage.serialize(new Instruction("room-not-exist")));
                        context.setState(new RoomState());
                        break;
                    }
                }catch (NumberFormatException e){
                    writer.println(SerializeDeserializeMessage.serialize(new Instruction("invalidNumber")));
                    context.setState(new RoomState());
                    break;
                }

            } else if (ClientMessage.equals("3")) {
                context.setState(new RoomState());
                break;
            } else {
                writer.println(SerializeDeserializeMessage.serialize(new Instruction("chose a valid option please")));
                context.setState(new RoomState());
                break;
            }
        }
    }
}
