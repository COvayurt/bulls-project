package model;

import constant.Constants;
import util.Utils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class BullsTickerDetail {

	private String tickerShortCode;
	private String tickerLongName;
	private String lastSignal;
	private String lastFormation;
	private BigDecimal lastPriceInTL;
	private List<BullsTickerSignalHistory> bullsTickerSignalHistoryList;
	private double sixMonthsSuccessRate;
	private double oneYearSuccessRate;
	private double twoYearsSuccessRate;
	private String bullUrl;
	private BigDecimal sixMonthIncome;
	private BigDecimal oneYearIncome;
	private BigDecimal twoYearsIncome;


	public BigDecimal getSixMonthIncome() {
		return sixMonthIncome;
	}

	public void setSixMonthIncome(BigDecimal sixMonthIncome) {
		this.sixMonthIncome = sixMonthIncome;
	}

	public BigDecimal getOneYearIncome() {
		return oneYearIncome;
	}

	public void setOneYearIncome(BigDecimal oneYearIncome) {
		this.oneYearIncome = oneYearIncome;
	}

	public BigDecimal getTwoYearsIncome() {
		return twoYearsIncome;
	}

	public void setTwoYearsIncome(BigDecimal twoYearsIncome) {
		this.twoYearsIncome = twoYearsIncome;
	}

	public String getTickerShortCode() {
		return tickerShortCode;
	}

	public void setTickerShortCode(String tickerShortCode) {
		this.tickerShortCode = tickerShortCode;
	}

	public String getTickerLongName() {
		return tickerLongName;
	}

	public void setTickerLongName(String tickerLongName) {
		this.tickerLongName = tickerLongName;
	}

	public String getLastSignal() {
		return lastSignal;
	}

	public void setLastSignal(String lastSignal) {
		this.lastSignal = lastSignal;
	}

	public String getLastFormation() {
		return lastFormation;
	}

	public void setLastFormation(String lastFormation) {
		this.lastFormation = lastFormation;
	}

	public BigDecimal getLastPriceInTL() {
		return lastPriceInTL;
	}

	public void setLastPriceInTL(BigDecimal lastPriceInTL) {
		this.lastPriceInTL = lastPriceInTL;
	}

	public List<BullsTickerSignalHistory> getBullsTickerSignalHistoryList() {
		return bullsTickerSignalHistoryList;
	}

	public void setBullsTickerSignalHistoryList(List<BullsTickerSignalHistory> bullsTickerSignalHistoryList) {
		this.bullsTickerSignalHistoryList = bullsTickerSignalHistoryList;
		calculateSuccessRateForTimes();
	}

	public double getSixMonthsSuccessRate() {
		return sixMonthsSuccessRate;
	}

	public void setSixMonthsSuccessRate(double sixMonthsSuccessRate) {
		this.sixMonthsSuccessRate = sixMonthsSuccessRate;
	}

	public double getOneYearSuccessRate() {
		return oneYearSuccessRate;
	}

	public void setOneYearSuccessRate(double oneYearSuccessRate) {
		this.oneYearSuccessRate = oneYearSuccessRate;
	}

	public double getTwoYearsSuccessRate() {
		return twoYearsSuccessRate;
	}

	public void setTwoYearsSuccessRate(double twoYearsSuccessRate) {
		this.twoYearsSuccessRate = twoYearsSuccessRate;
	}

	public String getBullUrl() {
		return bullUrl;
	}

	public void setBullUrl(String bullUrl) {
		this.bullUrl = bullUrl;
	}

	private void calculateSuccessRateForTimes() {

		Calendar today = Calendar.getInstance();
		Calendar sixMonthsAgo = Calendar.getInstance();
		Calendar oneYearAgo = Calendar.getInstance();
		Calendar twoYearsAgo = Calendar.getInstance();
		today.setTime(new Date());
		sixMonthsAgo.setTime(new Date());
		oneYearAgo.setTime(new Date());
		twoYearsAgo.setTime(new Date());

		sixMonthsAgo.add(Calendar.MONTH, -6);
		oneYearAgo.add(Calendar.YEAR, -1);
		twoYearsAgo.add(Calendar.YEAR, -2);

		int totalCount = 0;
		int successCount = 0;

		boolean sixMonthsBefore = false;
		boolean oneYearBefore = false;
		
		for (int index = 0; index < bullsTickerSignalHistoryList.size(); index++) {
			Date date = Utils.isDateWithFormatReturnDate(bullsTickerSignalHistoryList.get(index).getDate(),
					Constants.DATE_FORMAT_SITE);

			if (date.before(sixMonthsAgo.getTime()) && !sixMonthsBefore) {
				sixMonthsBefore = Boolean.TRUE;
				if (totalCount != 0) {
					sixMonthsSuccessRate = ((double) successCount / (double) totalCount) * 100.0;
				} else {
					sixMonthsSuccessRate = 0.0;
				}
			} else if (date.before(oneYearAgo.getTime()) && !oneYearBefore) {
				oneYearBefore = Boolean.TRUE;
				if (totalCount != 0) {
					oneYearSuccessRate = ((double) successCount / (double) totalCount) * 100.0;
				} else {
					oneYearSuccessRate = 0.0;
				}
			} else if (date.before(twoYearsAgo.getTime())) {
				if (totalCount != 0) {
					twoYearsSuccessRate = ((double) successCount / (double) totalCount) * 100.0;
				} else {
					twoYearsSuccessRate = 0.0;
				}
				break;
			}
		
			if (bullsTickerSignalHistoryList.get(index).isCouldEarnMoney()) {
				successCount++;
			}

			totalCount++;
		}
		
		if(sixMonthsSuccessRate == 0.0 && totalCount != 0){
			sixMonthsSuccessRate = ((double) successCount / (double) totalCount) * 100.0;
		}
		if(oneYearSuccessRate == 0.0 && totalCount != 0){
			oneYearSuccessRate = ((double) successCount / (double) totalCount) * 100.0;
		}
		if(twoYearsSuccessRate == 0.0 && totalCount != 0){
			twoYearsSuccessRate = ((double) successCount / (double) totalCount) * 100.0;
		}

	}

	@Override
	public String toString() {
		return "[\ntickerShortCode=" + tickerShortCode + ",\ntickerLongName=" + tickerLongName + ", \nlastSignal="
				+ lastSignal + ",\nlastFormation=" + lastFormation + ",\nlastPriceInTL=" + lastPriceInTL
				+ ",\nsixMonthsSuccessRate=%" + sixMonthsSuccessRate + ",\noneYearSuccessRate=%" + oneYearSuccessRate
				+ ",\ntwoYearsSuccessRate=%" + twoYearsSuccessRate + ",\nsixMonthIncome=" + sixMonthIncome + ",\noneYearIncome=" + oneYearIncome 
				+ ",\ntwoYearsIncome=" + twoYearsIncome + ",\nbullUrl=" + bullUrl + "\n]\n\n";
	}

}
