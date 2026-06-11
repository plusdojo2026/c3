package dto;

public class Parts {
	protected int id;
	protected String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Parts(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Parts() {
		this(0, "");
	}
}
