package com.doit.net.gsm.collector.base;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * Created by wiker on 2016/3/22.
 */
public class LTEHeader extends BaseCoder {

	public int msgType;  //4
    public int dataLength;     //1

    private SocketAddress remoteSocketAddress;
    private SocketAddress localSocketAddress;
    private int localPort;
    
    
    
    public final static int HEAD_SIZE = 6;

    public LTEHeader() {
    }


    public LTEHeader(byte[] data, int start, int len) {
        super(data, start, len);
    }


    public SocketAddress getRemoteSocketAddress() {
        return remoteSocketAddress;
    }

    public InetSocketAddress getInetSocketAddress(){
        return (InetSocketAddress)remoteSocketAddress;
    }

    public void setRemoteSocketAddress(SocketAddress socketAddress) {
        this.remoteSocketAddress = socketAddress;
    }

    public void setRemoteSocketAddress(String ip,int port){
        try {
            remoteSocketAddress = new InetSocketAddress(InetAddress.getByName(ip),port);
        } catch (UnknownHostException e) {
        }
    }
    
    public SocketAddress getLocalSocketAddress() {
		return localSocketAddress;
	}


	public void setLocalSocketAddress(SocketAddress localSocketAddress) {
		this.localSocketAddress = localSocketAddress;
	}


	public String getRemoteAddr(){
        return getInetSocketAddress().getAddress().getHostAddress();
    }

    public int getRemotePort(){
        return getInetSocketAddress().getPort();
    }

    @Override
    public Object decode() {
    	msgType = readShort();
    	dataLength = readInt();
        return this;
    }


    @Override
    public byte[] encode() {
    	writeShortHtons((short) msgType);
    	writeIntHtonl(dataLength);
        return getBytes();
    }
    
    

//    public abstract byte[] encodeHead();


    
    @Override
	public String toString() {
    	StringBuffer sb = new StringBuffer();
    	sb.append("msgType="+ msgType +",");
    	sb.append("len="+dataLength+",");
		return sb.toString();
	}


	public int getLocalPort() {
        return localPort;
    }

	public int getHeaderSize() {
		return HEAD_SIZE;
	}


	public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }
}
