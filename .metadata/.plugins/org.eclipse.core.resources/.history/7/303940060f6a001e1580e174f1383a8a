package client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import service.core.ClientInfo;
import service.core.QuotationService;

public class WSDLServiceListner implements ServiceListener {

	@Override
	public void serviceAdded(ServiceEvent event) {
		System.out.println("Service added: " + event.getInfo());

	}

	@Override
	public void serviceRemoved(ServiceEvent event) {
		System.out.println("Service removed: " + event.getInfo());

	}

	@Override
	public void serviceResolved(ServiceEvent event) {
		String path = event.getInfo().getPropertyString("path");
		if(path != null) ConnectToServicePath(path);

	}

	private void ConnectToServicePath(String url) {
		try {
			URL	wsdlUrl = new URL(url);
		
		 QName serviceName = new QName("http://core.service/", "BrokerService");
	        Service service = Service.create(wsdlUrl, serviceName);
	        QName portName = new QName("http://core.service/", "BrokerServicePort");
	        QuotationService quotationService = service.getPort(portName, QuotationService.class);
	        //System.out.println("Quotation is " + quotationService.generateQuotation(new ClientInfo("Niki Collier", ClientInfo.FEMALE, 49, 1.5494, 80, false, false)));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
