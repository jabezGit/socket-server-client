package com.areong.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

class ListeningThread extends Thread {
    private SocketServer socketServer;
    private ServerSocket serverSocket;
    private boolean isRunning;
    volatile List<ConnectionThread> connections = new ArrayList<ConnectionThread>();

    public ListeningThread(SocketServer socketServer, ServerSocket serverSocket) {
        this.socketServer = socketServer;
        this.serverSocket = serverSocket;
    	new Mes_Manage(connections).start();
        isRunning = true;
        // 启动发送数据线程
        
    }

    @Override
    public void run() {
        while(isRunning) {
            if (serverSocket.isClosed()) {
                isRunning = false;
                break;
            }
            try {
                Socket socket;
                socket = serverSocket.accept();
                // 关闭传输缓存
                socket.setTcpNoDelay(true);
                // 设置性能参数，可设置任意整数，数值越大，相应的参数重要性越高（连接时间，延迟，带宽）
                socket.setPerformancePreferences(1, 2, 0);
                // 将数值设为0后，会立即关闭底层的socket连接
                socket.setSoLinger(true, 0);
                ConnectionThread connection_th= new ConnectionThread(socket, socketServer);
                connection_th.start();
                // 将每一个接入的线程都记录下来
                connections.add(connection_th);
                System.out.println("有客户端接入系统");
                System.out.println("目前系统内客户端数目为："+connections.size());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public void stopRunning() {
        isRunning = false;
    }
} 