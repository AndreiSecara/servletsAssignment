package ro.mindit.todo.model;


import java.io.Serializable;

public class Todo implements Serializable {

	private Integer id;
	private String name;
	private String owner;
	private String priority;

	public Todo() {
        this.id = null;
        this.name = "";
        this.owner = "";
        this.priority = "";
	}

	public Todo(Integer id, String name, String owner, String priority) {
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.priority = priority;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "Todo: " + name +"\n"+
				"Id: " + id  + "\n" +
				"Owner: " + owner + "\n" +
				"Priority: " + priority + "\n";
	}
}
