package edu.purdue.cs.rooms;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
	Room room;

	Scanner in;
	PrintWriter out;

	int cReceived;
	int cSent;

	static int clientCount = 0;
	int clientId;

	public Client(Socket socket) throws IOException {
		// Setup input and output stream for this client connection...
		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();
		in = new Scanner(new InputStreamReader(is));
		out = new PrintWriter(os, true);

		// Initialize statistics...
		clientId = ++clientCount;
		cReceived = 0;
		cSent = 0;

		// Use the remote socket as the connection name...
		String name = socket.getRemoteSocketAddress().toString();
		setName(name);
		System.out.format("RS: connection from %d at %s started\n", clientId, name);
	}

	public void run() {
		Message message = new Message("Greetings from the Room Server");
		message.setArrival();
		sendMessage(message);
		System.out.format("RS: clientId %d assigned to connection %s\n", clientId, getName());

		if (in.hasNextLine()) {
			room = RoomServer.getRoom(in.nextLine());
			room.joinRoom(this);

			System.out.printf("RS: clientId %d joins room %s\n", clientId, room.getName());
			message = new Message(String.format("Connection from %s joins room %s", this.getName(), room.getName()));
			message.setArrival();
			room.broadcast(message);

			while (in.hasNextLine()) {
				message = new Message(in.nextLine());
				message.setArrival();
				cReceived++;
				room.broadcast(message);
			}
		} 

		System.out.printf("RS: end client %s (%d); %d messages received; %d messages sent\n", this.getName(),
				clientId, cReceived, cSent);
		if (room != null)  // must check for null since room might not have been determined
			room.leaveRoom(this);
	}

	void sendMessage(Message message) {
		message.setDeparture();
		out.write(message.toClient());
		out.flush();
		if (out.checkError()) {
			System.err.printf("RS: send error in client %s\n", this.getName());
			// TODO should leave room, but can't without error; need to pass flag back
		}
		cSent++;
	}
}
