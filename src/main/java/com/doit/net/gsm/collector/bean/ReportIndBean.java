package com.doit.net.gsm.collector.bean;

import com.doit.net.gsm.collector.base.ProtBody;

/**
 * Created by wiker on 2016/4/26.
 */
public class ReportIndBean extends ProtBody {
	

    public int msgId;
    public String imsi;
    public String imei;
    public int rssi;

    @Override
    public Object decode() {
        msgId = readShort();
        imsi = readString(16);
        imei = readString(16);
        rssi = readInt();
        return this;
    }

    @Override
    public byte[] encode() {
        return getBytes();
    }
}
