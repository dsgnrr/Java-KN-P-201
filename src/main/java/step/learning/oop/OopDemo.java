package step.learning.oop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

public class OopDemo {
    public void run() {
        Library library = new Library();
        try {
            library.load();
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        library.printAllCards();
    }

    public void run3() {
        // JSON - засобами Gson у задачы фабричного типу
        // уявимо, що ми не знаємо тип (книга, газета, ...) до того як парсимо рядок
        String str = "{\"author\": \"D. Knuth\", \"title\" : \"Art of programming\"}";
        // узагальнений парсер - створює JsonObject як ~Map<K, V>
        JsonObject literatureObject = JsonParser.parseString(str).getAsJsonObject();
        Literature literature = null;
        if (literatureObject.has("author")) {
            literature = new Book(
                    literatureObject.get("title").getAsString(),
                    literatureObject.get("author").getAsString()
            );
        } else if (literatureObject.has("number")) {
            literature = new Journal(
                    literatureObject.get("title").getAsString(),
                    literatureObject.get("number").getAsInt()
            );

        } else if (literatureObject.has("date")) {
            try {
                literature = new Newspaper(
                        literatureObject.get("title").getAsString(),
                        literatureObject.get("date").getAsString()
                );
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            System.out.println(literature.getCard());
        }
    }

    public void run2() {
        // JSON - засобами Gson
        Gson gson = new Gson();
        String str = "{\"author\": \"D. Knuth\", \"title\" : \"Art of programming\"}";
        Book book = gson.fromJson(str, Book.class);// ~typeof
        System.out.println(book.getCard());
        // оптимізований рядок - з мінімальною кількістю символів
        System.out.println(gson.toJson(book)); // {"author":"D. Knuth","title":"Art of programming"}
        book.setAuthor(null);
        System.out.println(gson.toJson(book));
        // Для налаштування серіалізатора використовується GsonBuilder
        Gson gson2 = new GsonBuilder()
                .setPrettyPrinting() // додаткові відступи та розриви
                .serializeNulls() // включати до json поля з null
                .create();
        System.out.println(gson2.toJson(book));
        try (
                InputStream bookStream =                                    // Одержуємо доступ до ресурсу
                        this.getClass()                                        // Оскільки файл копіюється до папки
                                .getClassLoader()                            // з класами, знаходимо її через getClassLoader()
                                .getResourceAsStream("book.json");
                InputStreamReader bookReader =                                // Для використання gson.fromJson
                        new InputStreamReader(                                // необхідний Reader, відповідно
                                Objects.requireNonNull(bookStream));        // створюжмо InputStreamReader
        ) {
            book = gson.fromJson(bookReader, Book.class);
            System.out.println(book.getCard());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run1() {
        Library library = new Library();
        try {
            library.add(new Book("D. Kunth", "Art of programming"));

            library.add(new Newspaper("Daily Mail", "2023-09-25"));

            library.add(new Journal("Science Journal", 5));

            library.add(new Book("Richter", "CLR via C#"));

            library.add(new Newspaper("Washington Post", "1971-01-01"));

            library.add(new Journal("Marvel Comics", 117));

            library.add(new Hologram("Hologram", "1920x1080"));

            library.save();

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
            System.out.printf("%s. %s count: %s%n", index, literature.getCard(), multipleLit.getCount());
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
Ресурси проекту - папка "resources" (src/main/resources), файли з якої
за замовчаннням копіюються у збірку (target/classes). Це гарантує
наявність ресурсів у підсумковому (зібраному) проєкті.
getClassLoader(), викликаний на довільному типі з нашого проєекту
дозволить визначити розміщення папки класів, а відтак і ресурсів
.getResourceAsStream("book.json") - відкриває файл та дає Stream
 */
/*
Робота з пакетами. JSON
Бібліотеки класів (аналог DLL) у Java постачається як .JAR файли
Для використання їх можливостей необхідно додавати ці файли до збірки
Альтернатива - автоматизоване управління пакетами IDE з їх підключенням
для проектів за допомогою декларацій у pom.xml файлі (у секції <dependencies>)
Maven має репозиторій (https://mvnrepository.com) - онлайн бібліотеку залежностей, завантаження,
з якої відбувається засобами IDE
 */
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

