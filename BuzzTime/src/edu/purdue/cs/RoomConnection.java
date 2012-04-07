package edu.purdue.cs;



import java.util.Observer;

public class RoomConnection {
	NetworkConnection nc;
	Writer writer;
	Thread writerThread;
	Reader reader;
	Thread readerThread;

	String serverLocation;
	int portLocation;

	public static final String DEFAULT_SERVER = "pc.cs.purdue.edu";
	public static final int DEFAULT_PORT = 42142;

	public RoomConnection(String name, Observer roomObserver) {
		initialize(name, roomObserver, DEFAULT_SERVER, DEFAULT_PORT);
	}

	public RoomConnection(String name, Observer roomObserver, String serverLocation, int portLocation) {
		initialize(name, roomObserver, serverLocation, portLocation);
	}

	private void initialize(String name, Observer roomObserver, String serverLocation, int portLocation) {
		this.serverLocation = serverLocation;
		this.portLocation = portLocation;
		nc = new NetworkConnection(name, serverLocation, portLocation);

		reader = new Reader(nc);
		reader.addObserver(roomObserver);
		readerThread = new Thread(reader);
		readerThread.start();

		writer = new Writer(nc);
		writerThread = new Thread(writer);
		writerThread.start();
	}

	public void setNetworkObserver(Observer observer) {
		nc.addObserver(observer);
	}

	public void sendMessage(Message message) {
		writer.sendMessage(message);
	}

	public String getServerString() {
		return String.format("%s:%d", serverLocation, portLocation);
	}

	public void shutdown() {
		nc.shutdown();
		reader.shutdown();
		writer.shutdown();
		try {
			readerThread.join();
			writerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.err.printf("Unexpected exception in RoomConnection shutdown()\n");
		}
	}
}
