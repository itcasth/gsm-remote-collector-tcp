package com.doit.net.gsm.collector.handler;

import com.doit.net.gsm.collector.bean.ReportIndBean;
import com.doit.net.gsm.collector.server.ClientManager;
import com.doit.net.gsm.collector.server.GSMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doit.net.gsm.collector.base.LTEHeader;

public class UeidReportHandler extends ILTEHandler<ReportIndBean> {
	
	
	private final static Logger log = LoggerFactory.getLogger(UeidReportHandler.class);

	private byte[] bodyData;
	
	@Override
	public byte[] getResponseData() {
		return bodyData;
	}
	
	
	@Override
	public void handlerMessage(LTEHeader header, ReportIndBean body) {
		try {
			log.debug("IMSI:"+body.imsi+",IMEI:"+body.imei);
			ClientManager.getClient(header.getRemoteAddr()).getClient().sentReportRsp(body.msgId,true);
		} catch (GSMException e) {
			e.printStackTrace();
		}
	}

}
