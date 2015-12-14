package com.areong.socket.example;

import com.areong.socket.Connection;
import com.areong.socket.MessageHandler;

class EchoHandler implements MessageHandler {
    @Override
    public void onReceive(Connection connection, byte[] message) {
        connection.println(message);
    }
}