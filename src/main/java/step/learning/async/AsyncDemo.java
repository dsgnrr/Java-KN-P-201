package step.learning.async;

public class AsyncDemo {
    public void run() {
        System.out.println("Async demo");
        multiThreadDemo();
    }

    private void multiThreadDemo() {
        Thread thread = new Thread( // об'єкт Thread відповідає за системний ресурс - потік
                new Runnable() { // Сильна ООП не дозволяє передавати лише метод, а
                    @Override           // потребує функціональний інтерфейс - інтерфейс з одним методом
                    public void run() { // Java дозволяє імплементувати інтерфейси у точці виклику
                        try {
                            System.out.println("Thread starts");
                            Thread.sleep(2000);
                            System.out.println("Thread finishes");
                        } catch (InterruptedException ex) {
                            System.err.println("Sleeping broken" + ex.getMessage());
                        }
                    }
                }); // Створення об'єкту НЕ запускає його активність
        // Запуск можливий у двох режимах: синхронному (run) та асинхронному (start)
        thread.start();
        // якщо завершення не очікувати, то продовжується виконання даної активності
        // якщо потрібне очікування, то виконується метод join
        try {
            thread.join();
        } catch (InterruptedException ex) {
            System.out.println("Thread joining interruped: " + ex.getMessage());
        }
        // Від Java-8 з'являється конструкція "лямбда", яка є сумісною з функціональними інтрфейсами
        new Thread(() -> {
            System.out.println("Thread 2 starts");
        }).start();
        // а також перетворювати тип, зазначаючи метод від іншого об'єкту
        new Thread(this::methodForThread).start();
        System.out.println("multiThreadDemo() finishes");
    }

    private void methodForThread() {
        System.out.println("new Thread (this::methodForThread).start();");
    }

}
/*
Асинхронне програмування
Синхронність (у програмуванні) - послідовне, один за одним виконання інструкцій
Асинхронність - будь-яке відхилення від синхронності.

Синхро ----------------- =============  ~~~~~~~~~~~~~~~~~~~~~~

Асинх ------------------ - в один момент часу виконується декілька інструкцій
Парал ======
      ~~~~~~~~~~
Асинх -- -- -- -- -- --  - в один момент часу тільки одна інструкциія
непар ==  ==  ~~  ~~  ~~  ~~

Способи реалізації асинхронності
- багатозадачність - рівень мови програмування, використання засобів мови/платформи
- багатопоточність - рівень ОС, у межах одного процесу/процесору
- багатопроцесність - рівень ОС, у різних процесах
- мережі технології (grid, network-)
 */
