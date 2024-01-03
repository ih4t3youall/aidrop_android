package ar.com.android.drop.services;

import java.io.Serializable;
import java.util.LinkedList;

import ar.com.android.drop.domine.Pc;
import ar.com.android.drop.exceptions.FileNotExist;
import ar.com.android.drop.persistence.Persistence;

public class PcService {

	private Pc pcLocal = new Pc("0");
	private LinkedList<Pc> externalPc = new LinkedList<Pc>();

	
	public void setExternalPc(LinkedList<Pc> externalPc){
		this.externalPc = externalPc;
	}
	
	
	public Pc getPcLocal() {
		return pcLocal;
	}

	public void setIpLocalhost(String ip) {
		pcLocal.setIp(ip);
	}

	public void setPcLocal(Pc pc){
		this.pcLocal = pc;
	}

	public void setNombreLocal(String nombre) {

		pcLocal.setPcName(nombre);

	}

	public String obtenerIpLocal() {
		return this.pcLocal.getIp();
	}

	public String getLocalPcName() {
		return this.pcLocal.getPcName();
	}

	public void addExternalPc(Pc pc) {

		boolean f = true;
		for (Pc iterPc : externalPc) {

			if (iterPc.getIp().equals(pc.getIp())) {
				//para actualizar cuando cambia por archivo->Editar local, asi se actualiza la lista.
				iterPc.setPcName(pc.getPcName());
				f = false;
			}

		}

		if (f) {

			this.externalPc.add(pc);
		}
	}

	public LinkedList<Pc> getListExternalPc() {
		return this.externalPc;
	}

	public void cleanList() {
		externalPc.clear();
	}

}
