/*
 * Copyright (C) 2013-2016 ShangHai DoIT Network
 * All rights reserved.
 * ShangHai DoIt Network
 */

package com.doit.net.gsm.collector.server;

import java.util.ArrayList;
import java.util.List;

public class ClientManager {

	private static List<GSMSocketWorkThread> clients = new ArrayList<GSMSocketWorkThread>();
	
	private static int msgId;
	
	public static void setId(int id){
		msgId = id+1;
	}
	
	public static int getId(){
		return msgId;
	}
	
	public static int getClientSize(){
		return clients.size();
	}
	
	public static GSMSocketWorkThread getClient(String ip) throws GSMException {
		if(clients == null || clients.size()<=0){
			throw new GSMException();
		}
		for(GSMSocketWorkThread client:clients){
			if(client.getSocket().getInetAddress().getHostAddress().equals(ip)){
				return client;
			}
		}
		return null;
	}

	public static GsmSocketClient getByIp(String ip) throws GSMException{
		return getClient(ip).getClient();
	}
	
	
	/**
	 * 是否已连接
	 * @return
	 */
	public static boolean isConnected(String ip){
		try {
			GSMSocketWorkThread tempClient = getClient(ip);
			return tempClient!=null && tempClient.getSocket() != null && tempClient.getSocket().isConnected();
		} catch (GSMException e) {
		}
		return false;
	}
	
	public static void addClient(GSMSocketWorkThread client) throws GSMException {
		//删除IP地址相同的
		for(int i=0;i<clients.size();i++){
			GSMSocketWorkThread c = clients.get(i);
			if(c.getSocket().getInetAddress().getHostAddress().equals(client.getSocket().getInetAddress().getHostAddress())){
				c.exit();
				clients.remove(i);
				break;
			}
		}
		ClientManager.clients.add(client);
	}
	

}
