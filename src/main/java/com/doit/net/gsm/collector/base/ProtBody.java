package com.doit.net.gsm.collector.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import com.doit.net.gsm.collector.utils.ByteUtils;
import com.doit.net.gsm.collector.utils.ProtUtils;

/**
 * Created by wiker on 2016/3/17.
 */
public abstract class ProtBody {


    public byte[] data;
    public int start,len;
    public int pos;

    public int dataLen;

    public ProtBody(){}

    public ProtBody(byte[] data, int start, int len){
        this.data = data;
        this.start = start;
        this.len = len;
    }

    public void arrayCopy(byte[] dest){
        try {
            System.arraycopy(data,pos+start,dest,0,dest.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract Object decode();

    public Object decode(byte[] data){
        return null;
    }

    public int readIntHtonl(){
        int i= ProtUtils.readIntHtonl(data,start+pos);
        pos+=4;
        dataLen +=4;
        return i;
    }

    public int readInt(){
        int i= ProtUtils.readInt(data,start+pos);
        pos+=4;
        dataLen +=4;
        return i;
    }

    public short readShortHtons(){
        short s = (short) ProtUtils.readShortHtons(data,start+pos);
        pos+=2;
        dataLen +=2;
        return s;
    }

    public short readShort(){
        short s = (short) ProtUtils.readShort(data,start+pos);
        pos+=2;
        dataLen +=2;
        return s;
    }


    public String readString(int len){
    	if(len <=0){
    		return null;
    	}
        byte[] serialBytes = new byte[len];
        System.arraycopy(data,pos+start,serialBytes,0,serialBytes.length);
        String s = ProtUtils.getString(serialBytes, "GBK");
        pos+=len;
        dataLen +=len;
        return s;
    }

    public <T> T readObject(Class<T> clz,byte[] d){
        return readObject(clz,d,pos+start);
    }

    public <T> T readObject(Class<T> clz,byte[] d,int start){
        try {
            ProtBody temp = (ProtBody)clz.newInstance();
//            log.debug(clz.getName()+",start="+temp.start);
            temp.data = d;
            temp.start = start;
            temp.len = d.length;
//            log.trace(String.format("read object:%s,_data size:%s,start:%s",clz.getName(),d.length,start));
            return (T)temp.decode();
        } catch (Exception e) {
        }
        return null;
    }

//    public <T> T readObject(Class<T> clz,int st){
//        try {
//            ProtBody temp = (ProtBody)clz.newInstance();
////            log.debug(clz.getName()+",start="+temp.start);
//            temp._data = _data;
//            temp.start = pos+start+st;
//            temp.len = _data.length;
//            return (T)temp.decode();
//        } catch (Exception e) {
//            log.error("read _data error",e);
//        }
//        return null;
//    }

    public <T> T readObject(Class<T> clz){
        return readObject(clz,data,pos+start);
    }

    public int readByte2Int(){
        byte b = data[pos+start];
        pos += 1;
        dataLen +=1;
        return ByteUtils.byte2Int(b);
    }

    public int readByte2SInt(){
        byte b = data[pos+start];
        pos += 1;
        dataLen +=1;
        return ByteUtils.byte2Int(b)-114;
    }

    public byte readByte(){
        byte b = data[pos+start];
        pos += 1;
        dataLen +=1;
        return b;
    }

    public byte[] readBytes(int len){
        byte[] b = new byte[len];
        System.arraycopy(data,pos+start,b,0,b.length);
        pos += len;
        dataLen +=len;
        return b;
    }

    private int sessionId;

    private boolean needReceive;  //是否需要接收消息，为true时会将当前发送记录保存，等待接收消息

    private SocketAddress socketAddress;


    public int getSessionId() {
        return sessionId;
    }

    /**
     * 同一对象不可以被调用多次
     * @return
     */
    public abstract byte[] encode();

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public void setSocketAddress(String ip,int port){
        try {
            socketAddress = new InetSocketAddress(InetAddress.getByName(ip),port);
        } catch (UnknownHostException e) {
        }
    }

    public boolean isNeedReceive() {
        return needReceive;
    }

    public void setNeedReceive(boolean needReceive) {
        this.needReceive = needReceive;
    }

    public ByteArrayOutputStream mByteOutStream = new ByteArrayOutputStream();

    public void writeInt(int i){
        try {
            mByteOutStream.write(ProtUtils.int2Byte(i));
        } catch (IOException e) {
        }
    }
    
    public void writeIntHtonl(int i){
        try {
            mByteOutStream.write(ProtUtils.int2Byte(ByteUtils.htonl(i)));
        } catch (IOException e) {
        }
    }

    public void writeInt2Byte(int i){
        try {
            mByteOutStream.write(ByteUtils.intToByte(i));
        } catch (Exception e) {
        }
    }

    public void writeShort(short s){
        try {
//            mByteOutStream.write(ProtUtils.short2Byte(ByteUtils.htons(s)));
            mByteOutStream.write(ProtUtils.short2Byte(s));
        } catch (IOException e) {
        }
    }

    public void writeShortHtons(short s){
        try {
            mByteOutStream.write(ProtUtils.short2Byte(ByteUtils.htons(s)));
//            mByteOutStream.write(ProtUtils.short2Byte(s));
        } catch (IOException e) {
        }
    }

    public void writeString(String s,int len){
        try {
            byte[] sBytes = new byte[len];
            if(s == null){
                mByteOutStream.write(sBytes);
                return;
            }
            byte[] temp = s.getBytes("GBK");
            System.arraycopy(temp,0,sBytes,0,temp.length);
            mByteOutStream.write(sBytes);
        } catch (IOException e) {
        }
    }
    
    public void writeString(String s){
        try {
            if(s == null){
                return;
            }
            byte[] temp = s.getBytes("GBK");
            byte[] sBytes = new byte[temp.length];
            System.arraycopy(temp,0,sBytes,0,temp.length);
            mByteOutStream.write(sBytes);
        } catch (IOException e) {
        }
    }

    public void writeBytes(byte[] b){
        try {
            mByteOutStream.write(b);
        } catch (IOException e) {
        }
    }
    public void writeByte(byte b){
        try {
            mByteOutStream.write(b);
        } catch (Exception e) {
        }
    }

    public void writeObject(ProtBody p){
        try {
            if(p == null){
                return;
            }
            byte[] b = p.encode();
            mByteOutStream.write(p.getBytes());
        } catch (Exception e) {
        }
    }
    
    protected void flush(){
    	data = null;
    }


    public byte[] getBytes(){
        return mByteOutStream.toByteArray();
    }

    public int getByteLength(){
        return 0;
    }
}
