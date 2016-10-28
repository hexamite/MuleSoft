package com.hexamite.connector.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.zeromq.ZMQ;
import com.hexamite.connector.Hx19Connector;
import sun.misc.BASE64Decoder;

public class hx19ConnectorReal implements Hx19Connector{

	@Override
	public String postMessage(String serverName, String message){
		
		String response = null;
		
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket toSerial = context.socket(ZMQ.PUB);
		
		try{
			toSerial.connect("tcp://" + serverName + ":5556");
			toSerial.send(message);
			response = toSerial.recvStr();
		} finally {
			toSerial.close();
			context.term();
		}
		return response;
	}

	@Override
	public String getMonitor(String serverName) {
		String response = null;
		
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
		
		try {
			context = ZMQ.context(1);
			subscriber = context.socket(ZMQ.SUB);
			subscriber.connect("tcp://" + serverName + ":5556");
			subscriber.subscribe(ZMQ.SUBSCRIPTION_ALL);
			
			response = subscriber.recvStr();
		} finally {
			subscriber.close();
			context.term();
		}
		
		return response;
	}

	@Override
	public String postUpload(String serverName, String base64File) {
		BASE64Decoder decoder = new BASE64Decoder();
		
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket toSerial = context.socket(ZMQ.PUB);
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(decoder.decodeBuffer(base64File))));
			String line = reader.readLine();
			while(line != null) {
				toSerial.send(line);
				line = reader.readLine();
			}
		} catch (IOException e) {
			return "Error in writting file";
		} finally {
			toSerial.close();
		    context.term();
		}
		
		return "Write completed";
	}
}
