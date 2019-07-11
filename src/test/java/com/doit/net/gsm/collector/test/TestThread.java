package com.doit.net.gsm.collector.test;


import java.util.Scanner;

public class TestThread extends Thread {
	
	Scanner sc = new Scanner(System.in); 

	public TestThread() {
		setName("4G-Test-Thread");
	}

	@Override
	public void run() {
		
		while(true){
			String tag = sc.nextLine();
			try {
				testProcess(tag);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("参数错误");
			}
		}
	}

	private void testProcess(String tag) throws Exception{
			/*
		if(tag.startsWith("get_scan")){
			ClientManager.getScan("1","38098,37900,39150,38950","1","");
		}else if(tag.startsWith("get_param")){
			ClientManager.getParam();
		}else if(tag.startsWith("rf_open")){
			ClientManager.setRfOpen();
		}else if(tag.startsWith("rf_close")){
			ClientManager.setRfClose();
		}else if(tag.startsWith("rpt_open")){
			ClientManager.setRptOpen();
		}else if(tag.startsWith("reboot")){
			ClientManager.reboot();
		}else if(tag.startsWith("reset")){
			ClientManager.reset();
		}else if(tag.startsWith("rpt_mode")){
			ClientManager.setCollectMode(tag.split(" ")[1]);
		}else if(tag.startsWith("set_power")){
			ClientManager.setPower(Integer.parseInt(tag.split(" ")[1]),Integer.parseInt(tag.split(" ")[2]));
		}else if(tag.startsWith("redirect")){
			ClientManager.setRedirect(3, null, null, null);
		}else if(tag.startsWith("tac")){
			ClientManager.changeTac();
		}else if(tag.startsWith("set_pci")){
			ClientManager.setParam(null, null, null, Integer.parseInt(tag.split(" ")[1]), null, null);
		}else if(tag.startsWith("set_band")){
			ClientManager.setParam(null, null, null, null, Integer.parseInt(tag.split(" ")[1]), null);
		}
		*/
	}

}
