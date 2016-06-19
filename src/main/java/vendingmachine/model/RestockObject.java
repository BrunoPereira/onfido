package vendingmachine.model;

public class RestockObject {
	private String id;
	private int value;
	private int quantity;
	
	public RestockObject(String id, int value, int quantity) {
		this.id = id;
		this.value = value;
		this.quantity = quantity;
	}

	public String getId() {
		return id;
	}

	public int getValue() {
		return value;
	}

	public int getQuantity() {
		return quantity;
	}
}
