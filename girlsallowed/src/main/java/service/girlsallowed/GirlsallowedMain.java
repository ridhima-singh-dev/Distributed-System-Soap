package service.girlsallowed;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import javax.xml.ws.Endpoint;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import service.auldfellas.AFQService;

public class GirlsallowedMain {
	
	public static void main(String[] args) {
      //Endpoint.publish("http://0.0.0.0:9003/quotation", new GAQService());
		String host="localhost";
		if (args.length>0){
			host=args[0];
		}

		try {
			Endpoint endpoint = Endpoint.create(new GAQService());
			HttpServer server = HttpServer.create(new InetSocketAddress(9003), 5);
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
			ServiceInfo serviceInfo = ServiceInfo.create("_quote._tcp.local.", "gaqs", 9003, "path=http://girlsallowed:9003/quotation?wsdl");
			jmdns.registerService(serviceInfo);
			System.out.println("Inside host " + jmdns.getInetAddress());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}
