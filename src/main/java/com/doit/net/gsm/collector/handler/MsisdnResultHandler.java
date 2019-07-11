package com.doit.net.gsm.collector.handler;

import com.doit.net.gsm.collector.base.LTEHeader;
import com.doit.net.gsm.collector.bean.MsisdnReportBean;
import com.doit.net.gsm.collector.bean.ReportResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsisdnResultHandler extends ILTEHandler<MsisdnReportBean> {


	private final static Logger log = LoggerFactory.getLogger(MsisdnResultHandler.class);

	private byte[] bodyData;

	@Override
	public byte[] getResponseData() {
		return bodyData;
	}


	@Override
	public void handlerMessage(LTEHeader header, MsisdnReportBean body) {

	}

}
