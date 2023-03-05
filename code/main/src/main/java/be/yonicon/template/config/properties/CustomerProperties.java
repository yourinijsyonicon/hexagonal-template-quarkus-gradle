package be.yonicon.template.config.properties;

import io.smallrye.config.WithName;

public interface CustomerProperties {
    @WithName("backend")
    String getBackend();
}
