package edu.purdue.cs;

public class Writer implements Runnable {
	private NetworkConnection nc;
	private ArrayDeque<Message> messages;
	private boolean running;

	public Writer(NetworkConnection nc) {
		this.nc = nc;
		messages = new ArrayDeque<Message>();
		running = true;
	}

	public synchronized void sendMessage(Message message) {
		messages.addLast(message);
		notify();
	}

	private synchronized Message getMessage() {
		while (running & messages.isEmpty())
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		return running ? messages.removeFirst() : null;
	}

	public synchronized void shutdown() {
		running = false;
		notify();
	}

	public void run() {
		while (running) {
			Message message = getMessage();

			if (message == null) {
				if (running) {
					System.err.printf("WriterThread: unexpected null message\n");
					System.exit(1);
				}
			} else {
				nc.writeMessage(message.toServer());
			}
		}
	}
}
