package step.learning.oop;

public abstract class Literature {
    @Required
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public abstract String getCard();
}
