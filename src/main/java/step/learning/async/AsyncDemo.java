package step.learning.async;

import java.util.Locale;

public class AsyncDemo {
    private double sum;
    private int activeThreadsCount; // кількість потоків, які ще не завершилися
    private final Object sumLocker = new Object();
    private final Object atcLocker = new Object();

    public void run() {
        System.out.println("Async demo");
        //multiThreadDemo();
        int months = 12;
        sum = 100.0;
        activeThreadsCount = months;
        Thread[] threads = new Thread[months];
        for (int i = 0; i < months; i++) {
            threads[i] = new Thread(new MonthRate(i + 1));
            threads[i].start();
//            threads[i].join(); // синхронне виконання
        }
        // визначення підсумку робти всіх потоків. Варіант 1) - чекаємо всі
        /*
        try {
            for (int i = 0; i < months; i++) {
                threads[i].join();
            }
        } catch (InterruptedException igonred) {
        }
        System.out.printf(Locale.US,
                "-----------%nTotal sum: %.2f%n", sum);

         */
        // визначення підсумку робти всіх потоків. Варіант 2) - кожен потік перевіряє
        // чи він є останній
    }


    class MonthRate // Nested class - class inside class
            implements Runnable {
        private final int month;

        public MonthRate(int month) {
            this.month = month;
        }

        @Override
        public void run() {
            double p = 0.1; // вважаємо, що це одержано як результат запиту
            double localSum; // локальні змінні - не є спільними для різних потоків
            try {
                Thread.sleep(1000); // імітація тривалого запиту до АПІ
            } catch (InterruptedException ignore) {
            }
            synchronized (sumLocker) { // "закрити" sumLocker
                // ~lock у С# - блок синхронізації

                // "додаємо" свій резуьтат до спільної суми
                localSum = // зберігаємо локальну копію результату
                        sum = sum * (1.0 + p); // read(sum) mul(1.1) write(sum)
                // read(sum) mul(1.1) write (sum) -- інший потік

            }// "відкрити" sumLocker (кінець блоку synchronized)

            // виводимо дані
            System.out.printf(Locale.US,
                    "month: %02d, percent: %.2f, sum: %.2f%n", month, p, localSum);
            // визначення підсумку робти всіх потоків. Варіант 2) - кожен потік перевіряє
            // чи він є останній
            synchronized (atcLocker) {
                activeThreadsCount--;
                if (activeThreadsCount == 0) {
                    System.out.printf(Locale.US,
                            "-----------%nTotal sum: %.2f%n", sum);
                }
            }
        }

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
Багатопоточність - використання систеинх ресурсів "Thread".
Особливості:
 - потік (як системний ресурс) вимагає адреси функції та адреси її параметрів
    це відбивається у тому, що
     = поверненення даних немає
     = передача параметрів обмежена однією адресою (але що за цією адресою - не обмежене)
    метод (класу), який стартує у потоці,
    має тип void, а параметор може бути тільки один (або не бути жодного)
    Традиційно, якщо у потік потрібно передавати дані, то це
    здійснюється шляхом переозначення або класу Thread, або інтерфейсу
    Runnable з додатковими параметрами
 - через першу особливість, багатопоточність вживає спільні ресурси
    як спосіб обміну даними. І це призводить до "конкуренції", коли
    одночасні звернення до спільного ресурсу можуть призводити до
    некоректної поведінки.
 */
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
/*
Використання асинхронних підходів має передувати дослідження задачі на
можливість парлельності. Як правило, мова іде про комутативність операцій
(їх незалежність від порядку операндів). Інколи задачі перед програмуванням
переформульовують для можливості паралельних обчислень.
Задача "інфляція": відомі відсотки інфляції за кожен місяць року, завдання -
обчислити річну інфляцію. Складність - одержання місячних даних здійснюється
з АПИ нацбанку, яке обмежене швидкістю мережі.
Етап 1. Аналіз можливості паралельності
    ( х + 10% ) + 20% =?= ( х + 20% ) + 10%
        110 + 22                120 + 12
    +10% -> x1.1, 20% -> 1.2
    (x + 1.1)*1.2 =?= (x+1.2)*1.1
    x*(1.1*1.2) == x*(1.2*1.1) - обгрунтовано, алгоритм можна виконувати паралельно
 */
/*
Синхронізація - утворення ситуації гарантовано послідовного виконання певного коду
(без можливості кількох активностей одночасно). Іншими словами - це утворення "транзакцій" - набору команд, які виконуюься як єдине ціле.
Зупинка (пауза) потоків - складна системна задача, для її вирішення використовуються
"сигнальні" об'єкти, серед яких критична секція, мьютекси, семафори, тощо.
У мовах типу Java (та C#) усі reference-типи мають у своєму складі критичні секції
Поширена практика - використання у якості синхро-об'єктів вже наявні
synchronized(list) {
    list.add(item);
}
Але це небезпечно, оскільки може перепризначити сам об'єкт, наприклад
synchronized(str) {
    str += "Hello"; тут створюється новий str
                    розблоковується незаблокований (новий) str, старий - deadlock
}
 */
