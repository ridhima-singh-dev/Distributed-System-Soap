SOAP Web Serice Implementation

Introduction
This practical focus is on adapting the LifeCo system to use SOAP web service for communication between components. The goal is to create a distributed system where each component can run in  a separate Docker container. Discovery of services is implemented using jmDNS a Java Multicast DNS implementation.

Components
core: Contains shared code, including distributed object interfaces and data classes.
auldfellas, dodgygeezers, girlsallowed: Quotation services providing quotes based on client information.
broker: The broker service get quotations from Quoatation services using soap web service.
client: Client services request quotations from the broker.

Module Integration: 
The core code serves as the foundation for the entire system. It defines the QuotationService interface, which specifies methods for obtaining quotations. Each quotation service module auldfellas, dodgygeezers, girlsallowed implements this interface. This step establishes a common contract for all services to adhere to when providing quotations.

Broker Service: 
The broker service acts as a coordinator between clients and quotation services. It utilizes SOAP web service for communication. Endpoint.publish() method is used to publish web services and make them accessible. WSDL (Web Services Description Language) is facilitated to describe web service.

Client Interaction: 
Clients interact with the system through the broker service via SOAP. They request quotations by invoking methods on the broker, providing specific client profiles as input. The broker, in turn, uses SOAP web service to communicate with the appropriate quotation services auldfellas, dodgygeezers, girlsallowed to get quotations. This step encapsulates the client from the complexity of communicating with multiple services, simplifying the client's interaction with the system.

Multicast DNS-based Service Discovery:
This distributed application uses jmDNS for service discovery.JmDNS advertised and discovered services within the local network. I Learned to implement mechanisms for Broker Services to discover and maintain a list of available Quotation Services.

In Task3 to run the application with program arguments, I have used below command
mvn exec:java -pl broker -Dexec.args="http://localhost:9001/quotations?wsdl http://localhost:9002/quotations?wsdl http://localhost:9003/quotations?wsdl"
