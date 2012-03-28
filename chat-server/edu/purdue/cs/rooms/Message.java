package edu.purdue.cs.rooms;

import java.util.Scanner;

public class Message {
	public final static int PROTOCOL_VERSION = 1;
	private long protocolVersion = 0;
	private long messageId = 0;
	private long tArrival = 0;
	private long tDeparture = 0;
	private long tDelay = 0;
	private String text;

	public Message(String text) {
		this.text = text;
	}

	public String toClient() {
		tDelay = tDeparture - tArrival;
		return String.format("%d %d %d %s\n", PROTOCOL_VERSION, messageId, tDelay, text);
	}

	/**
	 * Used by client to parse message text from server into fields; matches toClient().
	 */
	public void parseText() {
		Scanner s = new Scanner(text);
		if (s.hasNextLong())
			protocolVersion = s.nextLong();
		if (s.hasNextLong())
			messageId = s.nextLong();
		if (s.hasNextLong())
			tDelay = s.nextLong();
		this.text = s.nextLine().trim();
	}

	/**
	 * Used by client to send message to server.
	 * 
	 * @return String value to be sent to server.
	 */
	public String toServer() {
		// TODO Need to escape embedded newlines (thanks, John).
		return String.format("%s\n", text);
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setId(long messageId) {
		this.messageId = messageId;
	}

	public long getId() {
		return messageId;
	}

	public long getProtocolVersion() {
		return protocolVersion;
	}

	public void setArrival() {
		tArrival = System.currentTimeMillis();
	}

	public void setDeparture() {
		tDeparture = System.currentTimeMillis();
	}

	/*
	 * Converts Message to string.
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return String.format("%d: [%d ms] %s", messageId, tDelay, text);
	}
}
