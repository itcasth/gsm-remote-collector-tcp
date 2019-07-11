package com.doit.net.gsm.collector.handler;



import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Socket;

import com.doit.net.gsm.collector.base.LTEHeader;
import com.doit.net.gsm.collector.base.ProtBody;
import com.doit.net.gsm.collector.utils.MiniArrayUtils;
import com.doit.net.gsm.collector.utils.ProtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.doit.net.gsm.collector.utils.MiniStringUtils;

/**
 * Created by 维 on 2016-03-13.
 */
public abstract class ILTEHandler<T extends ProtBody> {

	private final static Logger log = LoggerFactory.getLogger(ILTEHandler.class);

    public LTEHeader mHeader;
    public Socket mSocket;
    
    protected byte[] bodyDatas;

    public ILTEHandler() {
    }
    
    protected Socket getSocket() {
		return mSocket;
	}

    public void handler(LTEHeader header,Socket socket,byte[] bodyData){
        this.mHeader = header;
        this.mSocket = socket;
        bodyDatas = MiniArrayUtils.addAll(header.data, bodyData);

        handlerMessage(mHeader,bodyData);
        handlerDecode(mHeader, bodyData);

    
    }
    public static boolean LOCAL_TEST = false;
    /**
     * 接收到的消息，需要自己解码
     * @param header
     * @param bodyData
     */
    public void handlerMessage(LTEHeader header, byte[] bodyData){}

    private void handlerDecode(LTEHeader header, byte[] bodyData) {
        //通过范型的方式直接解码
        try {
            Type genType = null;
            if(LOCAL_TEST) {
                genType = getClass().getGenericSuperclass();
            }else{
                genType = this.getClass().getSuperclass().getGenericSuperclass();
            }
            if(genType == null) return;

            if(genType instanceof ParameterizedType) {
                Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                if(params == null || params.length <=0) return;

                Class<T> respClass = (Class) params[0];
                T respBody = respClass.newInstance();
                respBody.data = bodyData;
                respBody.decode();
                handlerMessage(header,respBody);
            }


        } catch (Exception e) {
        	log.error("Decode body error", e);
        }
    }
    
    public abstract byte[] getResponseData();

    public void finish(){}
    
    /**
     * 接收到的消息，已解码
     * @param header
     * @param body
     */
    public void handlerMessage(LTEHeader header, T body){}

    public String getParam(String content,String name){
    	content = content.replaceAll("\t", ";").replaceAll("\r\n", ";");
    	content = ";"+content;
    	return ProtUtils.getMidStr(content, ";"+name+":", ";");
    }

    public String getParam(String content,String name,String defVal){
    	String val = getParam(content,name);
    	if(MiniStringUtils.isBlank(val)){
    		return defVal;
    	}
    	return val;
    }
}
