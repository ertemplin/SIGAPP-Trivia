import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import edu.purdue.cs.Message;

class RoomObserver implements Observer {

		@Override
		public void update(Observable arg0, Object arg1) {
			Message message = (Message) arg1;
			String text = message.getText();
			Scanner in = new Scanner(text);
			if (in.hasNextLong()) {
				long t = in.nextLong();
				long delay = System.currentTimeMillis() - t;
				message.setText(in.nextLine().trim());
				model.appendLine(String.format("%s [rtt %d ms]", message, delay));
			} else
				model.appendLine(message.toString());
		}
	}