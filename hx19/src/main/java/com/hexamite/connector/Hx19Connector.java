package com.hexamite.connector;

public interface Hx19Connector {
	public String postMessage(String serverName, String message);
	public String getMonitor(String serverName);
	public String postUpload(String serverName, String base64File);
}
