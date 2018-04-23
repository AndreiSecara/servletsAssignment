package ro.mindit.todo.main;


import ro.mindit.todo.dao.TodoDAO;
import ro.mindit.todo.model.Todo;

import java.sql.SQLException;
import java.util.List;

public class TestMethods {

    public static void main(String[] args) {

        TodoDAO todo = new TodoDAO();

        try {

            List<Todo> todos = todo.findAll();
            todos.stream()
                    .sorted((todo1, todo2) -> {
                        if (todo1.getId()>todo2.getId()){
                        return 1;
                    }else{
                        return -1;
                    }
                })
                    .forEach(item -> System.out.println(item));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
