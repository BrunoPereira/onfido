package vendingmachine.model;

public class Product {
	private String id;
	private int value;
	
	public Product(String id, int value) {
		this.id = id;
		this.value = value;
	}
	
	public String getId() {
		return id;
	}
	public int getValue() {
		return value;
	}
}
