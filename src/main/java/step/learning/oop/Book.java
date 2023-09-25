package step.learning.oop;

public class Book extends Literature
        implements ICopyable,IPrintable{
    private String author;

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
