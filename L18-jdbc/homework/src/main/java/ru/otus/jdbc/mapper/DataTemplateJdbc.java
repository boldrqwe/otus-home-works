package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.core.sessionmanager.DataBaseOperationException;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        String sql = entitySQLMetaData.getSelectByIdSql();
        return dbExecutor.executeSelect(connection, sql, List.of(id),
                resultSet -> {
                    try {
                        if (resultSet.next()) {
                            EntityClassMetaData entityClassMetaData = entitySQLMetaData.getEntityClassMetaDataClient();
                            T obj = (T) entityClassMetaData.getConstructor().newInstance();

                            List<Field> allFields = entityClassMetaData.getAllFields();
                            for (Field field : allFields) {
                                field.setAccessible(true);
                                field.set(obj, resultSet.getObject(field.getName()));
                            }
                            return obj;
                        } else {
                            return null;
                        }
                    } catch (Exception e) {
                        throw new DataBaseOperationException("Error while reading ResultSet", e);
                    }
                });
    }

    @Override
    public List<T> findAll(Connection connection) {
        String sql = entitySQLMetaData.getSelectAllSql();
        return dbExecutor.executeSelect(connection, sql, Collections.emptyList(), resultSet -> {
            List<T> result = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    EntityClassMetaData entityClassMetaData = entitySQLMetaData.getEntityClassMetaDataClient();
                    T obj = (T) entityClassMetaData.getConstructor().newInstance();
                    List<Field> allFields = entityClassMetaData.getAllFields();
                    for (Field field : allFields) {
                        field.setAccessible(true);
                        field.set(obj, resultSet.getObject(field.getName()));
                    }

                    result.add(obj);
                }
            } catch (Exception e) {
                throw new DataBaseOperationException("Error while reading ResultSet", e);
            }
            return result;
        }).orElse(List.of());
    }

    @Override
    public long insert(Connection connection, T object) {
        EntityClassMetaData entityClassMetaData = entitySQLMetaData.getEntityClassMetaDataClient();
        entityClassMetaData.setClass(object.getClass());
        String sql = entitySQLMetaData.getInsertSql();
        List<Object> params = new ArrayList<>();
        List<Field> allFields = entityClassMetaData.getFieldsWithoutId();
        try {
            for (Field field : allFields) {
                field.setAccessible(true);
                params.add(field.get(object));
            }
        } catch (IllegalAccessException e) {
            throw new DataBaseOperationException("Error accessing field value", e);
        }

        return dbExecutor.executeStatement(connection, sql, params);
    }

    @Override
    public void update(Connection connection, T object) {
        String sql = entitySQLMetaData.getUpdateSql();
        List<Object> params = new ArrayList<>();
        EntityClassMetaData entityClassMetaData = entitySQLMetaData.getEntityClassMetaDataClient();
        entityClassMetaData.setClass(object.getClass());
        List<Field> allFields = entityClassMetaData.getAllFields();
        try {
            for (Field field : allFields) {
                field.setAccessible(true);
                params.add(field.get(object));
            }
            // Add ID at the end for WHERE id = ?
            Field idField = entityClassMetaData.getIdField();
            idField.setAccessible(true);
            params.add(idField.get(object));

        } catch (IllegalAccessException e) {
            throw new DataBaseOperationException("Error accessing field value", e);
        }
        dbExecutor.executeStatement(connection, sql, params);
    }
}


