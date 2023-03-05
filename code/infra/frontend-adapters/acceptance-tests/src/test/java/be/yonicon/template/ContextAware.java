package be.yonicon.template;

import org.junit.jupiter.api.AfterEach;

public interface ContextAware {
    BlackboxApplicationContext getContext();

    @AfterEach
    default void afterEach() {
        getContext().clear();
    }
}
