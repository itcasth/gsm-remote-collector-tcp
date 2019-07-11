package com.doit.net.gsm.collector.bean;

import com.doit.net.gsm.collector.base.ProtBody;

/**
 * Created by wiker on 2016/4/26.
 */
public class VersionBean extends ProtBody {
	

    public String version;

    @Override
    public Object decode() {
//    	protId = readByte2Int();
//    	reserve = readByte2Int();
//    	serialNum = readByte2Int();
//    	contentLength = readByte2Int();
        version = readString(20);
        return this;
    }

    @Override
    public byte[] encode() {
    	writeString(version);
        return getBytes();
    }
}
