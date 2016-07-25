package vendingmachine.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import vendingmachine.VendingMachine;
import vendingmachine.exception.*;
import vendingmachine.model.BritishCoins;
import vendingmachine.model.RestockObject;

public class StockedMachineTest {
	private static VendingMachine machine;
	
	@Before
	public void prestineVendingMachine() throws InvalidProduct, InvalidMoney, InvalidChange{
		machine = new VendingMachine(new BritishCoins());
		List<RestockObject> products = new ArrayList<RestockObject>();
		List<RestockObject> change = new ArrayList<RestockObject>();
		
		products.add(new RestockObject("soda", 44, 10));
		products.add(new RestockObject("fries", 1, 10));
		machine.restockProducts(products);
		
		change.add(new RestockObject("£1", 100, 10));
		change.add(new RestockObject("50p", 50, 10));
		change.add(new RestockObject("20p", 20, 10));
		change.add(new RestockObject("10p", 10, 50));
		change.add(new RestockObject("5p", 5, 50));
		change.add(new RestockObject("2p", 2, 50));
		change.add(new RestockObject("1p", 1, 1));
		machine.restockChange(change);

	}
	
	@Test 
	public void selectInvalidProduct() {
		String product = "burger";
		assertEquals("Product it's not available", product + " is unnavailable", machine.selectProduct(product));		
	}
	
	@Test
	public void buyProduct() throws InvalidMoney, InvalidChange, InvalidProduct {
		String product = "soda";
		assertEquals("Product it's available", product, machine.selectProduct(product));
		assertEquals("Added 1£ and the inserted value is 100", 100, machine.addMoney("£1"));
		assertEquals("Added 1£ and the inserted value is 200", 200, machine.addMoney("£1"));
		assertEquals("Get The product and the change", "Product:soda Change:£1 50p 5p 1p ", machine.getProduct());
	}
	
	@Test
	public void resetMoney() throws InvalidMoney{
		assertEquals("Added 1£ and the inserted value is 100", 100, machine.addMoney("£1"));
		assertEquals("Added 1£ and the inserted value is 200", 200, machine.addMoney("£1"));
		assertEquals("Get back the money inserted", "£1 £1 ", machine.resetMoney());
	}
	
	@Test (expected = InvalidChange.class)
	public void unavailableChange() throws InvalidMoney, InvalidChange, InvalidProduct {
		String product = "soda";
		assertEquals("Product it's available", product, machine.selectProduct(product));
		assertEquals("Added 1£ and the inserted value is 100", 100, machine.addMoney("£1"));
		assertEquals("Get The product and the change", "Product:soda Change:50p 5p 1p ", machine.getProduct());
		assertEquals("Product it's available", product, machine.selectProduct(product));
		assertEquals("Added 1£ and the inserted value is 100", 100, machine.addMoney("£1"));
		assertEquals("Get The product and the change", "Product:soda Change:50p 5p 1p ", machine.getProduct());
	}
}
