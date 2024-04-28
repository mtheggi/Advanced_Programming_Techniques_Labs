package com.loader.assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class loaderBalancer {
    private List<String> servers = new ArrayList<>();
    private int index = 0;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody String server) {
        if (server == null || server.isEmpty()) {
            return new ResponseEntity<>("Server registration failed", HttpStatus.BAD_REQUEST);
        }

        servers.add(server);

        System.err.println("Server registered successfully: " + server);
        return new ResponseEntity<>("Server registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/check")
    public ResponseEntity<String> check(@RequestBody String checkString) throws UnknownHostException, IOException {
        if (checkString == null || checkString.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid string");
        }

        String server = getServer();
        Socket socket = new Socket("localhost", Integer.parseInt(server));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.println(checkString);

        String response = in.readLine();

        out.close();
        in.close();
        socket.close();

        return ResponseEntity.ok(response);

    }

    private synchronized String getServer() {
        if (index >= servers.size()) {
            index = 0;
        }
        return servers.get(index++);
    }
}

// class MyHandler extends TextWebSocketHandler {
// @Override
// public void handleTextMessage(WebSocketSession session, TextMessage message)
// {
// // Handle the message from the server here
// }
// }