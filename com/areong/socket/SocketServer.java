package com.areong.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

public class SocketServer {
    private ServerSocket serverSocket;
    private ListeningThread listeningThread;
    private int queueSize =10;

    public SocketServer(int port) {
        try {
            serverSocket = new ServerSocket();
            // 配置ServerSocket的信能参数：以寻得最好的socket性能
            // 关闭serverSocket时，立即释放serverSocket绑定的端口以便端口重用，默认是false;
            serverSocket.setReuseAddress(false);
            // accpet等待连接的超时时间为1000ms，默认为0，永不超时
            /* serverSocket.setSoTimeout(1000);*/
            // 为所有的accept方法放回的socket对象设置接受缓存区的大小，单位为字节，默认值和操作系统有关
            serverSocket.setReceiveBufferSize(512);
            // 设置性能参数，可设置任意整数，数值越大，相应的参数重要性越高（连接时间，延迟，带宽）
            serverSocket.setPerformancePreferences(1, 2, 0);
            // 服务器绑定端口，queueSize为服务器连接请求队列长度
            serverSocket.bind(new InetSocketAddress(port), queueSize);
            
            listeningThread = new ListeningThread(this, serverSocket);
            listeningThread.start();
        } catch (IOException e) {
           System.out.println("ERROR：地址占用");
           System.exit(-1);
        }
    }
    
    /*
     * Not ready for use.
     */
    private void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
           System.out.println("服务器关闭失败");
        }
    }
}