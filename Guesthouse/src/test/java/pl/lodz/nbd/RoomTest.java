package pl.lodz.nbd;

import org.junit.jupiter.api.Test;
import pl.lodz.nbd.manager.RoomManager;
import pl.lodz.nbd.model.Room;
import pl.lodz.nbd.repository.impl.RoomRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoomTest {

    private static final RoomRepository roomRepository = new RoomRepository();


    @Test
    void getAllRoomsTest() {
        RoomManager roomManager = new RoomManager(roomRepository);

        List<Room> rooms = roomManager.getAllRooms();
        assertNotNull(rooms);
        assertTrue(rooms.size() > 0);
        System.out.println(rooms);
        System.out.println(rooms.get(0).getUuid());

    }

    @Test
    void addRoomTest() {
        RoomManager roomManager = new RoomManager(roomRepository);

        //Check if rooms are persisted
        roomManager.addRoom(100.0, 2, 100);
        //assertNotNull(roomManager.addRoom(200.0, 3, 101));

        //Check if room is not persisted(existing room number)
//        assertNull(roomManager.addRoom(1000.0, 5, 100));
//
//        //Check if getRoomByNumber works properly
//        assertNotNull(roomManager.getByRoomNumber(100));
//        assertNotNull(roomManager.getByRoomNumber(101));
//        assertNull(roomManager.getByRoomNumber(300));
    }

//    @Test
//    void updateRoomTest() {
//        RoomManager roomManager = new RoomManager(roomRepository);
//
//        Room room = roomManager.addRoom(300.0, 3, 1040);
//        assertEquals(3, room.getSize());
//        room.setSize(10);
//        roomManager.updateRoom(room);
//        Room newRoom = roomManager.getByRoomNumber(1040);
//        assertEquals(10, newRoom.getSize());
//    }

}
