package model;

import java.math.BigDecimal;

public class BullsShareSignalHistory {

	private String date;
	private BigDecimal price;
	private String signal;
	private boolean couldEarnMoney;
	private BigDecimal priceIfDoneWhatTold;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getSignal() {
		return signal;
	}
	public void setSignal(String signal) {
		this.signal = signal;
	}
	public boolean isCouldEarnMoney() {
		return couldEarnMoney;
	}
	public void setCouldEarnMoney(boolean couldEarnMoney) {
		this.couldEarnMoney = couldEarnMoney;
	}
	public BigDecimal getPriceIfDoneWhatTold() {
		return priceIfDoneWhatTold;
	}
	public void setPriceIfDoneWhatTold(BigDecimal priceIfDoneWhatTold) {
		this.priceIfDoneWhatTold = priceIfDoneWhatTold;
	}
	@Override
	public String toString() {
		return "BullsShareSignalHistory [date=" + date + ", price=" + price + ", signal=" + signal + ", couldEarnMoney="
				+ couldEarnMoney + ", priceIfDoneWhatTold=" + priceIfDoneWhatTold + "]";
	}
	
	
	
}
