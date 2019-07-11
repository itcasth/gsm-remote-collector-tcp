package com.doit.net.gsm.collector.handler;

import com.doit.net.gsm.collector.base.LTEHeader;
import com.doit.net.gsm.collector.bean.StateIndBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartBeatHandler extends ILTEHandler<StateIndBean> {
	
	
	private final static Logger log = LoggerFactory.getLogger(HeartBeatHandler.class);

	private byte[] bodyData;
	
	@Override
	public byte[] getResponseData() {
		return bodyData;
	}
	
	@Override
	public void handlerMessage(LTEHeader header, StateIndBean body) {
		log.debug("Net State:"+body.netState);
		log.debug("MCC:"+body.mcc);
		log.debug("MNC:"+body.mnc);
		log.debug("ARFCN:"+body.arfcn);
		log.debug("Ms1State:"+body.ms1State);
		log.debug("Ms2State:"+body.ms2State);
		log.debug("Ms3State:"+body.ms3State);
		log.debug("Ms4State:"+body.ms4State);
		log.debug("Ms5State:"+body.ms5State);
		log.debug("Ms6State:"+body.ms6State);
	}

}
