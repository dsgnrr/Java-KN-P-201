package step.learning.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import step.learning.ioc.services.hash.HashService;
import step.learning.ioc.services.hash.Md5HashService;
import step.learning.ioc.services.hash.Sha256HashService;
import step.learning.ioc.services.random.RandomService;
import step.learning.ioc.services.random.RandomServiceV1;

public class ConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        // основний метод, у якому здійснюються налаштування служб
        //bind(HashService.class).to(Md5HashService.class);
        //bind(HashService.class).to(Sha256HashService.class);
        bind(HashService.class).annotatedWith(Names.named("Digest-Hash")).to(Sha256HashService.class);
        bind(HashService.class).annotatedWith(Names.named("DSA-Hash")).to(Md5HashService.class);
    }
    private RandomService randomService;
    @Provides
    private RandomService randomService(){
        /*
        Провайдери - методи, які дозволяють більш гнучко керувати процесом
        інжекції залежностей. Зв'язується за типом даних - включається для
        кожної точки інжекції з типом RandomService. У якості інжектованого
        об'єкту буде те, що поверне даний метод. Назва методу - не має значення
        тільки тип повернення
         */
        if(randomService==null){
            randomService=new RandomServiceV1();
            randomService.seed("0"); // як приклад того що самого конструктора недостатньо
        }
        return randomService;
    }
}
