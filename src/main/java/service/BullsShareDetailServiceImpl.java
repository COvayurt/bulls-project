package service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import constant.Constants;
import model.BullsShareDetail;
import model.BullsShareSignalHistory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.api.BullsShareDetailService;
import service.api.JSoupManagerService;
import util.Utils;


public class BullsShareDetailServiceImpl implements BullsShareDetailService {

	JSoupManagerService jSoupManagerService = new JSoupManagerServiceImpl();

	public void extractBullsShareDetailByUrl(String url) {
		Document doc = jSoupManagerService.getDocumentFromUrl(url);
		BullsShareDetail bullsShareDetail = new BullsShareDetail();
		if (doc != null) {

			String shareShortCode = getShareShortCodeFromDoc(doc);
			if (shareShortCode != null && !shareShortCode.trim().equals("")) {
				bullsShareDetail.setShareShortCode(shareShortCode);
			}

			String shareLastSignal = getShareLastSignalFromDoc(doc);
			if (shareLastSignal != null && !shareLastSignal.trim().equals("")) {
				bullsShareDetail.setLastSignal(shareLastSignal);
			}

			String shareLongName = getShareLongName(doc);
			if (shareLongName != null && !shareLongName.trim().equals("")) {
				bullsShareDetail.setShareLongName(shareLongName);
			}

			String shareLastPriceInTL = getShareLastPriceInTL(doc);
			if (shareLastPriceInTL != null && !shareLastPriceInTL.trim().equals("")) {
				bullsShareDetail.setLastPriceInTL(new BigDecimal(shareLastPriceInTL));
			}

			String shareLastFormation = getShareLastFormation(doc);
			if (shareLastFormation != null && !shareLastFormation.trim().equals("")) {
				bullsShareDetail.setLastFormation(shareLastFormation);
			}

			List<BullsShareSignalHistory> bullsShareSignalHistoryList = getShareSignalHistory24List(doc);

			bullsShareDetail.setBullsShareSignalHistoryList(bullsShareSignalHistoryList);
			bullsShareDetail.setBullUrl(url);

			bullsShareDetail
					.setSixMonthIncome(getSignalHistoryIncomeList(doc, Constants.SHARE_SIGNAL_HISTORY_TABLE_06_ID));
			bullsShareDetail
					.setOneYearIncome(getSignalHistoryIncomeList(doc, Constants.SHARE_SIGNAL_HISTORY_TABLE_12_ID));
			if (bullsShareSignalHistoryList.size() > 0
					&& bullsShareSignalHistoryList.get(0).getPriceIfDoneWhatTold() != null
					&& bullsShareSignalHistoryList.get(bullsShareSignalHistoryList.size() - 1)
							.getPriceIfDoneWhatTold() != null) {
				bullsShareDetail.setTwoYearsIncome(
						bullsShareSignalHistoryList.get(0).getPriceIfDoneWhatTold().subtract(bullsShareSignalHistoryList
								.get(bullsShareSignalHistoryList.size() - 1).getPriceIfDoneWhatTold()));
			}
			System.out.println(bullsShareDetail.toString());
		}
	}

	private String getShareLastPriceInTL(Document doc) {
		Elements condition = doc.select("span[id$=" + Constants.SHARE_LAST_PRICE_IN_TL_SPAN_ID + "]");
		if (!condition.isEmpty()) {
			return condition.get(0).text();
		}
		return null;
	}

	private String getShareLastFormation(Document doc) {
		Elements condition = doc.select("span[id$=" + Constants.SHARE_LAST_FORMATION_SPAN_ID + "]");
		if (!condition.isEmpty()) {
			return condition.get(0).text();
		}
		return null;
	}

	private String getShareLongName(Document doc) {
		Elements condition = doc.select("span[id$=" + Constants.SHARE_LONG_NAME_SPAN_ID + "]");
		if (!condition.isEmpty()) {
			return condition.get(0).text();
		}
		return null;
	}

	private String getShareShortCodeFromDoc(Document doc) {
		Elements condition = doc.select("span[id$=" + Constants.SHARE_SHORT_CODE_SPAN_ID + "]");
		if (!condition.isEmpty()) {
			return condition.get(0).text();
		}
		return null;
	}

	private String getShareLastSignalFromDoc(Document doc) {
		Elements condition = doc.select("span[id$=" + Constants.SHARE_LAST_SIGNAL_SPAN_ID + "]");
		if (!condition.isEmpty()) {
			return condition.get(0).text();
		}
		return null;
	}

