package com.doit.net.gsm.collector.bean;

import com.doit.net.gsm.collector.base.ProtBody;

/**
 * Created by wiker on 2016/4/26.
 */
public class ReportResultBean extends ProtBody {
	

    public int msgId;
    public int result;

    @Override
    public Object decode() {
        msgId = readShort();
        result = readInt();
        return this;
    }

    @Override
    public byte[] encode() {
        writeShortHtons((short) msgId);
        writeIntHtonl(result);
        return getBytes();
    }
}
