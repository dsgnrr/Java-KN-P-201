package step.learning.oop;

public class Journal extends Literature
        implements ICopyable, IPeriodic {
    public Journal(String title, int number) {
        this.setNumber(number);
        super.setTitle(title);
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        this.Number = number;
    }

    private int Number;


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
}
