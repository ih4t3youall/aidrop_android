package ar.com.android.drop.services;

import java.net.InetAddress;
import java.net.UnknownHostException;

import ar.com.android.drop.domine.Pc;
import ar.com.android.drop.exceptions.ServiceException;


public class IpService {

	private String hostAddress ;
	private String hostName ;
	
	public Pc getLocalIp() throws ServiceException{
		if (hostAddress == null){
		InetAddress localHost = null;
		try {
			localHost = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {

			throw new ServiceException("Error al obtener la ip" ,e1);
		}
		hostAddress = localHost.getHostAddress();
		hostName = localHost.getHostName();
		}
		Pc pc = new Pc(hostAddress);
		pc.setPcName(hostName);
		return pc;
		
	}
	
}
