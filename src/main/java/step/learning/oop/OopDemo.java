package step.learning.oop;

import java.util.Date;

public class OopDemo {
    public void run() {
        Library library = new Library();
        try {
            library.add(new Book("D. Kunth", "Art of programming"));

            library.add(new Newspaper("Daily Mail", "2023-09-25"));

            library.add(new Journal("Science Journal", 5));

            library.add(new Book("Richter", "CLR via C#"));

            library.add(new Newspaper("Washington Post", "1971-01-01"));

            library.add(new Journal("Marvel Comics", 117));

            library.add(new Hologram("Hologram", "1920x1080"));

        } catch (Exception ex) {
            System.err.println("Literature creation error: " + ex.getMessage());
        }
        ////////////////////////////////////////////////////////////
        System.out.println("------------- PRINTABLE -------------");
        int index = 0;
        for (Literature literature : library.getPrintable()) {
            index++;
            System.out.printf("%s. %s%n", index, literature.getCard());
        }
        ////////////////////////////////////////////////////////////
        System.out.println("------------- NON PRINTABLE -------------");
        index = 0;
        for (Literature literature : library.getNonPrintable()) {
            index++;
            System.out.printf("%s. %s%n", index, literature.getCard());
        }
        ////////////////////////////////////////////////////////////
        System.out.println("------------- MULTIPLE -------------");
        index = 0;
        for (Literature literature : library.getMultiple()) {
            index++;
            IMultiple multipleLit = (IMultiple) literature;
            System.out.printf("%s. %s count: %s%n", index, literature.getCard(),multipleLit.getCount());
        }
        ////////////////////////////////////////////////////////////
        System.out.println("------------- SINGLE -------------");
        index = 0;
        for (Literature literature : library.getSingle()) {
            index++;
            System.out.printf("%s. %s%n", index, literature.getCard());
        }
        // region Classwork
        /* library.printAllCards();

        System.out.println("------------- COPYABLE -------------");
        library.printCopyable();

        System.out.println("------------- NON COPYABLE -------------");
        library.printNonCopyable();

        System.out.println("------------- PERIODIC -------------");
        library.printPeriodic();

        System.out.println("------------- NON PERIODIC -------------");
        library.printNonPeriodic();

        System.out.println("------------- PERIODIC2 -------------");
        library.printPeriodic2();
         */

        // endregion
    }
}
/*
Бібліотека
Моделюємо книгосховище (бібліотеку) у якому є каталог
Видання є різного типу: книги, газети, журнали, тощо
Кожен тип має однакову картку у каталозі, у якій відзначається тип видання

Абстрагування - створення абстракцій, проєктування, :
 Література - термін, щ поєднує реальні сутності (книги, газети, журнали, тощо)
 Оскільки довільне видання повинно формувати картку, для каталога, у цей клас
 (Література) має бути виключений метод getCard(), але, оскільки до нього входить
 відомість про тип (який відомий тільки у конкретному об'єкті), цей метод може
 бути тільки абстрактний.
 */
/*
ООП - об'єктно-орієнтована парадигма програмування
Програма - управління об'єктами та їх взаємодією
Програмування - налаштування об'єктів та зв'язків
Види зв'язків:
 - спадкування (наслідування)
 - асоціація
 - композиція
 - агрегація
 - залежність
* */

