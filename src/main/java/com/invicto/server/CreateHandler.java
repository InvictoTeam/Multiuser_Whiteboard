package com.invicto.server;

import java.util.Random;

public class CreateHandler implements HttpHandler {

    private final HttpRouter router;
    private String id;

    public CreateHandler(HttpRouter router) {
        this.router = router;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) {
        createRoom();
        response.message(200, id, "text/plain");
    }

    private void createRoom() {
        id = generateRoomId();
        router.addHandler("/" + id, new FileHandler("board.html"));
        router.addHandler("/" + id + "/ws", new WebSocketHandler());
    }

    private String generateRoomId() {
        StringBuilder roomId = new StringBuilder();
        int n = 20;
        while (n > 0) {
            Random random = new Random();
            int numVal = random.nextInt(122 - 48 + 1) + 48;
            boolean condition1 = (numVal > 57 && numVal < 65);
            boolean condition2 = (numVal > 90 && numVal < 97);
            if (!(condition1 || condition2)) {
                roomId.append((char) numVal);
                n--;
            }
        }
        return String.valueOf(roomId);
    }
}
