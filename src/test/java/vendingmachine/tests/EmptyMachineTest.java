package vendingmachine.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import vendingmachine.VendingMachine;
import vendingmachine.exception.*;
import vendingmachine.model.BritishCoins;

public class EmptyMachineTest {
	private static VendingMachine machine = new VendingMachine(new BritishCoins());
	private static String productId = "soda";
	private static String coin = "£2";
	
	@Test
	public void selectProduct() {
		assertEquals("There's no product yet", productId + " is unnavailable", machine.selectProduct(productId));
	}
	
	@Test  (expected = InvalidProduct.class)
	public void getSelectProduct() throws InvalidChange, InvalidProduct {
		assertEquals("There's no select product", productId + " is unnavailable", machine.getProduct());
	}
	
	@Test
	public void resetMoneyNoMoney() {
		assertEquals("No money has been inserted", "", machine.resetMoney());
	}
	
	@Test (expected = InvalidMoney.class)
	public void addInvalidMoney() throws InvalidMoney {
		assertEquals("Unexpected currency", "", machine.addMoney("escudo"));
	}
	
	@Test
	public void addMoney() throws InvalidMoney {
		assertEquals("The user inserted £2", 200, machine.addMoney(coin));
		machine.resetMoney();
	}
	
	@Test
	public void resetMoneyWithMoney() throws InvalidMoney {
		machine.addMoney(coin);
		assertEquals("The user inserted £2 and it's getting them back", "£2 ", machine.resetMoney());
	}
}
