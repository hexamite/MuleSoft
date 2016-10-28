package com.hexamite.connector.impl;

import com.hexamite.connector.Hx19Connector;

public class hx19ConnectorMock implements Hx19Connector{
	
	@Override
	public String postMessage(String serverName, String message) {
		return "R19:X40 C6650 U40 | X40 C6655 U40 | X40 C6652 U40 | X40 C6659 U40 | X40";
	}

	@Override
	public String getMonitor(String serverName) {
		return "R19:X40 C6650 U40 | X40 C6655 U40 | X40 C6652 U40 | X40 C6659 U40 | X40";
	}

	@Override
	public String postUpload(String serverName, String base64File) {
		return "Upload OK";
	}
	
}
