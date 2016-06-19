package vendingmachine.enumeration;

public enum BritishCoinsEnum implements MoneyEnum{
	£2("£2",200), £1("£1",100), P50("50p",50), P20("20p",20), 
	P10("10p",10), P5("5p",5), P2("2p",2), P1("1p",1);
	
	private String denomination;
	private int value;
	
	BritishCoinsEnum(String denomination, int value) {
		this.denomination = denomination;
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public String getDenomination() {
		return this.denomination;
	}
}

