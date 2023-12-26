package ar.com.android.drop.domine;

import java.io.Serializable;

public class Pc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ip;
	private String pcName;

	
	public Pc(String ip){
		this.ip = ip;
	}
	public Pc(String ip, String pcName){
		this.ip = ip;
		this.pcName = pcName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPcName() {
		return pcName;
	}

	public void setPcName(String pcName) {
		this.pcName = pcName;
	}
	
	

}