	private BigDecimal getSignalHistoryIncomeList(Document doc, String tableId) {
		Elements condition = doc.select("table[id$=" + tableId + "]");
		List<BullsShareSignalHistory> bullsShareSignalHistoryList = new ArrayList<>();
		Elements listElements = condition.get(0).children().get(0).children();

		for (int index = 1; index < listElements.size(); index++) {
			Elements tdElements = listElements.get(index).children();
			BullsShareSignalHistory bullsShareSignalHistory = new BullsShareSignalHistory();

			for (int indexTd = 0; indexTd < tdElements.size(); indexTd++) {
				Element tdElement = tdElements.get(indexTd);
				String text = tdElement.text();
				if (text != null && !text.trim().equals("")) {
					if (text.contains("/") && Utils.isDateWithFormat(text, Constants.DATE_FORMAT_SITE)) {
						bullsShareSignalHistory.setDate(text);
					} else if (text.contains(".") && Utils.isAmountShareValue(text)) {
						try {
							bullsShareSignalHistory.setPrice(new BigDecimal(text.replaceAll(",", "")));
						} catch (Exception e) {
							System.out.println("setPrice error " + text);
						}
					} else if (text.contains(".") && Utils.isAmountShareCapitalValue(text)) {
						try {
							bullsShareSignalHistory.setPriceIfDoneWhatTold(new BigDecimal(text.replaceAll(",", "")));
						} catch (Exception e) {
							System.out.println("setPriceIfDoneWhatTold error " + text);
						}
					} else if (Constants.SHARE_SIGNAL_HISTORY_ACTIONS.containsKey(text.trim())) {
						bullsShareSignalHistory.setSignal(text.trim());
					}

				} else {
					Element tdElementImg = tdElement.child(0);
					String attributeSrc = tdElementImg.attr("src");
					bullsShareSignalHistory.setCouldEarnMoney(!attributeSrc.toLowerCase().contains("uncheck"));
				}
			}

			bullsShareSignalHistoryList.add(bullsShareSignalHistory);
		}

		if (bullsShareSignalHistoryList.size() > 0
				&& bullsShareSignalHistoryList.get(0).getPriceIfDoneWhatTold() != null && bullsShareSignalHistoryList
						.get(bullsShareSignalHistoryList.size() - 1).getPriceIfDoneWhatTold() != null) {
			return bullsShareSignalHistoryList.get(0).getPriceIfDoneWhatTold().subtract(
					bullsShareSignalHistoryList.get(bullsShareSignalHistoryList.size() - 1).getPriceIfDoneWhatTold());
		}
		return new BigDecimal("0");
	}

	private List<BullsShareSignalHistory> getShareSignalHistory24List(Document doc) {
		Elements condition = doc.select("table[id$=" + Constants.SHARE_SIGNAL_HISTORY_TABLE_24_ID + "]");
		List<BullsShareSignalHistory> bullsShareSignalHistoryList = new ArrayList<>();
		Elements listElements = condition.get(0).children().get(0).children();

		for (int index = 1; index < listElements.size(); index++) {
			Elements tdElements = listElements.get(index).children();
			BullsShareSignalHistory bullsShareSignalHistory = new BullsShareSignalHistory();

			for (int indexTd = 0; indexTd < tdElements.size(); indexTd++) {
				Element tdElement = tdElements.get(indexTd);
				String text = tdElement.text();
				if (text != null && !text.trim().equals("")) {
					if (text.contains("/") && Utils.isDateWithFormat(text, Constants.DATE_FORMAT_SITE)) {
						bullsShareSignalHistory.setDate(text);
					} else if (text.contains(".") && Utils.isAmountShareValue(text)) {
						try {
							bullsShareSignalHistory.setPrice(new BigDecimal(text.replaceAll(",", "")));
						} catch (Exception e) {
							System.out.println("setPrice error " + text);
						}
					} else if (text.contains(".") && Utils.isAmountShareCapitalValue(text)) {
						try {
							bullsShareSignalHistory.setPriceIfDoneWhatTold(new BigDecimal(text.replaceAll(",", "")));
						} catch (Exception e) {
							System.out.println("setPriceIfDoneWhatTold error " + text);
						}
					} else if (Constants.SHARE_SIGNAL_HISTORY_ACTIONS.containsKey(text.trim())) {
						bullsShareSignalHistory.setSignal(text.trim());
					}

				} else {
					Element tdElementImg = tdElement.child(0);
					String attributeSrc = tdElementImg.attr("src");
					bullsShareSignalHistory.setCouldEarnMoney(!attributeSrc.toLowerCase().contains("uncheck"));
				}
			}

			bullsShareSignalHistoryList.add(bullsShareSignalHistory);
		}

		return bullsShareSignalHistoryList;
	}
}
