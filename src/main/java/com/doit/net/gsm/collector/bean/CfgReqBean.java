package com.doit.net.gsm.collector.bean;

import com.doit.net.gsm.collector.base.ProtBody;

/**
 * Created by wiker on 2016/4/26.
 */
public class CfgReqBean extends ProtBody {
	

    public int cfgCmd;         //unit 16
    public int workMode;       //unit 8
    public String tmsiSdn;     //char[20]
    public String cmsisDn1;    //char[20]
    public String cmsisDn2;    //char[20]
    public String cmsisDn3;    //char[20]
    public String cmsisDn4;    //char[20]
    public String callMsg;     //char[70]

    @Override
    public Object decode() {
        cfgCmd = readShort();
        workMode = readByte2Int();
        tmsiSdn = readString(20);
        cmsisDn1 = readString(20);
        cmsisDn2 = readString(20);
        cmsisDn3 = readString(20);
        cmsisDn4 = readString(20);
        callMsg = readString(70);
        return this;
    }

    @Override
    public byte[] encode() {
        writeShortHtons((short)cfgCmd);
        /*
        writeInt2Byte(workMode);
        writeString(tmsiSdn,20);
        writeString(cmsisDn1,20);
        writeString(cmsisDn2,20);
        writeString(cmsisDn3,20);
        writeString(cmsisDn4,20);
        writeString(callMsg,70);
        */
        return getBytes();
    }
}
