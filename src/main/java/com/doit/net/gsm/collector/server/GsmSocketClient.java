/*
 * Copyright (C) 2013-2016 ShangHai DoIT Network
 * All rights reserved.
 * ShangHai DoIt Network
 */

package com.doit.net.gsm.collector.server;

import com.doit.net.gsm.collector.bean.CfgReqBean;
import com.doit.net.gsm.collector.bean.ReportResultBean;
import com.doit.net.gsm.collector.constants.GsmMessageType;

import java.net.Socket;

/**
 * @author wiker <a href="mailto:wikeryong@qq.com">wikeryong@qq.com</a>
 * @date 2017-03-17 09:39
 */
public class GsmSocketClient extends BaseSocket {

    public GsmSocketClient(Socket socket) {
        super(socket);
    }

    public void sentSetParam(CfgReqBean bean) throws GSMException{
        send(GsmMessageType.SS_DEV_CFG_REQ.ordinal(),bean);
    }

    public void sentReportRsp(int msgId,boolean isOk) throws GSMException{
        ReportResultBean bean = new ReportResultBean();
        bean.msgId = msgId;
        bean.result = isOk?1:0;
        send(GsmMessageType.SS_DEV_IMSI_RSP.ordinal(),bean);
    }
}
