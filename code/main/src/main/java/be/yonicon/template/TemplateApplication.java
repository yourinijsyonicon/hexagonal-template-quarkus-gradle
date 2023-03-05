package be.yonicon.template;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@QuarkusMain
public class TemplateApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateApplication.class);

    public static void main(String... args) {
        LOGGER.info("Running Main");
        Quarkus.run(args);
    }
}
