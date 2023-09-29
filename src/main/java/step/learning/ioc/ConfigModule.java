package step.learning.ioc;

import com.google.inject.AbstractModule;
import step.learning.ioc.services.hash.HashService;
import step.learning.ioc.services.hash.Md5HashService;

public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        // основний метод, у якому здійснюються налаштування служб
        bind(HashService.class).to(Md5HashService.class);
    }
}
