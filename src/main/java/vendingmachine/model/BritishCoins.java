package vendingmachine.model;

import vendingmachine.enumeration.BritishCoinsEnum;
import vendingmachine.enumeration.MoneyEnum;
import vendingmachine.exception.InvalidMoney;

public class BritishCoins implements Money{
	
	public MoneyEnum mapMoney(String denomination) throws InvalidMoney{
		for (BritishCoinsEnum coin: BritishCoinsEnum.values()){
			if (coin.getDenomination().equals(denomination)){
				return coin;
			}
		}
		throw new InvalidMoney();
	}

}
