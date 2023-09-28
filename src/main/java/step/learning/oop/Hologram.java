package step.learning.oop;

import com.google.gson.JsonObject;

import java.text.ParseException;

@Serializable
public class Hologram extends Literature {
    public Hologram(String title, String size) {
        super.setTitle(title);
        this.setSize(size);
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    private String size;

    @ParseChecker
    public static boolean isParseableFromJson(JsonObject jsonObject) {
        String[] requiredFields = {"title", "size"};
        for (String field : requiredFields) {
            if (!jsonObject.has(field)) {
                return false;
            }
        }
        return true;
    }

    @FromJsonParser
    public static Hologram fromJson(JsonObject jsonObject) throws ParseException {
        String[] requiredFields = {"title", "size"};
        for (String field : requiredFields) {
            if (!jsonObject.has(field)) {
                throw new ParseException("Missing required field: " + field, 0);
            }
        }
        return new Hologram(
                jsonObject.get(requiredFields[0]).getAsString(),
                jsonObject.get(requiredFields[1]).getAsString()
        );
    }

    @Override
    public String getCard() {
        return String.format(
                "Hologram: '%s' size: %s",
                super.getTitle(),
                this.getSize()
        );
    }
}
