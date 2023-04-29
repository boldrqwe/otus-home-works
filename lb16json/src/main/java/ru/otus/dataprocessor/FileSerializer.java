package ru.otus.dataprocessor;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import ru.otus.model.Measurement;

public class FileSerializer implements Serializer {

    private final Path outputPath;
    Gson gson = new Gson();

    public FileSerializer(String outputPath) {
        this.outputPath = Paths.get(outputPath);
    }

    @Override
    public void serialize(Map<String, Double> data) {
        // Сортировка записей по ключу перед добавлением их в LinkedHashMap
        Map<String, Double> sortedData = new TreeMap<>(data);
        JsonObject jsonObject = new JsonObject();

        sortedData.forEach((s, aDouble) -> jsonObject.add(s, new JsonPrimitive(aDouble)));
        String s = gson.toJson(jsonObject);

        try {
            Files.writeString(outputPath, s);
        } catch (IOException e) {
            throw new FileProcessException(e.getMessage());
        }
    }
}

