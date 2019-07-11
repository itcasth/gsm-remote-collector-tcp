package com.doit.net.gsm.collector.base;

/**
 * 发送消息构造
 * Created by wiker on 2016/3/22.
 */
public class LTEMessage extends BaseCoder {

    private LTEHeader header;
    private ProtBody msgBody;
    private byte[] msgBits;

    public LTEMessage() {
    }

    public LTEMessage(LTEHeader header, ProtBody msgBody) {
        this.header = header;
        this.msgBody = msgBody;


        encode();
    }

    @Override
    public Object decode() {
        return null;
    }

    @Override
    public byte[] encode() {
    	if(msgBody != null){
            byte[] body = msgBody.encode();
            int bodyLen = body.length;
            header.dataLength = bodyLen;
            
            writeBytes(header.encode());
            writeBytes(body);
            msgBits = getBytes();
    	}else{
            writeBytes(header.encode());
            msgBits = getBytes();
    	}
        return msgBits;
    }

    public LTEHeader getHeader() {
        return header;
    }

    public void setHeader(LTEHeader header) {
        this.header = header;
    }

    public ProtBody getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(ProtBody msgBody) {
        this.msgBody = msgBody;
    }

    public byte[] getMsgBits() {
        return msgBits;
    }

    public void setMsgBits(byte[] msgBits) {
        this.msgBits = msgBits;
    }
}
