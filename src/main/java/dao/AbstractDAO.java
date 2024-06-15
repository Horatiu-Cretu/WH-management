package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Product;
import model.Order;

/**
 * This class encapsulates all the code needed to interact with the database,
 * such as SQL queries and result processing.
 *
 * @param <T> the type of the objects to be handled by this DAO
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Creates the text for a simple select query that uses the specified field as a filter.
     *
     * @param field the field to use in the where clause
     * @return the select query string
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ").append(field).append(" =?");
        return sb.toString();
    }

    /**
     * Creates the text for a simple delete query that uses the specified field as a filter.
     *
     * @param field the field to use in the where clause
     * @return the delete query string
     */
    private String createDeleteQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE ");
        sb.append("FROM ");
        sb.append(type.getSimpleName());
        if (type.getSimpleName().equals("Product")) {
            sb.append(" WHERE productID =?");
        } else {
            sb.append(" WHERE ").append(field).append(" =?");
        }
        return sb.toString();
    }

    /**
     * Creates the text for a simple select all query.
     *
     * @return the select all query string
     */
    private String createSelectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        if (type.getSimpleName().equals("Order")) {
            sb.append("`").append(type.getSimpleName()).append("`");
        } else {
            sb.append(type.getSimpleName());
        }
        return sb.toString();
    }

    /**
     * Creates the text for a simple update query that uses the specified field as a filter.
     *
     * @param t      the object to be updated
     * @param field1 the field to use in the where clause
     * @return the update query string
     */
    private String createUpdateQuery(T t, String field1) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName()).append(" SET ");
        int i = 0;
        for (Field field : type.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (i == type.getDeclaredFields().length - 1) {
                    sb.append(field.getName()).append(" = '").append(field.get(t).toString()).append("' ");
                } else {
                    sb.append(field.getName()).append(" = '").append(field.get(t).toString()).append("', ");
                }
                i++;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.append(" WHERE ").append(field1).append(" =?");
        return sb.toString();
    }

    /**
     * Creates the text for a simple insert query.
     *
     * @param t the object to be inserted
     * @return the insert query string
     */
    private String createInsertQuery(T t) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        if (t instanceof Order) {
            sb.append("`").append(type.getSimpleName()).append("`");
        } else {
            sb.append(type.getSimpleName());
        }
        sb.append(" (");
        int i = 0;
        for (Field field : type.getDeclaredFields()) {
            if (i == type.getDeclaredFields().length - 1) {
                sb.append(field.getName()).append(") VALUES (");
            } else {
                sb.append(field.getName()).append(",");
            }
            i++;
        }

        i = 0;
        for (Field field : type.getDeclaredFields()) {
            try {
                if (i == type.getDeclaredFields().length - 1) {
                    field.setAccessible(true);
                    // Access field values using reflection
                    sb.append("'").append(field.get(t).toString()).append("');");
                } else {
                    field.setAccessible(true);
                    sb.append("'").append(field.get(t).toString()).append("',");
                }
                i++;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    /**
     * Finds all objects of type T from the database.
     *
     * @return a list of all objects of type T
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Deletes an object by its ID.
     *
     * @param idToDelete the ID of the object to be deleted
     */
    public void deleteById(int idToDelete) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createDeleteQuery("id");

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, idToDelete);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Finds an object by its ID.
     *
     * @param t  the object to be found
     * @param id the ID of the object to be found
     * @return the found object
     */
    public T findById(T t, int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query;
        if (t instanceof Product) {
            query = createSelectQuery("productID");
        } else {
            query = createSelectQuery("id");
        }
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Creates a list of objects from a ResultSet.
     *
     * @param resultSet the ResultSet to create objects from
     * @return a list of objects
     */
    public List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        Constructor<?> ctor = null;
        try {
            Constructor<?>[] ctors = type.getDeclaredConstructors();
            for (Constructor<?> constructor : ctors) {
                if (constructor.getGenericParameterTypes().length == 0) {
                    ctor = constructor;
                    break;
                }
            }

            while (resultSet.next()) {
                assert ctor != null;
                ctor.setAccessible(true);
                //instantiate objects dynamically using reflection
                T instance = (T) ctor.newInstance();

                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);

                    if (value == null && field.getType().isPrimitive()) {
                        if (field.getType().equals(int.class)) {
                            value = 0;
                        } else if (field.getType().equals(long.class)) {
                            value = 0L;
                        } else if (field.getType().equals(double.class)) {
                            value = 0.0;
                        } else if (field.getType().equals(float.class)) {
                            value = 0.0f;
                        } else if (field.getType().equals(boolean.class)) {
                            value = false;
                        } else if (field.getType().equals(char.class)) {
                            value = '\u0000';
                        } else if (field.getType().equals(byte.class)) {
                            value = (byte) 0;
                        } else if (field.getType().equals(short.class)) {
                            value = (short) 0;
                        }
                    }

                    if (value == null && !field.getType().isPrimitive()) {
                        continue;
                    }

                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();

                    if (method != null) {
                        method.setAccessible(true);
                        value = convertValueIfNeeded(method.getParameterTypes()[0], value);
                        //Invoke setter method using reflection
                        method.invoke(instance, value);
                    }
                }

                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException |
                 InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Converts a value to the specified type if needed.
     *
     * @param parameterType the type to convert to
     * @param value         the value to convert
     * @return the converted value
     */
    private Object convertValueIfNeeded(Class<?> parameterType, Object value) {
        if (!parameterType.isInstance(value)) {
            if (parameterType.equals(Integer.class) || parameterType.equals(int.class)) {
                return ((Number) value).intValue();
            } else if (parameterType.equals(Long.class) || parameterType.equals(long.class)) {
                return ((Number) value).longValue();
            } else if (parameterType.equals(Double.class) || parameterType.equals(double.class)) {
                return ((Number) value).doubleValue();
            } else if (parameterType.equals(Float.class) || parameterType.equals(float.class)) {
                return ((Number) value).floatValue();
            } else if (parameterType.equals(Boolean.class) || parameterType.equals(boolean.class)) {
                return Boolean.parseBoolean(value.toString());
            } else if (parameterType.equals(Character.class) || parameterType.equals(char.class)) {
                return value.toString().charAt(0);
            } else if (parameterType.equals(Byte.class) || parameterType.equals(byte.class)) {
                return ((Number) value).byteValue();
            } else if (parameterType.equals(Short.class) || parameterType.equals(short.class)) {
                return ((Number) value).shortValue();
            }
        }
        return value;
    }

    /**
     * Inserts an object into the database.
     *
     * @param t the object to be inserted
     * @return the inserted object
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createInsertQuery(t);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.execute();
            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Updates an object in the database.
     *
     * @param t  the object to be updated
     * @param id the ID of the object to be updated
     * @return the updated object
     */
    public T update(T t, int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query;
        if (t instanceof Product) {
            query = createUpdateQuery(t, "productID");
        } else {
            query = createUpdateQuery(t, "id");
        }
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}
