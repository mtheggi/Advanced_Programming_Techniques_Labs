
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Server1 {
	private static final String LOAD_BALANCER_IP = "localhost";
	private static final int LOAD_BALANCER_PORT = 8080;

	public static void main(String[] args) throws IOException {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the port number of the server: ");
		int serverPort = scanner.nextInt();
		registerWithLoadBalancer(String.valueOf(serverPort));
		ServerSocket ss = new ServerSocket(serverPort); // create a server socket with port 6666
		System.out.println("Server is started.\n");
		scanner.close();
		try {
			while (true) {
				/*
				 * The server waits for a client to send a request. When the connection request
				 * reaches, it establishes a connection with the client and returns the socket
				 * object that will be used for communication with the client.
				 */

				Socket socket = ss.accept();
				System.out.println("A client sent request and connection is established.");
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				String str = in.readLine();
				System.err.println(str);
				boolean isAlphanumeric = str != null && str.matches("[A-Za-z0-9]+");
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				if (isAlphanumeric) {
					out.println("The message is alphanumeric.");
				} else {
					out.println("The message is not alphanumeric.");

				}

			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			/* Don't forget to close the server socket */
			ss.close();
		}
	}

	private static void registerWithLoadBalancer(String SeverPort) {
		try {
			URI uri = new URI("http", null, LOAD_BALANCER_IP, LOAD_BALANCER_PORT, "/register", null, null);
			java.net.URL url = uri.toURL();
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			String input = SeverPort;

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			System.out.println("Server registered successfully");

			conn.disconnect();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
