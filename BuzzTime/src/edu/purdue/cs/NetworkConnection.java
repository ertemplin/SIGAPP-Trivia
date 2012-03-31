package edu.purdue.cs;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

public class NetworkConnection extends Observable {
	String name;
	String serverLocation;
	int portLocation;

	volatile private Socket socket;
	private OutputStreamWriter outputStreamWriter;
	private BufferedReader bufferedReader;
	private Object monitor; // used to control exclusive access to socket and related variables
	private boolean running;

	public NetworkConnection(String name, String serverLocation, int portLocation) {
		this.name = name;
		this.serverLocation = serverLocation;
		this.portLocation = portLocation;
		socket = null;
		running = true;
		monitor = new Object();
	}

	public void shutdown() {
		running = false;
		if (socket != null)
			try {
				socket.close();
			} catch (IOException e) {
			}
	}

	public void writeMessage(String text) {
		boolean done = false;

		while (running & !done) {
			openSocket();
			try {
				done = true;
				outputStreamWriter.write(text);
				outputStreamWriter.flush();
			} catch (IOException e) {
				done = false;
				socket = null; // force reopening of socket
			}
		}
	}

	public String readMessage() {
		boolean done = false;
		String text = null;

		while (running & !done) {
			openSocket();
			try {
				done = true;
				text = bufferedReader.readLine();
			} catch (IOException e) {
				done = false;
				socket = null; // force reopening of socket
			}
		}
		return text;
	}

	private void writeInitialMessage() {
		try {
			outputStreamWriter.write(name);
			outputStreamWriter.write("\n");
			outputStreamWriter.flush();
		} catch (IOException e) {
			socket = null;
		}
	}

	private void sleepBriefly() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
	}

	private void notifyNetworkFailure(String text) {
		socket = null;
		String message = String.format("OPENFAILED in NetworkConnection: %s for %s:%d", text, serverLocation,
				portLocation);
		System.err.println(message);
		setChanged();
		notifyObservers("Is your network connected?");
		sleepBriefly();
	}

	private void openSocket() {
		while (socket == null && running) {
			try {
				// Open server connection; controlled by monitor variable since both reader and writer threads might try
				// to do an openSocket() simultaneously. But, synchronizing the entire method is both unnecessarily too
				// much, and also causes deadlocks with threads trying to addObserver to this object (since it is also
				// an Observable).
				synchronized (monitor) {
					socket = new Socket(serverLocation, portLocation);
					bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
				}
			} catch (UnknownHostException e) {
				notifyNetworkFailure("UnknownHostException");
			} catch (IOException e) {
				notifyNetworkFailure("IOException");
			}
			if (socket != null)
				writeInitialMessage();
		}
	}
}
