package vendingmachine.model;

import vendingmachine.enumeration.MoneyEnum;
import vendingmachine.exception.InvalidMoney;

public interface Money {
	public MoneyEnum mapMoney(String denomination) throws InvalidMoney ;
}
