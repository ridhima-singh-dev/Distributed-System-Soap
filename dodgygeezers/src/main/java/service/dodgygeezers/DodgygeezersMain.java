package service.dodgygeezers;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import javax.xml.ws.Endpoint;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import service.auldfellas.AFQService;

public class DodgygeezersMain {
	
	public static void main(String[] args) {
       // Endpoint.publish("http://0.0.0.0:9002/quotation", new DGQService());
		String host="localhost";
		if (args.length>0){
			host=args[0];
		}
		try {
			Endpoint endpoint = Endpoint.create(new DGQService());
			HttpServer server = HttpServer.create(new InetSocketAddress(9002), 5);
			server.setExecutor(Executors.newFixedThreadPool(5));
			HttpContext context = server.createContext("/quotation");
			endpoint.publish(context);
			server.start();
			
			Thread.sleep(6000);
			Advertise(host);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	private static void Advertise(String host) {
		try {
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
			ServiceInfo serviceInfo = ServiceInfo.create("_quote._tcp.local.", "dgqs", 9002, "path=http://dodgygeezers:9002/quotation?wsdl");
			jmdns.registerService(serviceInfo);
			System.out.println("Inside host " + jmdns.getInetAddress());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}
