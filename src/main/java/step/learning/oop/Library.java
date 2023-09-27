package step.learning.oop;

import com.google.gson.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.lang.reflect.Method;
import java.util.Objects;

public class Library {
    public List<Literature> getFunds() {
        return funds;
    }

    private List<Literature> funds;

    private List<Literature> getSerializableFunds() {
        List<Literature> serializableFunds = new ArrayList<>();
        for (Literature literature : getFunds()) {
            // перевіряємо наявність анотації  @Serializable у класа
            if (literature.getClass().isAnnotationPresent(Serializable.class)) {
                serializableFunds.add(literature);
            }
        }
        return serializableFunds;
    }

    public Library() {
        this.funds = new LinkedList<>();
    }

    public void save() throws IOException {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .setPrettyPrinting()
                .create();
        FileWriter writer = new FileWriter("./src/main/resources/Library.json");
        writer.write(gson.toJson(this.getSerializableFunds()));
        writer.close();
    }

    public void load() throws RuntimeException {
        try (InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(
                        this.getClass()
                                .getClassLoader()
                                .getResourceAsStream("Library.json")
                )
        )) {
            for (JsonElement item : JsonParser.parseReader(reader).getAsJsonArray()) {
                this.funds.add(
                        this.fromJson(item.getAsJsonObject())
                );
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException ignored) {
            throw new RuntimeException("Resource not found");
        }
    }

    private List<Class<?>> getSerializableClasses() {
        /* Задача знайти всі класи з анотацією Serializable
        Рішення - сканування папки з зкомпільованими класами, звернення до них,
        перевірка анотаціїї. Вибір папки (-ок) для сканування - об регулюється
        політикою проєкту, або робиться рекурсивно по всіх підпапках проєкту.
        Будемо вважати, що для нашого проєкту сканується лише папка "oop"
         */
        //        Class<?>[] literatures = {Book.class, Journal.class, Newspaper.class, Hologram.class};
        List<Class<?>> literatures = new LinkedList<>();

        // Визначаємо ім'я довільного класу, якмй гарантовано знаходиться у пакеті "oop"
        String className = Literature.class.getName(); // step.learning.oop.Literature
        // видаляємо ім'я самого класу, залишаємо лише пакет (рядок до останньої точки)
        String packageName = className
                .substring(0, className.lastIndexOf('.')); // step.learning.oop
        // Використовуємо той файкт, що пакети однозначно пов'язані з файловую системою,
        // для формування файлового шляху замінюємо точки на слеши
        String packagePath = packageName.replace('.', '/');
        // Звертаємось до даного шляху як до ресурсу, та визначаємо його реальний шлях
        String absolutePath = Literature.class
                .getClassLoader()
                .getResource(packagePath)
                .getPath(); // /D:/IdeaProjects/Java-KN-P-201/target/classes/step/learning/oop
        // Одержуємо перелік визначеної дерикторії (див. тему "файли")
        File[] files = new File(absolutePath).listFiles();
        if (files == null) {
            throw new RuntimeException("Class path inaccessible");
        }

        for (File file : files) { // перебираэмо всі файли з дерикторії
            if (file.isFile()) {
                // далі спираємось на те, що імена файлів = імена класів, лише закінчються на '.class'
                String filename = file.getName();
                if (filename.endsWith(".class")) {
                    String fileClassName = filename.substring(0, filename.lastIndexOf('.'));
                    // fileClassName - ім'я класу, визначеного у даному файлі

                    try {
                        // одержуємо відомості про клас (тип) за іменем
                        Class<?> fileClass = Class.forName(packageName + "." + fileClassName);
                        // перевіряємо в ньмоу наявність анотації Serializable
                        if (fileClass.isAnnotationPresent(Serializable.class)) {
                            // це анотований клас, включаємо його до переліку сканованих
                            literatures.add(fileClass);
                        }
                    } catch (ClassNotFoundException ignored) {
                        continue;
                    }
                }
            } else if (file.isDirectory()) { // за потреби скануємо вкладені папки
                continue;
            }
        }
        return literatures;
    }

