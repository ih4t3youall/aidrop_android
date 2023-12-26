package ar.com.android.drop.exceptions;

import java.net.UnknownHostException;

public class ServiceException extends Throwable {

	public ServiceException(String string, UnknownHostException e){
		System.out.println("Error getting the Ip Local");
		e.printStackTrace();
	}
	
}
