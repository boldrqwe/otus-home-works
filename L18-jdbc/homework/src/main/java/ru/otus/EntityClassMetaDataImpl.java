package ru.otus;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.crm.model.Id;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private  Class<T> clazz;
    private  List<Field> allFields;
    private  List<Field> fieldsWithoutId;
    private Field idField;

    public EntityClassMetaDataImpl() {

    }

    public void setClass(Class<T> clazz) {
        this.clazz = clazz;
        this.allFields = Arrays.asList(clazz.getDeclaredFields());

        for (Field field : allFields) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
                break;
            }
        }

        if (idField == null) {
            throw new IllegalArgumentException(String.format("No field with @Id annotation in class %s", clazz.getSimpleName()));
        }

        this.fieldsWithoutId = allFields.stream()
                .filter(field -> !field.equals(idField))
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
