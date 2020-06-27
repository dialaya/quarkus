package io.quarkus.smallrye.reactivemessaging.camel.devmode;

import static org.awaitility.Awaitility.await;

import java.util.concurrent.TimeUnit;

import org.apache.camel.cdi.CdiCamelExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.smallrye.reactivemessaging.camel.CamelFileConsumerBean;
import io.quarkus.smallrye.reactivemessaging.camel.CamelFileProducerBean;
import io.quarkus.smallrye.reactivemessaging.camel.TestResource;
import io.quarkus.test.QuarkusDevModeTest;
import io.restassured.RestAssured;

public class CamelDevModeTest {

    @RegisterExtension
    static QuarkusDevModeTest TEST = new QuarkusDevModeTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
                    .addClasses(CdiCamelExtension.class, CamelFileConsumerBean.class, CamelFileProducerBean.class,
                            TestResource.class)
                    .addAsResource("application.properties"));

    @Test
    public void testCodeUpdate() {
        await()
                .atMost(1, TimeUnit.MINUTES)
                .until(() -> {
                    String value = RestAssured.get("/filename").asString();
                    return value != null;
                });

        TEST.modifySourceFile(CamelFileProducerBean.class, s -> s.replace("* 2", "* 3"));

        await()
                .atMost(1, TimeUnit.MINUTES)
                .until(() -> {
                    String value = RestAssured.get("/filename").asString();
                    return value != null;
                });

    }

}
