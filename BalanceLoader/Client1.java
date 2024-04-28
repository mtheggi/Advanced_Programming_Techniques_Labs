
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client1 {
	private static final String LOAD_BALANCER_IP = "localhost";
	private static final int LOAD_BALANCER_PORT = 8080;

	public static void main(String[] args) {
		try {
			/*
			 * This line establishes a connection with the server with IP address
			 * "localhost" or "127.0.0.1" and port 6666, it is the same port that will be
			 * used in the server. Note that: "localhost" refers to the local computer that
			 * a program is running on. The local machine is defined as "localhost," which
			 * gives it an IP address of 127.0. 0.1.
			 */
			// System.out.println("Hi I am the new client");
			// Socket socket = new Socket("localhost", 6666);

			/*
			 * Don't forget to set the autoflush parameter of the PrintWriter object as true
			 */

			//

			/* The client sends a user-input message to the server */

			// The following code sends one hardcoded message
			/*
			 * Thread.sleep(5000);
			 * String message = "Hello world, I am Client 1";
			 * out.println(message);
			 * System.out.println("I have sent the message to the server");
			 */

			// The following code sends any number of messages that the user enters in the
			// client console
			Scanner scanner = new Scanner(System.in);
			String message = null;
			for (int i = 0; (message = scanner.nextLine()) != null; i++) {

				System.out.println(sendCheckRequest(message));
			}
			scanner.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static String sendCheckRequest(String checkString) {
		try {
			URI uri = new URI("http", null, LOAD_BALANCER_IP, LOAD_BALANCER_PORT, "/check", null, null);
			URL url = uri.toURL();
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			OutputStream os = conn.getOutputStream();
			os.write(checkString.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			StringBuilder response = new StringBuilder();
			while ((output = br.readLine()) != null) {
				response.append(output);
			}

			conn.disconnect();
			return response.toString();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
}