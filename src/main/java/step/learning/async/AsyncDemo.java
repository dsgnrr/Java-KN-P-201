package step.learning.async;

public class AsyncDemo {
    public void run() {
        System.out.println("Async demo");
        multiThreadDemo();
    }

    private void multiThreadDemo() {
        // region Class work
//        Thread thread = new Thread( // об'єкт Thread відповідає за системний ресурс - потік
//                new Runnable() { // Сильна ООП не дозволяє передавати лише метод, а
//                    @Override           // потребує функціональний інтерфейс - інтерфейс з одним методом
//                    public void run() { // Java дозволяє імплементувати інтерфейси у точці виклику
//                        try {
//                            System.out.println("Thread starts");
//                            Thread.sleep(2000);
//                            System.out.println("Thread finishes");
//                        } catch (InterruptedException ex) {
//                            System.err.println("Sleeping broken" + ex.getMessage());
//                        }
//                    }
//                }); // Створення об'єкту НЕ запускає його активність
        // Запуск можливий у двох режимах: синхронному (run) та асинхронному (start)
        //thread.start();
        // якщо завершення не очікувати, то продовжується виконання даної активності
        // якщо потрібне очікування, то виконується метод join
//        try {
//            thread.join();
//        } catch (InterruptedException ex) {
//            System.out.println("Thread joining interruped: " + ex.getMessage());
//        }
//        // Від Java-8 з'являється конструкція "лямбда", яка є сумісною з функціональними інтрфейсами
//        new Thread(() -> {
//            System.out.println("Thread 2 starts");
//        }).start();
//        // а також перетворювати тип, зазначаючи метод від іншого об'єкту
//        new Thread(this::methodForThread).start();
//        System.out.println("multiThreadDemo() finishes");
        // endregion
//        За допомогою багатопоточності реалізувати наступну схему виконання :
//        ----------------1------------- ---final---
//                ------2------ --------3-------
//                1 start
//        2 start
//        2 finish
//        3 start
//        3 finish
//        final
//        1 finish
        Thread thread1 = new Thread(() -> {
            System.out.println("1 start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("thread.sleep: " + e.getMessage());
            }
            System.out.println("1 finish");
        });
        Thread thread2 = new Thread(() -> {
            System.out.println("2 start");
            System.out.println("2 finish");
        });
        Thread thread3 = new Thread(() -> {
            System.out.println("3 start");
            System.out.println("3 finish");
        });
        thread1.start();
        thread2.start();

        try {
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println("thread.join: " + e.getMessage());
        }
        thread3.start();
        try {
            thread3.join();
        } catch (InterruptedException e) {
            System.out.println("thread.join: " + e.getMessage());
        }
        System.out.println("final");
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
