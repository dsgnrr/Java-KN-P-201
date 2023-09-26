package step.learning.oop;

import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.lang.reflect.Method;
import java.util.Objects;

public class Library {
    public List<Literature> getFunds() {
        return funds;
    }

    private List<Literature> funds;

    public Library() {
        this.funds = new LinkedList<>();
    }

    public void save() throws IOException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        FileWriter writer = new FileWriter("./src/main/resources/Library.json");
        writer.write(gson.toJson(this.getFunds()));
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

    private Literature fromJson(JsonObject jsonObject) throws ParseException {
        Class<?>[] literatures = {Book.class, Journal.class, Newspaper.class, Hologram.class};
        try {
            for (Class<?> literature : literatures) {
                Method isParseableFromJson = literature.getDeclaredMethod("isParseableFromJson", JsonObject.class);
                isParseableFromJson.setAccessible(true);
                if ((boolean) isParseableFromJson.invoke(null, jsonObject)) {
                    Method fromJson = literature.getDeclaredMethod("fromJson", JsonObject.class);
                    fromJson.setAccessible(true);
                    return (Literature) fromJson.invoke(null, jsonObject);
                }
            }
        } catch (Exception ex) {
            throw new ParseException("Reflection error: "+ ex.getMessage(),0);
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