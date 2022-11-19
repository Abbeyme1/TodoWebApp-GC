package classes;

import java.io.Serializable;

public class Todo implements Serializable{

	private static int counter; // NOT IDEAL FOR SCALABLE SYSTEMS
	private String id;
	private String description;
	private boolean done;
	
	public Todo(String description) {
		setDescription(description);
		setId("t"+(++counter));
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDone() {
		return done;
	}
	
	public void changeDone()
	{
		setDone(!done);
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
}
