package step.learning.oop;

import java.util.LinkedList;
import java.util.List;

public class Library {
    private List<Book> books;

    public Library() {
        this.books = new LinkedList<>();
    }

    public void add(Book book) {
        if (!books.equals(null)) {
            this.books.add(book);
        }
    }

    public void printAllCards() {
        int index = 0;
        for (Book book : books) {
            index++;
            System.out.println(String.format("%s. %s", index, book.getCard()));
        }
    }
}
