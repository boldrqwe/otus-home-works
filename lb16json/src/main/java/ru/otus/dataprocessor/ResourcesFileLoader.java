package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ResourcesFileLoader implements Loader {

    private final String filename;

    public ResourcesFileLoader(String fileName) {
        this.filename = "/" + fileName;
    }

    public List<Measurement> load() {
        List<Measurement> measurements;
        Gson gson = new Gson();

        try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(filename)),
                StandardCharsets.UTF_8)) {
            Type measurementListType = new TypeToken<List<Measurement>>() {
            }.getType();
            measurements = gson.fromJson(reader, measurementListType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return measurements;
    }

}

