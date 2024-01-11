package service.broker;

import java.net.URL;
import java.util.LinkedList;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;

/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */
@WebService(name = "BrokerService", targetNamespace = "http://core.service/", serviceName = "BrokerService")
@SOAPBinding(style = Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class LocalBrokerService implements BrokerService , ServiceListener {

	
//	 List<String> urls = new ArrayList<String>(){{
//	  add("http://0.0.0.0:9001/quotations?wsdl");
//	  add("http://0.0.0.0:9002/quotations?wsdl");
//	  add("http://0.0.0.0:9003/quotations?wsdl"); }};



	static LinkedList<String> urlList = new LinkedList<String>();

//	public LocalBrokerService(List<String> quoataionUrlList) {
//		 this.urlList.addAll(quoataionUrlList);
//		 }


	public LocalBrokerService() {
	}

	@WebMethod
	public LinkedList<Quotation> getQuotations(ClientInfo info) {
		LinkedList<Quotation> quotations = new LinkedList<Quotation>();
		for (String url : urlList) {

			try {
				URL wsdlUrl = new URL(url);
				QName serviceName = new QName("http://core.service/", "QuotationService");
				Service service = Service.create(wsdlUrl, serviceName);
				QName portName = new QName("http://core.service/", "QuotationServicePort");
				QuotationService quotationService = service.getPort(portName, QuotationService.class);
				quotations.add(quotationService.generateQuotation(info));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return quotations;

	}

	@Override
	public void serviceAdded(ServiceEvent serviceEvent) {
	}

	@Override
	public void serviceRemoved(ServiceEvent serviceEvent) {
	}

	@Override
	public void serviceResolved(ServiceEvent event) {
		//call add url function to add online services
		String path=event.getInfo().getPropertyString("path");
		try {
			if(path!=null) {
				this.urlList.add(path);
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}

}
