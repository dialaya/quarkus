package io.quarkus.smallrye.reactivemessaging.camel;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/filename")
public class TestResource {

    @Inject
    CamelFileConsumerBean fileConsumerBean;

    @GET
    public String getFilename() {
        return fileConsumerBean.getFilename();
    }

}
