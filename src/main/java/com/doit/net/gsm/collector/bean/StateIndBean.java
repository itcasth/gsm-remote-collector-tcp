package com.doit.net.gsm.collector.bean;

import com.doit.net.gsm.collector.base.ProtBody;

/**
 * Created by wiker on 2016/4/26.
 */
public class StateIndBean extends ProtBody {
	

    public int netState;
    public int mcc;
    public int mnc;
    public int arfcn;
    public int ms1State;
    public int ms2State;
    public int ms3State;
    public int ms4State;
    public int ms5State;
    public int ms6State;

    @Override
    public Object decode() {
//    	protId = readByte2Int();
//    	reserve = readByte2Int();
//    	serialNum = readByte2Int();
//    	contentLength = readByte2Int();
        netState = readShort();
        mcc = readShort();
        mnc = readShort();
        arfcn = readShort();
        ms1State = readShort();
        ms2State = readShort();
        ms3State = readShort();
        ms4State = readShort();
        ms5State = readShort();
        ms6State = readShort();
        return this;
    }

    @Override
    public byte[] encode() {
        return getBytes();
    }
}
