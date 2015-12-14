package com.areong.socket;

import java.net.Socket;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Connection {
    private Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public void println(byte[] message) {
    	DataOutputStream os;
        try {
        	os = new DataOutputStream(socket.getOutputStream());
        	// 这里可能处理数据的长度有问题
			os.write(message, 0, message.length);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}