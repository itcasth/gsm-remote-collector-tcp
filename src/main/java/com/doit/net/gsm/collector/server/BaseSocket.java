package com.doit.net.gsm.collector.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.doit.net.gsm.collector.base.LTEHeader;
import com.doit.net.gsm.collector.utils.PrintUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doit.net.gsm.collector.base.LTEMessage;
import com.doit.net.gsm.collector.base.ProtBody;

public class BaseSocket {

	protected final static Logger log = LoggerFactory.getLogger(BaseSocket.class);
	
	private Socket mSocket;
	private InputStream clientRead;
	private OutputStream clientWrite;
	
	public BaseSocket(){}
	
	public BaseSocket(Socket socket){
		this.mSocket = socket;
		try {
			clientRead = mSocket.getInputStream();
			clientWrite = mSocket.getOutputStream();
		} catch (IOException e) {
			log.error("实例化GSMBaseSocket 异常", e);
		}
	}
	
	public void send(int msgType,ProtBody p) throws GSMException {
		try {
			LTEHeader header = new LTEHeader();
			header.msgType = msgType;

			LTEMessage msg = new LTEMessage();
			msg.setHeader(header);
			if(p != null){
				msg.setMsgBody(p);
			}
			msg.encode();
			
			log.info("Send msg msgType:{},len:{},To:{}",msgType,msg.getMsgBits().length,mSocket.getRemoteSocketAddress().toString());
			
//			System.out.println("Send msg:"+PTMessageID.values()[messageId]);
			PrintUtils.printHex(msg.getMsgBits());
			clientWrite.write(msg.getMsgBits());
			clientWrite.flush();
//			mSocket.shutdownOutput();
			
			/*
			BufferedInputStream bis = new BufferedInputStream(clientRead);
			byte[] headBytes = new byte[LTEHeader.HEAD_SIZE];
			bis.read(headBytes);
			
			//解析头部
			LTEHeader respHeader = new LTEHeader(headBytes,0,LTEHeader.HEAD_SIZE);
			respHeader.decode();
			respHeader.setRemoteSocketAddress(mSocket.getRemoteSocketAddress());
			respHeader.setLocalSocketAddress(mSocket.getLocalSocketAddress());
			
			log.debug("Resp msgType:{},code:{},len:{}",respHeader.msgType,respHeader.msgCode,respHeader.dataLength);
			
			byte[] bodyBytes = new byte[respHeader.dataLength];
			bis.read(bodyBytes);
			PrintUtils.printHex(bodyBytes);
			
	        T t = decode(c,bodyBytes);
	        ServiceManager.callService(respHeader, t);
	        return t;
	        */
		} catch (IOException e) {
			log.error("发送消息异常", e);
			throw new GSMException("发送消息异常",e);
		}
	}
	
	private <T extends ProtBody> T decode(Class<? extends ProtBody> c, byte[] bodyData) {
        //通过范型的方式直接解码
        try {
        	T respBody = (T) c.newInstance();
            respBody.data = bodyData;
            respBody.decode();
            return respBody;
        } catch (Exception e) {
        	log.error("解析数据异常", e);
        }
        return null;
    }

	public Socket getmSocket() {
		return mSocket;
	}

	public void setmSocket(Socket mSocket) {
		this.mSocket = mSocket;
	}

	public InputStream getClientRead() {
		return clientRead;
	}

	public void setClientRead(InputStream clientRead) {
		this.clientRead = clientRead;
	}

	public OutputStream getClientWrite() {
		return clientWrite;
	}

	public void setClientWrite(OutputStream clientWrite) {
		this.clientWrite = clientWrite;
	}
	
	
}
