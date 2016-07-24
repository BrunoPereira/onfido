package vendingmachine.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import vendingmachine.VendingMachine;
import vendingmachine.exception.*;
import vendingmachine.model.BritishCoins;
import vendingmachine.model.RestockObject;

public class ReStockMachineTest {
	private static VendingMachine machine = new VendingMachine(new BritishCoins());

	private List<RestockObject> restockProducts(){
		List<RestockObject> response = new ArrayList<RestockObject>();
		response.add(new RestockObject("product1", 0, 10));
		response.add(new RestockObject("product2", 10, 10));
		response.add(new RestockObject("product2", 10, 10));
		response.add(new RestockObject("product3", 10, 10));
		response.add(new RestockObject("product4", 5, 50));
		return response;
	}
	private List<RestockObject> restockChange(){
		List<RestockObject> response = new ArrayList<RestockObject>();
		response.add(new RestockObject("£2", 200, 10));
		response.add(new RestockObject("£1", 100, 10));
		response.add(new RestockObject("50p", 50, 10));
		response.add(new RestockObject("20p", 20, 10));
		response.add(new RestockObject("10p", 10, 50));
		response.add(new RestockObject("5p", 5, 50));
		response.add(new RestockObject("2p", 2, 50));
		response.add(new RestockObject("1p", 1, 100));
		response.add(new RestockObject("10p", 10, 50));
		
		return response;
	}	
	
	@Test (expected = InvalidProduct.class)
	public void restockNullProduct() throws InvalidProduct {
		List<RestockObject> products = restockProducts();
		products.add(new RestockObject(null, 10, 10));
		machine.restockProducts(products);
	}
	
	@Test (expected = InvalidProduct.class)
	public void restockEmptyProductId() throws InvalidProduct {
		List<RestockObject> products = restockProducts();
		products.add(new RestockObject("", 10, 10));
		machine.restockProducts(products);
	}
	
	@Test (expected = InvalidProduct.class)
	public void restockNegativeProductValue() throws InvalidProduct {
		List<RestockObject> products = restockProducts();
		products.add(new RestockObject("soda", -10, 10));
		machine.restockProducts(products);
	}
	
	//TODO
	/*
	@Test 
	public void restockZeroProductValue() throws InvalidProduct {
		List<RestockObject> products = restockProducts();
		products.add(new RestockObject("soda", -10, 10));
		machine.restockProducts(products);
	}*/
	

}
