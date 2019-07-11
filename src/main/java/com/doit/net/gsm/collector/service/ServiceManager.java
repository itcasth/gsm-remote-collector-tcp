package com.doit.net.gsm.collector.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doit.net.gsm.collector.base.LTEHeader;
import com.doit.net.gsm.collector.base.ProtBody;

public class ServiceManager {
	
	private final static Logger log = LoggerFactory.getLogger(ServiceManager.class);

	private static Map<Integer,IResponseHandler> serviceDict = new HashMap<Integer,IResponseHandler>();
	
	static{
		
	}
	
	public static void registerService(int msgType,IResponseHandler handler){
		try {
			log.info("Register response msgType:{},class:{}",msgType,handler.getClass().getName());
			serviceDict.put(msgType, handler);
		} catch (Exception e) {
			log.error("Register response error", e);
		}
	}
	
	public static void callService(LTEHeader header,ProtBody resp){
		IResponseHandler handler = serviceDict.get(header.msgType);
		if(handler != null){
			log.debug("Call service:"+handler.getClass().getName());
			handler.response(header, resp);
		}
	}
	
	
}
