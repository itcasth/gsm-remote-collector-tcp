package com.doit.net.gsm.collector.handler;

import com.doit.net.gsm.collector.bean.CfgReqBean;
import com.doit.net.gsm.collector.bean.VersionBean;
import com.doit.net.gsm.collector.server.ClientManager;
import com.doit.net.gsm.collector.server.GSMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doit.net.gsm.collector.base.LTEHeader;

public class VersionIndHandler extends ILTEHandler<VersionBean> {
	
	
	private final static Logger log = LoggerFactory.getLogger(VersionIndHandler.class);

	private byte[] bodyData;
	
	
	@Override
	public byte[] getResponseData() {
		return bodyData;
	}
	
	


	@Override
	public void handlerMessage(LTEHeader header, VersionBean body) {
		log.debug(body.version);
		CfgReqBean bean = new CfgReqBean();
		bean.cfgCmd = 0;
		try {
			ClientManager.getClient(header.getRemoteAddr()).getClient().sentSetParam(bean);
		} catch (GSMException e) {
			e.printStackTrace();
		}
	}
}
