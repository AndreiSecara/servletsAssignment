package ro.mindit.todo.dao;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ro.mindit.todo.model.Todo;

import java.sql.SQLException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class TodoDAOIntegrationTest {

    private TodoDAO todoDAO;

    @Before
    public void setUp() {
        todoDAO = new TodoDAO();
    }

    @Test
    @Ignore
    public void integrationTestAddTodo() throws SQLException {
        todoDAO.addTodo(new Todo(1, "Dorna", "Mark", "High"));
    }

    @Test
  //  @Ignore
    public void integrationTestFindAllTodos() throws SQLException {
        // todoDAO.addTodo(new Todo(null, "test name", "test owner", ""));

        List<Todo> results = todoDAO.findAll();
        System.out.println(results.size());
        assertTrue("Expected to find at least one todo in DB, the one inserted above", results.size() >= 1);

    }
}
