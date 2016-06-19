package vendingmachine.model;

public class Stock {
	private Product product;
	private int quantity = 0;
	
	public Stock(Product product) {
		this.product = product;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public void addStock(int quantity){
		this.quantity +=quantity;
	}
	
	public void decStock(){
		quantity--;
	}

	public int getQuantity() {
		return quantity;
	}
}

