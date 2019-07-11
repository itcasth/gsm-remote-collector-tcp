package com.doit.net.gsm.collector.bean;

import com.doit.net.gsm.collector.base.ProtBody;

/**
 * Created by wiker on 2016/4/26.
 */
public class ResultBean extends ProtBody {
	

    public int result;

    @Override
    public Object decode() {
//    	protId = readByte2Int();
//    	reserve = readByte2Int();
//    	serialNum = readByte2Int();
//    	contentLength = readByte2Int();
        result = readInt();
        return this;
    }

    @Override
    public byte[] encode() {
    	writeInt(result);
        return getBytes();
    }
}
