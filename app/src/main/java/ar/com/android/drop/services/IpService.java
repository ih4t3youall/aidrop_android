package ar.com.android.drop.services;

import android.util.Log;
import android.widget.TextView;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import ar.com.android.drop.MainActivity;
import ar.com.android.drop.R;
import ar.com.android.drop.devHelpers.ScreenLog;
import ar.com.android.drop.domine.Pc;
import ar.com.android.drop.exceptions.ServiceException;


public class IpService extends Thread{

	private String hostAddress ;
	private String hostName ;
	private ScreenLog screenLog;
	private MainActivity mainActivity;
	private PcService pcService;
	public IpService(MainActivity mainActivity, ScreenLog screenLog, PcService pcService){
		this.mainActivity = mainActivity;
		this.screenLog = screenLog;
		this.pcService = pcService;
	}
	public void run() {
		if (hostAddress == null){
			InetAddress localHost = null;
			try {
				localHost = InetAddress.getLocalHost();
			} catch (UnknownHostException e1) {
				Log.d("loginfo","exploto por exception");
				//throw new ServiceException("Error al obtener la ip" ,e1);
			}
			hostAddress = localHost.getHostAddress();
			hostName = localHost.getHostName();
		}
		Pc pc = new Pc(getLocalIpAddress());
		pc.setPcName(hostName);
		pcService.setPcLocal(pc);
		screenLog.addText(getLocalIpAddress());
		mainActivity.myPc = pc;

	}

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

	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
}
