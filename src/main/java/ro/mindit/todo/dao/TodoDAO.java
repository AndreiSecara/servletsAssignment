package ro.mindit.todo.dao;

import ro.mindit.todo.model.Todo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ro.mindit.todo.util.Constants.*;

public class TodoDAO {
    private List<Todo> todos;

    private Connection jdbcConnection;

    public TodoDAO() {
    }

    public void addTodo(Todo todo) throws SQLException {
        System.out.println("Add method invoked!");
        String sqlInsert =
            "INSERT INTO todos(id, name, owner, priority) " +
            "VALUES(?, ?, ?, ?)";

        PreparedStatement  statement = null;

        try {
            connect();

            statement = jdbcConnection.prepareStatement(sqlInsert);
            statement.setInt(1, todo.getId());
            statement.setString(2, todo.getName());
            statement.setString(3, todo.getOwner());
            statement.setString(4, todo.getPriority());
            statement.executeUpdate();

        } finally {
            if (statement != null) {
                statement.close();
            }

            disconnect();
        }
        System.out.println("Add method finished!");

    }

    public void updateTodo(Todo todo) throws SQLException {
        System.out.println("Update method invoked!");
        String sqlUpdate = "UPDATE todos SET name = ?, owner = ?, priority = ? WHERE id = ?";

        PreparedStatement statement = null;

        try {
            connect();

            statement = jdbcConnection.prepareStatement(sqlUpdate);
            statement.setString(1, todo.getName());
            statement.setString(2, todo.getOwner());
            statement.setString(3, todo.getPriority());
            statement.setInt(4, todo.getId());
            statement.executeUpdate();

        } finally {
            if (statement != null) {
                statement.close();
            }

            disconnect();
        }
        System.out.println("Update method finished!");
    }

    public void deleteTodo(Integer id) throws SQLException {
        System.out.println("Delete method invoked with id = "+id);
        String sqlDelete = "DELETE FROM todos WHERE id = ?";

        PreparedStatement statement = null;

        try {
            connect();

            statement = jdbcConnection.prepareStatement(sqlDelete);
            statement.setInt(1, id);
            statement.executeUpdate();

        } finally {
            if (statement != null) {
                statement.close();
            }
            disconnect();
        }
        System.out.println("Delete method finished!");
    }

    public Todo findOne(Integer id) throws SQLException {
        Todo todo = null;
        String sql = "SELECT id, name, owner, priority FROM todos WHERE id = ?";

        PreparedStatement statement = null;
        ResultSet resultSet = null;

            try {

            connect();
            statement = jdbcConnection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String owner = resultSet.getString("owner");
                String priority = resultSet.getString("priority");

                todo = new Todo(id, name, owner, priority);
            }

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }

            disconnect();

        }

        return todo;
    }

    public List<Todo> findAll() throws SQLException {
        System.out.println("List method invoked!");
        todos = new ArrayList<>();

        String sql =
            "SELECT " +
                "id as todoId, " +
                "name as todoName, " +
                "owner as todoOwner, " +
                "priority as todoPriority " +
            "FROM todos as todo order by todoId";

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connect();
            statement = jdbcConnection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("todoId");
                String title = resultSet.getString("todoName");
                String author = resultSet.getString("todoOwner");
                String price = resultSet.getString("todoPriority");

                Todo todo = new Todo(id, title, author, price);
                todos.add(todo);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }

            disconnect();
        }

        return todos;
    }

    public void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName(jdbcDriver);
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    public void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }
}