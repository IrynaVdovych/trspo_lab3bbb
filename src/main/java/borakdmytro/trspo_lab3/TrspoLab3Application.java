package borakdmytro.trspo_lab3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrspoLab3Application {

    public static void main(String[] args) {
        SpringApplication.run(TrspoLab3Application.class, args);
    }

}


/**
 * 4. Система Бібліотека. Читач має можливість здійснювати пошук і замовлення Книг у
 * Каталозі. Бібліотекар видає Читачу Книгу на абонемент або у читальний зал. Книга
 * може бути у Бібліотеці в одному або кількох екземплярах.
 *
 *
 * переглянути бібліотекаря /ів
 * додати бібліотекаря
 * ? редагувати бібліотекаря
 * ? видалити бібліотекаря
 *
 * переглянути користувачів
 * додати користувача
 * ? редагувати користувача
 * ? видалити користувача (і його нові замовлення)
 *
 * переглянути книгу/и
 * переглянути книгу в каталозі ( по назві / автору)
 * додати книгу
 * редагувати книгу
 * // видалити книгу
 *
 * переглянути нові замовнення /borrowings/new
 * підтвердити замовлення /borrowings/id/confirm
 * скасувати замовлення /borrowings/id/cancel
 *
 * переглянути замовлення до повернення /borrowings/active
 * завершити замовлення /borrowings/id/finish
 *
 * переглянути усі замовлення /borrowings/history
 *
 *
 * зробити замовлення книги /user/id/borrowings
 * переглянути мої незавершені замовнення /user/id/borrowings/active
 * переглянути історію замовлень /user/id/borrowings/history
 * скасувати замовлення /user/id/borrowings/id/cancel
 *
 * 18
 * ? 23
 */
