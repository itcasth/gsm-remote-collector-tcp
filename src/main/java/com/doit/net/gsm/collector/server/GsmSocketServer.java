package com.doit.net.gsm.collector.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GsmSocketServer implements Runnable {
	
	private final static Logger log = LoggerFactory.getLogger(GsmSocketServer.class);

	private final static boolean server_start = false;
	
	private static int port = 7001;
	
	private static int TIME_OUT = 10*1000;
	private static ServerSocket serverSocket;
	private final static int RECEIVER_BUFF_SIZE = 10240*20000;
	
	public static void start() throws GSMException {
		init();
		new Thread(new GsmSocketServer()).start();
	}
	
	public static void start(int port) throws GSMException {
		setPort(port);
		init();
		new Thread(new GsmSocketServer()).start();
	}
	
	public static void init() throws GSMException {
		try {
			serverSocket = new ServerSocket(getPort());
//			serverSocket.setSoTimeout(TIME_OUT);
			serverSocket.setReceiveBufferSize(RECEIVER_BUFF_SIZE);
			log.info("LTE Socket Server Started on port:"+getPort());
		} catch (IOException e) {
			throw new GSMException("服务启动异常",e);
		}
	}
	
	@Override
	public void run() {
		while(true){
	        Socket socket=null;
	        try{
	            socket=serverSocket.accept();//从连接队列中取出一个连接，如果没有则等待
//	            socket.setKeepAlive(true);
	            GSMSocketWorkThread client = new GSMSocketWorkThread(socket);
	            ClientManager.addClient(client);
	            new Thread(client).start();
	            log.info("New LTE socket client：{}:{},client size:{}",socket.getInetAddress(),socket.getPort(),ClientManager.getClientSize());
	            //接收和发送数据
	        }catch(Exception e){
	        	log.error("",e);
	        }
	    }
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		GsmSocketServer.port = port;
	}
	
}
