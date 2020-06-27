package io.quarkus.smallrye.reactivemessaging.camel;

import java.util.Random;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class CamelFileProducerBean {

    private Random random = new Random();

    @Outgoing("files-out")
    public Multi<String> generate() {
        return Multi.createFrom().range(1, 11)
                .map(x -> random.nextDouble())
                .map(p -> Double.toString(p));
    }

}
