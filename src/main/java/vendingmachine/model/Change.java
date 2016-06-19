package vendingmachine.model;

import vendingmachine.enumeration.MoneyEnum;

public class Change {
	private MoneyEnum money;
	private int quantity = 0;
	
	public Change(MoneyEnum product) {
		this.money = product;
	}
	
	public MoneyEnum getMoney() {
		return money;
	}
	
	public void addStock(int quantity){
		this.quantity +=quantity;
	}
	
	public void decMoney(){
		quantity--;
	}

	public int getQuantity() {
		return quantity;
	}
}

