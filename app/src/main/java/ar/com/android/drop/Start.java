package ar.com.android.drop;

import java.io.IOException;


import ar.com.android.drop.domine.Pc;
import ar.com.android.drop.exceptions.ServiceException;
import ar.com.android.drop.services.IpService;
import ar.com.android.drop.services.PcService;
import ar.com.android.drop.services.ReceptionService;
import ar.com.android.drop.views.MenuPrincipal;

public class Start {


	public Start(IpService ipService, ReceptionService receptionService, PcService pcService){
		this.ipService = ipService;
		this.receptionService = receptionService;
		this.pcService = pcService;

	}

	private IpService ipService = null;
	private ReceptionService receptionService = null;
	private PcService pcService = null;

	public Start() {
		Pc pc = null;
		try {
			pc = ipService.getLocalIp();
			if (pcService.obtenerIpLocal().equals("0")){
				pcService.setIpLocalhost(pc.getIp());
			}
			pcService.setNombreLocal(pc.getPcName());

			
		} catch (ServiceException e) {
			System.out.println("Error al obtener la ip local, verifique su conexion a internet");
			System.exit(0);
		}
			
		
		receptionService.startServerSocketObjects();
		
		try {
			MenuPrincipal menuPrincipal = new MenuPrincipal();
		} catch (IOException e) {
			
			System.out.println("error jodete");
		}


	}

	public IpService getIpService() {
		return ipService;
	}

	public void setIpService(IpService ipService) {
		this.ipService = ipService;
	}

	public ReceptionService getReceptionService() {
		return receptionService;
	}

	public void setReceptionService(ReceptionService recepcionService) {
		this.receptionService = recepcionService;
	}

}
