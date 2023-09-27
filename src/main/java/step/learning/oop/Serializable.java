package step.learning.oop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // інформація доступна при запуску (включається у виконавчий код)
@Target(ElementType.TYPE) // елементи, що позначаються даною анотацією (тип - класи)
public @interface Serializable { // для позначення Літератури, яка включається у файл
}
/*
Анотації - різновиди ітерфейсів(та їх реалізації)
Як правило використовуется для метаданих (додаткові, суппровідні дані,
які не впливають на самі дані, але їх доповнюють і покращують можливості
пошуку/сортування/групування)
Головна відмінність від звичайних інтерфейсів - ці дані можуть стосуватись
не лише типів а й їх компонент (полів, методів, тощо)
 */
