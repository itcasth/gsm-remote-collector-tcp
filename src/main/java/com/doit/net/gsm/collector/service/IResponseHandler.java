package com.doit.net.gsm.collector.service;

import com.doit.net.gsm.collector.base.LTEHeader;
import com.doit.net.gsm.collector.base.ProtBody;

public interface IResponseHandler {

	void response(LTEHeader header,ProtBody resp);
}
