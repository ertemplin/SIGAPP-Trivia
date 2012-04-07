package edu.purdue.cs.rooms;

import java.util.ArrayList;

public class Room {
	private String name;
	private long messageId;
	private ArrayList<Client> clients;

	public Room(String name) {
		this.name = name;
		messageId = 0;
		clients = new ArrayList<Client>();
	}

	public synchronized void broadcast(Message message) {
		message.setId(++messageId);
		System.out.printf("RS: broadcasting %d: \"%s\" to %d clients in room %s... ", message.getId(), message.getText(), clients.size(), name);
		for (Client client : clients) {
			client.sendMessage(message);
		}
		System.out.printf("done\n");
	}

	public synchronized boolean joinRoom(Client client) {
		for (Client c : clients) {
			if(c.getClientName().equals(client.getClientName())){
				if(!c.getClientUUID().equals(client.getClientUUID())){
					return false;
				}
			}
		}
		clients.add(client);
		return true;
	}

	public synchronized void leaveRoom(Client client) {
		// TODO Delete room when last client leaves.
		clients.remove(client);
	}

	public String getName() {
		return name;
	}
}
