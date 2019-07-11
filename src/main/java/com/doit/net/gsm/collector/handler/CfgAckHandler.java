package com.doit.net.gsm.collector.handler;

import com.doit.net.gsm.collector.bean.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doit.net.gsm.collector.base.LTEHeader;

public class CfgAckHandler extends ILTEHandler<ResultBean> {
	
	
	private final static Logger log = LoggerFactory.getLogger(CfgAckHandler.class);

	private byte[] bodyData;
	
	@Override
	public byte[] getResponseData() {
		return bodyData;
	}
	
	
	@Override
	public void handlerMessage(LTEHeader header, ResultBean body) {

	}
	
}
