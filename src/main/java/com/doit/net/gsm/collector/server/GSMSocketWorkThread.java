package com.doit.net.gsm.collector.server;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.doit.net.gsm.collector.base.LTEHeader;
import com.doit.net.gsm.collector.handler.LTEHandlerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doit.net.gsm.collector.handler.ILTEHandler;
import com.doit.net.gsm.collector.utils.PrintUtils;

public class GSMSocketWorkThread implements Runnable {

	private final static Logger log = LoggerFactory.getLogger(GSMSocketWorkThread.class);
	
	private boolean isExit = false;
	private Socket mSocket;
	private InputStream clientRead;
	private OutputStream clientWrite;

	private GsmSocketClient client;
	public GSMSocketWorkThread(Socket mSocket) {
		this.mSocket = mSocket;
		try {
			clientRead = mSocket.getInputStream();
			clientWrite = mSocket.getOutputStream();
			lastTime = System.currentTimeMillis();
			client = new GsmSocketClient(mSocket);
		} catch (IOException e) {
			log.error("实例化GSMScoketClient 异常", e);
		}
	}

	public GsmSocketClient getClient() {
		return client;
	}

	public final Socket getSocket() {
		return mSocket;
	}


	public void stopConn(){
		if(mSocket != null){
			try {
				mSocket.close();
			} catch (IOException e1) {
			}
		}
	}
	private long lastTime;

	public void exit(){
		isExit = true;
	}
	
	@Override
	public void run() {
		BufferedInputStream bis = new BufferedInputStream(clientRead);
		while(true) {
			try{
				if(isExit){
					break;
				}
				if(bis.available()<=0){
					Thread.sleep(100);
					continue;
				}
				if((System.currentTimeMillis() - lastTime)>1000*60*3){
					log.info("Client timeout close !");
					break;
				}

				lastTime = System.currentTimeMillis();
				
				byte[] headBytes = new byte[LTEHeader.HEAD_SIZE];
				bis.read(headBytes);
				PrintUtils.printHex(headBytes);
				//解析头部
				LTEHeader header = new LTEHeader(headBytes,0,LTEHeader.HEAD_SIZE);
				header.decode();
				header.setRemoteSocketAddress(mSocket.getRemoteSocketAddress());
				header.setLocalSocketAddress(mSocket.getLocalSocketAddress());
				
				log.info("Receive msg msgType:{},data len:{},from:{}",header.msgType,header.dataLength,mSocket.getRemoteSocketAddress());
				
				ILTEHandler handler = LTEHandlerManager.get(header.msgType);
				if(handler == null){
					log.warn("Msg Type:{} no handler",header.msgType);
					Thread.sleep(100);
					continue;
				}

				byte[] bodyBytes = new byte[header.dataLength];
				bis.read(bodyBytes);
				
				PrintUtils.printHex(bodyBytes);
				
				handler.handler(header, mSocket, bodyBytes);
//				client.send(2,new CfgReqBean());
				

//				byte[] responseBytes = handler.getResponseData();
//				if(responseBytes != null && responseBytes.length>0){
//					log.info("Response len:{}",responseBytes.length);
//					PrintUtils.printHex(responseBytes);
//					clientWrite.write(responseBytes);
//					clientWrite.flush();
////					mSocket.shutdownOutput();
//				}
				handler.finish();
			}catch (Exception e) {
				log.error("Socket 处理异常", e);
				closeConn(bis);
				break;
			}
		}
		closeConn(bis);
	}

	private void closeConn(BufferedInputStream bis) {
		try {
            Thread.sleep(100);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
		try {
            bis.close();
        } catch (IOException e2) {
        }
		stopConn();
	}

	//这种方式貌似。好慢，不知为何：呵呵
	public static byte[] toByteArray(InputStream input) throws IOException {
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    byte[] buffer = new byte[1024];
	    int n = 0;
	    while (-1 != (n = input.read(buffer))) {
	        output.write(buffer, 0, n);
	    }
	    return output.toByteArray();
	}
	
	public static byte[] toByteArray1(InputStream input) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int bytes = 0;
		byte[] buffer = new byte[1024];
		while ((bytes = input.read(buffer)) > 0) {
			baos.write(buffer, 0, bytes);
		}
		return baos.toByteArray();
	}
	
	public static byte[] toBytes(InputStream input) throws IOException{
		int count = 0;  
        while (count == 0) {  
            count = input.available();  
        }  
        byte[] b = new byte[count];  
        input.read(b);  
        return b;  
	}
	
}
