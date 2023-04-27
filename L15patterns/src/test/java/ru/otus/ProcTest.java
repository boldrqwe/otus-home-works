package ru.otus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.processor.ProcessorUpperField10;

public class ProcTest {

    @Test
    public void testProcess() throws Exception {
        ProcessorUpperField10 processor = new ProcessorUpperField10();
        try {
            processor.process();
        } catch (Exception e) {
            Assertions.assertEquals(e.getLocalizedMessage(), "Ошибка в четную секунду");
        }

    }

}
