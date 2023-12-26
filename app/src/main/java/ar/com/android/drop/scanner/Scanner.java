package ar.com.android.drop.scanner;

import java.util.LinkedList;
import java.util.StringTokenizer;

import ar.com.android.drop.domine.Message;
import ar.com.android.drop.domine.Pc;
import ar.com.android.drop.exceptions.SendThroughtSocketException;
import ar.com.android.drop.services.SendService;
import ar.com.android.drop.services.PcService;

public class Scanner {

	public Scanner(PcService pcService, SendService sendService){
		this.pcService = pcService;
		this.sendService = sendService;
	}

	private static LinkedList<Pc> pcs = new LinkedList<Pc>();
	
	private PcService pcService = null;
	private SendService sendService = null;

	public void startScanning() throws InterruptedException {

		String ipToScan = cleanIp(pcService.obtenerIpLocal());

		for (int i = 0; i < 255; i++) {

			String serverHostName = ipToScan + i;
			ThreadScanner threadTestIp = new ThreadScanner(serverHostName,
					pcs);
			threadTestIp.start();
		}

		Thread.sleep(7000);

		for (Pc pc : pcs) {
			if (!pc.getIp().equals(pcService.obtenerIpLocal())) {

				Message message = new Message(pcService.getPcLocal());
				message.setDestinationIp(pc.getIp());
				message.setCommand("who");

				try {
					sendService.sendMessage(message);
				} catch (SendThroughtSocketException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("omitiendo localhost...");
			}

		}

	}

	public String cleanIp(String ip) {
		String cleanIp = "";
		StringTokenizer token = new StringTokenizer(ip, ".");

		for (int i = 0; i < 3; i++) {
			cleanIp += token.nextToken() + ".";
		}
		return cleanIp;
	}

	public PcService getPcService() {
		return pcService;
	}

	public void setPcService(PcService pcService) {
		this.pcService = pcService;
	}

	public SendService getSendService() {
		return sendService;
	}

	public void setSendService(SendService sendService) {
		this.sendService = sendService;
	}

}
