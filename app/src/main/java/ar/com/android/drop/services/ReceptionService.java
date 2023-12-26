package ar.com.android.drop.services;

import ar.com.android.drop.exceptions.ReceiveThroughtServletException;
import ar.com.android.drop.threads.RecibirArchivo;
import ar.com.android.drop.threads.ReceiveMessage;

public class ReceptionService {

	
	public void startServerSocketObjects(){
		
		ReceiveMessage receiveMessage;
		try {
			receiveMessage = new ReceiveMessage();
		receiveMessage.start();
		new RecibirArchivo(new FileService()).start();
		} catch (ReceiveThroughtServletException e) {
			e.printStackTrace();
		}
	}
	
}
