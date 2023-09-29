package step.learning.oop;

import com.google.gson.JsonObject;


import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Serializable
public class Book extends Literature
        implements ICopyable, IPrintable {
    @Required
    private String author;
    private static List<String> requiredFieldsNames;

    private static List<String> getRequierdFieldsNames() {
        if (requiredFieldsNames == null) { // перше звернення будуємо коллекцію
            Field[] fields = Book.class.getDeclaredFields();
            Field[] fields2 = Book.class.getSuperclass().getDeclaredFields();
            requiredFieldsNames = Stream.concat(
                            Arrays.stream(fields),
                            Arrays.stream(fields2))
                    .filter(field -> field.isAnnotationPresent(Required.class))
                    .map(Field::getName)
                    .collect(Collectors.toList());
        }
        return requiredFieldsNames;
    }

    @ParseChecker
    public static boolean isParseableFromJson(JsonObject jsonObject) {
        for (String field : getRequierdFieldsNames()) {
            if (!jsonObject.has(field)) {
                return false;
            }
        }
        return true;
    }

    @FromJsonParser
    public static Book fromJson(JsonObject jsonObject) throws ParseException {
        String[] requiredFields = getRequierdFieldsNames().toArray(new String[0]);
        for (String field : requiredFields) {
            if (!jsonObject.has(field)) {
                throw new ParseException("Missing required field: " + field, 0);
            }
        }
        return new Book(
                jsonObject.get(requiredFields[0]).getAsString(),
                jsonObject.get(requiredFields[1]).getAsString()
        );
    }

    public Book(String author, String title) {
        this.setAuthor(author);
        super.setTitle(title);
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String getCard() {
        return String.format(
                "Book: %s '%s' ",
                this.getAuthor(),
                super.getTitle()
        );
    }


}
/*          this.getTitle, якщо є
getTitle() <
            super.getTitle(), якщо не має this.getTitle()

виклик методу не є однозначним і залежить від наявності/відусутності
this.getTitle()
Іншими словами, переозначення this.getTitle() призведе до "перемикання"
роботи на себе. А оскільки @Override не вимагається, це може статись
випадково.
 */
