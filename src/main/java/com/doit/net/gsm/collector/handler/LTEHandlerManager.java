package com.doit.net.gsm.collector.handler;

import java.util.HashMap;
import java.util.Map;

import com.doit.net.gsm.collector.constants.GsmMessageType;

/**
 * Created by wiker on 2016/3/15.
 */
public class LTEHandlerManager {


    private static Map<Integer,ILTEHandler> handlerMap = new HashMap<Integer,ILTEHandler>();

    public static void initHandler(){
    	put(GsmMessageType.DEV_SS_VERSION_IND.ordinal(),new VersionIndHandler());
    	put(GsmMessageType.DEV_SS_CFG_CNF.ordinal(),new CfgAckHandler());
    	put(GsmMessageType.DEV_SS_STATE_IND.ordinal(),new HeartBeatHandler());
    	put(GsmMessageType.DEV_SS_IMSI_IND.ordinal(),new UeidReportHandler());
//    	put(GsmMessageType.SS_DEV_IMSI_RSP.ordinal(),new ReportResultHandler());
    	put(GsmMessageType.DEV_SS_MSISDNM_REPORT.ordinal(),new MsisdnResultHandler());
    }

    public static ILTEHandler get(int msgType){
        return handlerMap.get(msgType);
    }
    
    public static void put(int msgType,ILTEHandler handler){
    	handlerMap.put(msgType, handler);
    }

}