    private Literature fromJson(JsonObject jsonObject) throws ParseException {
        try {
            for (Class<?> literature : getSerializableClasses()) {
                // Hardcore - пошук метода за встановленим іменем
                // Method isParseableFromJson = literature.getDeclaredMethods()
                // Ріщення - анотації
//                Method isParseableFromJson = literature.getDeclaredMethod("isParseableFromJson", JsonObject.class);
                Method isParseableFromJson = null;
                for (Method method : literature.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(ParseChecker.class)) {
                        if (isParseableFromJson != null) { // раніше був знайдений метод з анотацією
                            throw new ParseException("Multiple ParseChecker annotaions", 0);
                        }
                        isParseableFromJson = method;
                    }
                }
                if (isParseableFromJson == null) { // якщо метод - до наступного класу
                    continue;
                }
                isParseableFromJson.setAccessible(true);
                if ((boolean) isParseableFromJson.invoke(null, jsonObject)) {
                    Method fromJson = null;
                    for (Method method : literature.getDeclaredMethods()) {
                        if (method.isAnnotationPresent(FromJsonParser.class)) {
                            if (fromJson != null) { // раніше був знайдений метод з анотацією
                                throw new ParseException("Multiple FromJsonParser annotaions", 0);
                            }
                            fromJson = method;
                        }
                    }
                    if (fromJson == null) {
                        continue;
                    }
                    fromJson.setAccessible(true);
                    return (Literature) fromJson.invoke(null, jsonObject);
                }
            }
        } catch (Exception ex) {
            throw new ParseException("Reflection error: " + ex.getMessage(), 0);
        }
        /*if (Book.isParseableFromJson(jsonObject)) {
            return Book.fromJson(jsonObject);
        } else if (Journal.isParseableFromJson(jsonObject)) {
            return Journal.fromJson(jsonObject);
        } else if (Newspaper.isParseableFromJson(jsonObject)) {
            return Newspaper.fromJson(jsonObject);
        } else if (Hologram.isParseableFromJson(jsonObject)) {
            return Hologram.fromJson(jsonObject);
        }*/
        throw new ParseException("Literature type unrecognized", 0);
    }

    public void add(Literature literature) {
        if (!funds.equals(null)) {
            this.funds.add(literature);
        }
    }

    public void printAllCards() {
        int index = 0;
        for (Literature literature : funds) {
            index++;
            System.out.println(String.format("%s. %s", index, literature.getCard()));
        }
    }

    public void printCopyable() {
        int index = 0;
        for (Literature literature : funds) {
            if (isCopiable(literature)) {
                index++;
                System.out.println(String.format("%s. %s", index, literature.getCard()));
            }
        }
    }

    public void printNonCopyable() {
        int index = 0;
        for (Literature literature : funds) {
            if (!isCopiable(literature)) {
                index++;
                System.out.println(String.format("%s. %s", index, literature.getCard()));
            }
        }
    }

    public List<Literature> getPrintable() {
        List<Literature> printableList = new LinkedList<Literature>();
        for (Literature literature : funds) {
            if (isPrintable(literature)) {
                printableList.add(literature);
            }
        }
        return printableList;
    }

    public List<Literature> getNonPrintable() {
        List<Literature> printableList = new LinkedList<Literature>();
        for (Literature literature : funds) {
            if (!isPrintable(literature)) {
                printableList.add(literature);
            }
        }
        return printableList;
    }

    public List<Literature> getMultiple() {
        List<Literature> multipleList = new LinkedList<Literature>();
        for (Literature literature : funds) {
            if (isMultiple(literature)) {
                multipleList.add(literature);
            }
        }
        return multipleList;
    }

    public List<Literature> getSingle() {
        List<Literature> multipleList = new LinkedList<Literature>();
        for (Literature literature : funds) {
            if (!isMultiple(literature)) {
                multipleList.add(literature);
            }
        }
        return multipleList;
    }

    public boolean isMultiple(Literature literature) {
        return (literature instanceof IMultiple);
    }

    public boolean isPrintable(Literature literature) {
        return (literature instanceof IPrintable);
    }

    public boolean isCopiable(Literature literature) {
        return (literature instanceof ICopyable);
    }

    public void printPeriodic() {
        int index = 0;
        for (Literature literature : funds) {
            if (isPeriodic(literature)) {
                index++;
                IPeriodic listAsIPeriodic = (IPeriodic) literature;
                System.out.println(String.format("%s. %s: %s", index, listAsIPeriodic.getPeriod(), literature.getCard()));
            }
        }
    }

    public void printPeriodic2() {
        for (Literature literature : funds) {
            try {
                Method getPeriodMethod = literature.getClass()
                        .getDeclaredMethod("getPeriod");
                System.out.println(getPeriodMethod.invoke(literature) + " " + literature.getCard());
            } catch (Exception ignored) {

            }
        }
    }

    public void printNonPeriodic() {
        int index = 0;
        for (Literature literature : funds) {
            if (!isPeriodic(literature)) {
                index++;
                System.out.println(String.format("%s. %s", index, literature.getCard()));
            }
        }
    }

    public boolean isPeriodic(Literature literature) {
        return (literature instanceof IPeriodic);
    }

}
/*
Перетворення типів (cast), спадкування
Literature [ title | getTitle() ]
Book       [ ----- | <        > | author | getAuthor() ]
 */