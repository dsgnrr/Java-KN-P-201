package step.learning.oop;

import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Serializable
public class Journal extends Literature
        implements ICopyable, IPeriodic, IPrintable, IMultiple {
    public Journal(String title, int number) {
        this.setNumber(number);
        super.setTitle(title);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private static List<String> requiredFieldsNames;
    @Required
    private int number;

    private static List<String> getRequierdFieldsNames() {
        if (requiredFieldsNames == null) { // перше звернення будуємо коллекцію
            Field[] fields = Journal.class.getDeclaredFields();
            Field[] fields2 = Journal.class.getSuperclass().getDeclaredFields();
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
        //String[] requiredFields = {"title", "number"};
        for (String field : getRequierdFieldsNames()) {
            if (!jsonObject.has(field)) {
                return false;
            }
        }
        return true;
    }

    @FromJsonParser
    public static Journal fromJson(JsonObject jsonObject) throws ParseException {
        String[] requiredFields = getRequierdFieldsNames().toArray(new String[0]);
        for (String field : requiredFields) {
            if (!jsonObject.has(field)) {
                throw new ParseException("Missing required field: " + field, 0);
            }
        }
        return new Journal(
                jsonObject.get(requiredFields[1]).getAsString(),
                jsonObject.get(requiredFields[0]).getAsInt()
        );
    }

    @Override
    public String getCard() {
        return String.format(
                "Journal: '%s' № %s",
                super.getTitle(),
                this.getNumber()
        );
    }

    @Override
    public String getPeriod() {
        return "monthly";
    }

    @Override
    public int getCount() {
        return 12;
    }
}
