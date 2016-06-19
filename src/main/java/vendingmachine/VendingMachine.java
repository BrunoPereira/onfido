package vendingmachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import vendingmachine.enumeration.MoneyEnum;
import vendingmachine.exception.InvalidChange;
import vendingmachine.exception.InvalidMoney;
import vendingmachine.model.*;

public class VendingMachine {
	private static final Logger LOGGER = LogManager.getLogger(VendingMachine.class.getName());
	private Map<String,Stock> inventory = new HashMap<String,Stock>();
	private Map<Integer,Change> availableChange = new HashMap<Integer,Change>();
	private Money currency;
	private Product selectedProduct = null;
	private int insertedValue = 0;
	private List<MoneyEnum> insertedMoney = new ArrayList<MoneyEnum>();
	private List<MoneyEnum> safe = new ArrayList<MoneyEnum>();
	
	public VendingMachine(Money currency) {
		this.currency = currency;
	}

	public String selectProduct(String productId){
		LOGGER.debug("Selecting Product:"+productId);
		if (inventory.containsKey(productId)){
			Stock aux = inventory.get(productId);
			if (aux.getQuantity() > 0){
				this.selectedProduct = aux.getProduct();
				return productId ;
			}
		}
		return productId + " is unnavailable";
	}
	
	public int addMoney(String denomination) throws InvalidMoney{
		LOGGER.debug("Adding Money:"+denomination);
		MoneyEnum aux = currency.mapMoney(denomination);
		insertedMoney.add(aux);
		insertedValue += aux.getValue();
		return insertedValue;		
	}
	
	public String getProduct() throws InvalidChange{
		LOGGER.debug("Get Product");
		if (insertedValue >= selectedProduct.getValue()){
			String response = "Product:" + selectedProduct.getId() + " Change:" + getChange(insertedValue, selectedProduct.getValue());
			dispachProduct();			
			return response;
		}
		return "Insufficient Funds. Product:" + selectedProduct.getId() + " costs:" + selectedProduct.getValue() +
				". Current inserted value:" + insertedValue;
	}

	public String resetMoney(){
		LOGGER.debug("Get Money Back");
		String response = moneyToString(insertedMoney);
		insertedValue = 0;
		insertedMoney.clear();
		return response;
	}

	public void restockProducts(List<RestockObject> products){
		LOGGER.debug("Restocking products");
		for(RestockObject product:products){
			if (!inventory.containsKey(product.getId())){
				inventory.put(product.getId(), new Stock(new Product(product.getId(), product.getValue())));
			}
			inventory.get(product.getId()).addStock(product.getQuantity());
			LOGGER.debug("Product added to Stock. product:" + product.getId() + " price:" + product.getValue() + 
					" quantity" + product.getQuantity());
		}
	}
	
	public void restockChange(List<RestockObject> change) throws InvalidMoney{
		LOGGER.debug("Restocking change");
		for(RestockObject money:change){
			if (!availableChange.containsKey(money.getValue())){
				availableChange.put(money.getValue(), new Change(currency.mapMoney(money.getId())));
			}
			availableChange.get(money.getValue()).addStock(money.getQuantity());
			LOGGER.debug("Change added. denomination:" + money.getId() + " value:" + money.getValue() + 
					" quantity" + money.getQuantity());
		}
	}
	
	private void dispachProduct() {
		insertedValue = 0;
		inventory.get(selectedProduct.getId()).decStock();
		selectedProduct = null;
		safe.addAll(insertedMoney);
		insertedMoney.clear();
	}
	
	private String getChange(int insertedValue, int productValue) throws InvalidChange {
		int change = insertedValue - productValue;
		String response = "";
		if (change > 0 /*&& availableChange.isEmpty()*/){
			throw new InvalidChange();
		}
		/* TODO Take care of change
		while( change > 0){
			
		}*/
		
		return response;
	}
	
	private String moneyToString(List<MoneyEnum> wallet) {
		String response = "";
		for(MoneyEnum money:wallet){
			response += money.getDenomination() + " ";
		}
		return response;
	}

}