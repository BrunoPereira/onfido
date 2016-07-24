package vendingmachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import vendingmachine.enumeration.MoneyEnum;
import vendingmachine.exception.InvalidChange;
import vendingmachine.exception.InvalidMoney;
import vendingmachine.model.*;

/**
* <h1>VendingMachine</h1>
* This Class creates a Vending Machine
* We duplicate the behavior of a physical vending machine
* It has an inventory of products
* A safe to collect the money
* If the price is lower than the insert amount, it will give change
*
* @author  Bruno Pereira
* @version 1.0
* @since   2016-06-19
*/
public class VendingMachine {
	private static final Logger LOGGER = LogManager.getLogger(VendingMachine.class.getName());
	private Map<String,Stock> inventory = new HashMap<String,Stock>();
	private Map<Integer,Change> availableChange = new HashMap<Integer,Change>();
	private List<Integer> sortedChange = new ArrayList<Integer>();
	private Money currency;
	private Product selectedProduct = null;
	private int insertedValue = 0;
	private List<MoneyEnum> insertedMoney = new ArrayList<MoneyEnum>();
	private List<MoneyEnum> safe = new ArrayList<MoneyEnum>();
	
	public VendingMachine(Money currency) {
		this.currency = currency;
	}
	
	/**
	 * Selects a product
	 * @param productId - The unique identifier of a given product
	 * 
	 * @return the id of the product or a message saying that the product is unavailable 
	 */
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
	
	/**
	 * Increases the amount of money introduced by the user
	 * @param denomination - The denomination of the money introduced
	 * 
	 * @return the total amount of money introduced by the user
	 */
	public int addMoney(String denomination) throws InvalidMoney{
		LOGGER.debug("Adding Money:"+denomination);
		MoneyEnum aux = currency.mapMoney(denomination);
		insertedMoney.add(aux);
		insertedValue += aux.getValue();
		return insertedValue;		
	}
	
	/**
	 * Starts the process of getting a product
	 * 
	 * @return the product select by the user and the change
	 */
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
	
	/**
	 * Returns the money introduced by the user 
	 * 
	 * @return the money introduced by the user
	 */
	public String resetMoney(){
		LOGGER.debug("Get Money Back");
		String response = moneyToString(insertedMoney);
		insertedValue = 0;
		insertedMoney.clear();
		return response;
	}

	/**
	 * Adds stock to the machine inventory
	 * @param products - A list of RestockObject containing the id, price and quantity to restock
	 */
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
	
	/**
	 * Adds Change to the machine 
	 * @param change - A list of RestockObject containing the denomination, value and quantity to restock
	 */
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
		updateSortedChange();
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
		List<MoneyEnum> changeCoins = new ArrayList<MoneyEnum>();

		while( change > 0){
			try {
				MoneyEnum coin = getMaxCoinForChange(change);
				changeCoins.add(coin);
				change -= coin.getValue();
			} catch (InvalidChange e) {
				// The change needs to be kept in the machine
				List<RestockObject> restock = new ArrayList<RestockObject>();
				for(MoneyEnum coin:changeCoins){
					RestockObject aux = new RestockObject(coin.getDenomination(), coin.getValue(), 0);
					restock.add(aux);
				}
				try {
					restockChange(restock);
				} catch (InvalidMoney e1) {
					// Wont happen we're using values that have been inserted already
				}
				throw new InvalidChange();
			}
		}
		return moneyToString(changeCoins);
	}
	
	private MoneyEnum getMaxCoinForChange(int change) throws InvalidChange {
		if (sortedChange.isEmpty()){
			throw new InvalidChange();
		}
		for(Integer coin:sortedChange){
			if (coin < change){
				return getAndUpdateAvailableChange(coin);
			}
		}
		throw new InvalidChange();
	}
	
	private MoneyEnum getAndUpdateAvailableChange(Integer coin) {
		Change maxCoin = availableChange.get(coin);
		maxCoin.decMoney();
		if (maxCoin.getQuantity() == 0){
			availableChange.remove(coin);
			updateSortedChange();
		}
		return maxCoin.getMoney();
	}

	private void updateSortedChange() {
		List<Integer> sortedChange = new ArrayList<Integer>();
		sortedChange.addAll(availableChange.keySet());
		Collections.sort(sortedChange, new Comparator<Integer>()	{
			public int compare(Integer o1, Integer o2)	{
				return o2 - o1; //DESC
			}
		});
		this.sortedChange = sortedChange;
	}

	private String moneyToString(List<MoneyEnum> wallet) {
		String response = "";
		for(MoneyEnum money:wallet){
			response += money.getDenomination() + " ";
		}
		return response;
	}

}