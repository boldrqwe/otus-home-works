package ru.otus;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private EntityClassMetaData entityClassMetaDataClient;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaDataClient) {
        this.entityClassMetaDataClient = entityClassMetaDataClient;
    }

    @Override
    public String getSelectAllSql() {
        return "SELECT * FROM " + entityClassMetaDataClient.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return "SELECT * FROM " + entityClassMetaDataClient.getName().toLowerCase() + " WHERE " +
                entityClassMetaDataClient.getIdField().getName() +
                " = ?";
    }

    @Override
    public String getInsertSql() {
        List<Field> fieldsWithoutId = entityClassMetaDataClient.getFieldsWithoutId();
        String fields = fieldsWithoutId.stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));

        String placeholders = fieldsWithoutId.stream()
                .map(f -> "?")
                .collect(Collectors.joining(", "));

        return "INSERT INTO " + entityClassMetaDataClient.getName().toLowerCase() + " (" + fields + ") VALUES (" +
                placeholders + ")";
    }

    @Override
    public String getUpdateSql() {
        List<Field> fieldsWithoutId = entityClassMetaDataClient.getFieldsWithoutId();
        String fieldsToUpdate = fieldsWithoutId.stream()
                .map(f -> f.getName() + " = ?")
                .collect(Collectors.joining(", "));

        return "UPDATE " + entityClassMetaDataClient.getName() + " SET " + fieldsToUpdate + " WHERE" +
               entityClassMetaDataClient.getIdField().getName() + " = ?";
    }

    public EntityClassMetaData getEntityClassMetaDataClient() {
        return entityClassMetaDataClient;
    }
}
