package com.doit.net.gsm.collector.test;
import com.doit.net.gsm.collector.handler.LTEHandlerManager;
import com.doit.net.gsm.collector.server.GsmSocketServer;
import org.slf4j.impl.SimpleLogger;

import com.doit.net.gsm.collector.server.GSMException;

import static com.doit.net.gsm.collector.handler.ILTEHandler.LOCAL_TEST;

public class ServerTest {

	public static void main(String[] args) {
		try {
			System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE");
			System.setProperty(SimpleLogger.SHOW_SHORT_LOG_NAME_KEY, "true");
			LTEHandlerManager.initHandler();
			LOCAL_TEST = true;
			GsmSocketServer.start(10080);
			new TestThread().start();
		} catch (GSMException e) {
			e.printStackTrace();
		}
	}
}
