package service.broker;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import javax.jmdns.JmDNS;
import javax.xml.ws.Endpoint;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import service.core.ClientInfo;

public class BrokerMain {

	private static ClientInfo info;

	public static void main(String[] args) {

		/*List<String> quotationUrls = new ArrayList<>();

		for (String arg : args) {
			quotationUrls.add(arg);
		}*/
		LocalBrokerService localBrokerService = new LocalBrokerService();

		String host = "localhost";
		if (args.length > 0) {
			host = args[0];
		}
		 try {
	            Endpoint endpoint = Endpoint.create(new LocalBrokerService());
	            HttpServer server = HttpServer.create(new InetSocketAddress(9000), 5);
	            server.setExecutor(Executors.newFixedThreadPool(5));
	            HttpContext context = server.createContext("/broker");
	            endpoint.publish(context);
	            server.start();
	            //Create a JmDNS instance
	            JmDNS jmdns = JmDNS.create(host);
	            System.out.println("on host " + jmdns.getInetAddress());
	            // Add service listener
	            jmdns.addServiceListener("_quote._tcp.local.", new LocalBrokerService());
	            System.out.println("broker is running");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
}