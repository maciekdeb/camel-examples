package maciejdebowski;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.cxf.DataFormat;

import javax.xml.namespace.QName;

public class MyRouteBuilder extends RouteBuilder {

    private CxfEndpoint cxfEndpoint = createCxfEndpoint();

    public void configure() {

        from("file:src/data")
            .log("${body}")
                .to(cxfEndpoint)
                    .log("${body}");

    }

    private CxfEndpoint createCxfEndpoint(){
        CamelContext camelContext = getContext();
        CxfEndpoint cxfEndpoint = new CxfEndpoint();
        cxfEndpoint.setAddress("http://localhost:8008/");
        cxfEndpoint.setWsdlURL("http://localhost:8008/");
        cxfEndpoint.setServiceName(new QName("http://example.com/sample.wsdl", "my_dispatcherService"));
        cxfEndpoint.setPortName(new QName("http://example.com/sample.wsdl", "my_dispatcher"));
        cxfEndpoint.setDataFormat(DataFormat.MESSAGE);
        cxfEndpoint.setCamelContext(camelContext);
        return cxfEndpoint;
    }

}
