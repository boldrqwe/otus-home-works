package ru.otus.processor;

import java.time.LocalDateTime;

import ru.otus.model.Message;

public interface Processor {

    Message process(Message message);

    default void changeFields(Message message) {
        String field11 = message.getField11();
        String field12 = message.getField12();
        message.setField11(field12);
        message.setField12(field11);
    }

    //todo: 2. Сделать процессор, который поменяет местами значения field11 и field12

    //todo: 3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с
    // гарантированным результатом)
    //         Секунда должна определяьться во время выполнения.
    //         Тест - важная часть задания

    default void process() throws Exception {
        while (true) {
            int seconds = LocalDateTime.now().getSecond();

            if (seconds % 2 == 0) {
                throw new Exception("Ошибка в четную секунду");
            }
        }
    }
    // Обязательно посмотрите пример к паттерну Мементо!
}
