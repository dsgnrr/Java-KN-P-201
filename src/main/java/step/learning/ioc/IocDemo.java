package step.learning.ioc;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import step.learning.ioc.services.hash.HashService;
import step.learning.ioc.services.hash.Md5HashService;
import step.learning.ioc.services.random.RandomService;


public class IocDemo {

    private final HashService digestHashService;
    private final HashService dsaHashService;
    private final RandomService randomService;

    @Inject
    public IocDemo(@Named("Digest-Hash") HashService digestHashService,
                   @Named("DSA-Hash") HashService dsaHashService,
    RandomService randomService) {
        this.digestHashService = digestHashService;
        this.dsaHashService = dsaHashService;
        this.randomService = randomService;
    }

    public void run() {
        System.out.println("IoC Demo");
        System.out.println("SHA-256: " + digestHashService.hash("IoC Demo"));
        System.out.println("MD5: " + dsaHashService.hash("IoC Demo"));
        System.out.println("Random: "+ randomService.randomHex(5));
//        System.out.println((hashService.hashCode() + " " + hashService2.hashCode()));
//        System.out.println(hashService128.hash("IoC Demo"));
    }
}
/*
//Інжекція через поля - не найкращий варіант, оскільки інжекція у формі змінних
    //(не констант), що потенційно може призвести до іх віпадкового пошкодження (переприсвоення)
   // @Inject //Інжекція від класу (Md5HashService) -
    //private Md5HashService md5HashService;//Знвходится автоматично, не потребує конфігурації

   // @Inject //Інжекція залежності від інтерфейсу(HashService)
  //  private HashService hashService; //вимагає зв'язування(bind) його з класом кофігурації
    //Інжкція через конструктор - дозволяє зберегти посилання на служби як константи
    private final HashService hashService;
    @Inject// !!! Не забувати перед інжекційним конструктором
    public IocDemo(HashService hashService){
        this.hashService = hashService;
    }
    //чи можливий змішанний варіант? Інжекції через костркутор та через поля
    @Inject
    private HashService hashService2; // Так це можливо. Це інша (друга) точка інжекції,
    // яка сформує окремий запит на інжецію. Чи отримає вона новий об'єкт, чи раніше створенний-
    // залежить від способу реестрації звлежності(Singelton чи [Default]-transient)



    * Іменовані залежності розділяють декілька різних об'єктів однакового інтерфейсу
    * (клвсу). Це може бути корсним для String (DbPrefix, CinnectionString, ConfigeFilename, UploadDir)
    * А також для гешів з різною розрядністю(або різного призначення)

    @Inject @Named("Hash128") private HashService hashService128;
 */

/*
IoC - Inversion of Control (інверсія управління)
Архітектурний патерн - шаблон побудови (організації) коду згідно з яким
управління створенням об'єктів делегується окремому модулю, який часто
назиівають інжектором або контейнером служб.

Class1 { DbContext = new() .... } => Class 1 { @Inject DbContext .... }
Class2 { DbContext = new() .... }    Class 2 { @Inject DbContext .... }
Class3 { Class2 = new()         }    Class 3 { @Inject Class2         }

Через це IoC також називають DI (Dependency Injection !! не плутати з DIP
Dependency Inversion Principle)

Наявність IoC змінює підходи до організації структури у тому числі структури запуску
поділяючи її на 2 етапи: налаштування служб (інжектора) та резолюція (Resolve)
об'єктів - побудови "дерева" залежностей і розв'язання його (які створювати
першими, які від них залежать і т.д.)

Поширені системи IoC - Spring, Guice
На прикладі Guice:
- встановлюємо - знаходимо залежність або JAR, додаємо до проєкту
- оголошуємо клас, який буде відповідати за налаштуванням інжектора (
    реєструвати служби), це нащадок AbstractModule - ConfigModule
- створюємо сам інжектор та передаємо йому клас налаштувань ( див. App:: main )
 */
