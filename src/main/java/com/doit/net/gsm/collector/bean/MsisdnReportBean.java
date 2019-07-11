package com.doit.net.gsm.collector.bean;

import com.doit.net.gsm.collector.base.ProtBody;

/**
 * Created by wiker on 2016/4/26.
 */
public class MsisdnReportBean extends ProtBody {
	

    public String imsi;
    public String msisdn;

    @Override
    public Object decode() {
        imsi = readString(16);
        msisdn = readString(12);
        return this;
    }

    @Override
    public byte[] encode() {
        return getBytes();
    }
}
