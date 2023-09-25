package step.learning.oop;

import java.util.LinkedList;
import java.util.List;
import java.lang.reflect.Method;

public class Library {
    private List<Literature> funds;

    public Library() {
        this.funds = new LinkedList<>();
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
            try{
                Method getPeriodMethod = literature.getClass()
                        .getDeclaredMethod("getPeriod");
                System.out.println(getPeriodMethod.invoke(literature)+" "+literature.getCard());
            }catch (Exception ignored){

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