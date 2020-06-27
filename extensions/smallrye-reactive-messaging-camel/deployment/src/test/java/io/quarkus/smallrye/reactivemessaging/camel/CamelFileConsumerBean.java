package io.quarkus.smallrye.reactivemessaging.camel;

import java.io.File;

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.component.file.GenericFile;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class CamelFileConsumerBean {

    volatile String filename;

    @Incoming("files")
    public void consume(GenericFile<File> gf) {
        File file = gf.getFile();
        filename = file.getName();
    }

    public String getFilename() {
        return filename;
    }
}
