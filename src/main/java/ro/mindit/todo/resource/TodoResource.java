package ro.mindit.todo.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import ro.mindit.todo.dao.TodoDAO;
import ro.mindit.todo.model.Todo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

public class TodoResource extends HttpServlet {

    private TodoDAO todoDAO;

    @Override
    public void init() throws ServletException {
        todoDAO = new TodoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("application/json");

        String json = getTodoFromDatabase(request);

        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        StringBuffer jb = new StringBuffer();
    String line;
        try {
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null)
            jb.append(line);
    } catch (IOException e) {
        e.printStackTrace();
    }

        try {
        JSONObject jsonObject = new JSONObject(jb.toString());

        Integer id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        String owner = jsonObject.getString("owner");
        String priority = jsonObject.getString("priority");

        Todo todo = buildTodo(id, name, owner, priority);

        todoDAO.addTodo(todo);

    } catch (JSONException e) {
        // crash and burn
        throw new IOException("Error parsing JSON request string");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuffer jb = new StringBuffer();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        try {
            JSONObject jsonObject = new JSONObject(jb.toString());

            Integer id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            String owner = jsonObject.getString("owner");
            String priority = jsonObject.getString("priority");

            Todo todo = buildTodo(id, name, owner, priority);

            todoDAO.updateTodo(todo);

        } catch (JSONException e) {
            // crash and burn
            throw new IOException("Error parsing JSON request string");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String idString = request.getParameter("id");

        if (idString != null) {
            Integer id = Integer.parseInt(idString);
            try {
                todoDAO.deleteTodo(id);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
    }

    private String getTodoFromDatabase(HttpServletRequest request) throws JsonProcessingException, UnsupportedEncodingException {
        String json = "";
        ObjectMapper objectMapper = new ObjectMapper();

        String id = request.getQueryString();

        try {
            todoDAO.connect();

            if (id != null) {
                id = id.replace("id=", "");
                Todo todo = todoDAO.findOne(Integer.parseInt(id));
                json = objectMapper.writeValueAsString(todo);
            } else {
                List<Todo> todos = todoDAO.findAll();
                json = objectMapper.writeValueAsString(todos);
            }

            todoDAO.disconnect();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return json;
    }

    private Todo buildTodo(Integer id, String name, String owner, String priority) {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setName(name);
        todo.setOwner(owner);
        todo.setPriority(priority);
        return todo;
    }
}