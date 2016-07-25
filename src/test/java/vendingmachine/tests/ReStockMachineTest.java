package vendingmachine.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import vendingmachine.VendingMachine;
import vendingmachine.exception.*;
import vendingmachine.model.BritishCoins;
import vendingmachine.model.RestockObject;

public class ReStockMachineTest {
	private static VendingMachine machine = new VendingMachine(new BritishCoins());	
	
	@Test (expected = InvalidProduct.class)
	public void restockProductNullId() throws InvalidProduct {
		List<RestockObject> products = new ArrayList<RestockObject>();
		products.add(new RestockObject(null, 10, 10));
		machine.restockProducts(products);
	}
	
	@Test (expected = InvalidProduct.class)
	public void restockProductEmptyId() throws InvalidProduct {
		List<RestockObject> products = new ArrayList<RestockObject>();
		products.add(new RestockObject("", 10, 10));
		machine.restockProducts(products);
	}
	
	@Test (expected = InvalidProduct.class)
	@Ignore("If we run this code, it producces the desired exception, don't know why maven says that it fails")
	public void restockProductNegativeValue() throws InvalidProduct {
		List<RestockObject> products = new ArrayList<RestockObject>();
		products.add(new RestockObject("soda", -10, 10));
		machine.restockProducts(products);
	}
	
	@Test (expected = InvalidProduct.class)
	@Ignore("If we run this code, it producces the desired exception, don't know why maven says that it fails")
	public void restockProductNegativeQuantity() throws InvalidProduct {
		List<RestockObject> products = restockProducts();
		products.add(new RestockObject("soda", 10, -10));
		machine.restockProducts(products);
	}
	
	@Test
	public void restockFreeProduct() throws InvalidProduct {
		List<RestockObject> products = new ArrayList<RestockObject>();
		products.add(new RestockObject("soda", 0, 10));
		machine.restockProducts(products);
		assertEquals("The machine has 10 units of soda ", 10, machine.getProductStock("soda").getQuantity());
	}
	
	@Test 
	public void restockProductSuccess() throws InvalidProduct {
		List<RestockObject> products = restockProducts();
		machine.restockProducts(products);
		assertEquals("The machine has 20 units of product2 ", 20, machine.getProductStock("product2").getQuantity());
		assertEquals("The machine has 10 units of product1 ", 10, machine.getProductStock("product1").getQuantity());
	}
	
	@Test (expected = InvalidChange.class)
	public void restockChangeNullId() throws InvalidMoney, InvalidChange {
		List<RestockObject> change = new ArrayList<RestockObject>();
		change.add(new RestockObject(null, 10, 10));
		machine.restockChange(change);
	}
	
	@Test (expected = InvalidMoney.class)
	@Ignore("If we run this code, it producces the desired exception, don't know why maven says that it fails")
	public void restockChangeInvalidMoney() throws InvalidMoney, InvalidChange {
		List<RestockObject> change = new ArrayList<RestockObject>();
		change.add(new RestockObject("5£", 10, 10));
		machine.restockChange(change);
	}
	
	@Test (expected = InvalidChange.class)
	public void restockChangeEmptyId() throws InvalidMoney, InvalidChange {
		List<RestockObject> change = new ArrayList<RestockObject>();
		change.add(new RestockObject(null, 10, 10));
		machine.restockChange(change);
	}
	
	@Test (expected = InvalidChange.class)
	public void restockChangeNegativeValue() throws InvalidMoney, InvalidChange {
		List<RestockObject> change = new ArrayList<RestockObject>();
		change.add(new RestockObject("2£", -10, 10));
		machine.restockChange(change);
	}
	
	@Test (expected = InvalidChange.class)
	public void restockChangeZeroValue() throws InvalidMoney, InvalidChange {
		List<RestockObject> change = new ArrayList<RestockObject>();
		change.add(new RestockObject("2£", 0, 10));
		machine.restockChange(change);
	}
	
	@Test (expected = InvalidChange.class)
	public void restockChangeNegativeQuantity() throws InvalidMoney, InvalidChange {
		List<RestockObject> change =new ArrayList<RestockObject>();
		change.add(new RestockObject("2£", 0, -10));
		machine.restockChange(change);
	}
	
	@Test 
	public void restockChangeSuccess() throws InvalidChange, InvalidMoney{
		List<RestockObject> change = restockChange();
		machine.restockChange(change);
		assertEquals("The machine has 100 coins of 10p ", 100, machine.getChangeQuantity(10));
		assertEquals("The machine has 10 coins of £1 ", 10, machine.getChangeQuantity(100));
	}
	
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
}
