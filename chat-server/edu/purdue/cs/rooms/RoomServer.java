package edu.purdue.cs.rooms;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class RoomServer extends Thread {
	public static final String DEFAULT_SERVER = "pc.cs.purdue.edu";
	public static final int DEFAULT_PORT = 42142;

	static ArrayList<Room> rooms;

	public static void main(String[] args) {
		ServerSocket ss;

		rooms = new ArrayList<Room>();

		System.out.format("RS: starting...\n");

		try {
			ss = new ServerSocket(RoomServer.DEFAULT_PORT);
			ss.setReuseAddress(true);
		} catch (IOException ioException) {
			System.err.println("RS: unable to wait for connection");
			return;
		}

		System.out.format("RS: socket allocated; waiting for connection at %s...\n", ss.getLocalSocketAddress());

		while (true) {
			try {
				// Wait for a connection...
				Socket socket = ss.accept();

				// Create a new client and add it to the collection...
				Client client = new Client(socket);
				client.start();
			} catch (IOException e) {
				System.err.format("RS: failed to establish connection");
			}
		}
	}

	public static Room getRoom(String name) {
		synchronized (rooms) {
			for (Room room : rooms)
				if (room.getName().equals(name))
					return room;
			Room room = new Room(name);
			rooms.add(room);
			return room;
		}
	}
}
