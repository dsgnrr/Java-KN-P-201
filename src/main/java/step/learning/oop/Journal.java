package step.learning.oop;

import com.google.gson.JsonObject;

import java.text.ParseException;

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

    private int number;

    public static boolean isParseableFromJson(JsonObject jsonObject) {
        String[] requiredFields = {"title", "number"};
        for (String field : requiredFields) {
            if (!jsonObject.has(field)) {
                return false;
            }
        }
        return true;
    }

    public static Journal fromJson(JsonObject jsonObject) throws ParseException {
        String[] requiredFields = {"title", "number"};
        for (String field : requiredFields) {
            if (!jsonObject.has(field)) {
                throw new ParseException("Missing required field: " + field, 0);
            }
        }
        return new Journal(
                jsonObject.get(requiredFields[0]).getAsString(),
                jsonObject.get(requiredFields[1]).getAsInt()
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
