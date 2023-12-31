package step.learning.oop;

public interface ICopyable {
    /*
    Інтерфейс-маркер, у ньому немає методів, він буде позначати Літературу,
    яка може буи зкопійована (уявимо, що книги, журнали копіюються, газета - ні)
     */
}
/*
Інтерфейси (в ООП) - абстрактні класи, які містять
- тільки методи
- тільки абстрактні
- тільки public
Роль інтерфейсів залежить від галузі використання
- інтерфейси-маркери: як правило інтерфейси без методів (або з малою кількістю),
    які "помічають" класи як такі, що належать до певної групи
    = Анотації (атрибути) - різновид маркерів для частин типів (полів, методів, класів, тощо)
- інтерфейси-контракти: набір методів, необхідних для реалізації з метою
    включення об'єктів у певну функціональність
    = DIP (принцип з SOLID) Dependency Inversion Principle: ~ не типізувати
        залежності як класи, а типізувати як інтерфейси
        ? class Ctrl {Md5Hash hash}             ! class Ctrl { IHash hash }

 */
