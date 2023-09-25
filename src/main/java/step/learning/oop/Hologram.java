package step.learning.oop;

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

    @Override
    public String getCard() {
        return String.format(
                "Hologram: '%s' size: %s",
                super.getTitle(),
                this.getSize()
        );
    }
}
