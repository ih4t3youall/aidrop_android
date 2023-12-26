package ar.com.android.drop.scanner;

import java.net.Socket;
import java.util.LinkedList;

import ar.com.android.drop.domine.Pc;
import ar.com.android.drop.services.PcService;

public class ThreadEscanerPuertos extends Thread {


	public ThreadEscanerPuertos(PcService pcService){
		this.pcService = pcService;
	}
	private Pc pc = null;
	
	private PcService pcService = null;
	
	private LinkedList<Pc> filtered;
	
	public ThreadEscanerPuertos(Pc pc, LinkedList<Pc> filtered){
		
		this.filtered = filtered;
		this.pc = pc;

	}
	
	
	
	public void run(){
		
		try {
			Socket socket = new Socket(pc.getIp(),8123);
			System.out.println("el puerto esta abierto "+pc.getIp());
			filtered.add(pc);
		} catch (Exception e) {
			System.out.println("error al verificar el puerto de : "+pc.getIp());
			
		}
		
		}



	public PcService getPcService() {
		return pcService;
	}



	public void setPcService(PcService pcService) {
		this.pcService = pcService;
	}

	}
	

