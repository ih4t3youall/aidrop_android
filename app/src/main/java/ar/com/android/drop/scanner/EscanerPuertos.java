package ar.com.android.drop.scanner;

import java.util.Iterator;
import java.util.LinkedList;

import ar.com.android.drop.domine.Pc;
import ar.com.android.drop.services.PcService;

public class EscanerPuertos {

	public EscanerPuertos(PcService pcService){
		this.pcService = pcService;
	}

	private PcService pcService = null;
	
	public void inicioEscanner(){
		
		Iterator<Pc> iterator = pcService.getListExternalPc().iterator();
		
		LinkedList<Pc> filtradas = new LinkedList<Pc>();
		
		while(iterator.hasNext()){
			Pc pc = iterator.next();

		ThreadEscanerPuertos thread =new  ThreadEscanerPuertos(pc,filtradas);
		thread.start();
		}
		
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		pcService.setExternalPc(filtradas);
	}

	public PcService getPcService() {
		return pcService;
	}

	public void setPcService(PcService pcService) {
		this.pcService = pcService;
	}
	
}
