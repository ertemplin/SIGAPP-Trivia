package edu.purdue.cs;



import java.util.Observable;

public class Reader extends Observable implements Runnable {

	private NetworkConnection nc;
	private boolean running;

	public Reader(NetworkConnection nc) {
		this.nc = nc;
		running = true;
	}

	public void shutdown() {
		running = false;
	}

	public void run() {
		while (running) {
			String text = nc.readMessage();
			if (text != null) { // TODO null means !running
				Message message = new Message(text);
				message.parseText();
				setChanged();
				notifyObservers(message);
			}
		}
	}
}
