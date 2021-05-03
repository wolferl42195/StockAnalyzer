package stockanalyzer.ctrl;

import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.YahooResponse;
import yahooApi.yahooFinanceIOException;
import yahoofinance.Stock;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Controller {

	private Stock s;
		
	public void process(String ticker) throws yahooFinanceIOException {
		System.out.println("Start process");

		try{

		s = YahooFinance.get(ticker);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -30);

		var max = s.getHistory(cal, Interval.DAILY).stream()
				.mapToDouble(i -> i.getClose().doubleValue())
				.max()
				.orElse(0.00d);

		var avg = s.getHistory().stream()
				.mapToDouble(i -> i.getClose().doubleValue())
				.average()
				.orElse(0.00d);


		var amount = s.getHistory().stream()
				.mapToDouble(i -> i.getClose().doubleValue())
				.count();

		//SIMPLE AUSGABE
		System.out.println(ticker);
		System.out.println("max: " + max);
		System.out.println("avg: " + avg);
		System.out.println("amount: " + amount);

		} catch (IOException e){
			e.printStackTrace();
		}
	}
	

	public Object getData(String searchString) {

		/*
		YahooFinance yahooFinance = new YahooFinance();
		List<String> tickers = Arrays.asList("searchString");
		YahooResponse response = yahooFinance.getCurrentData(tickers);
		QuoteResponse quotes = response.getQuoteResponse();
		quotes.getResult().stream().forEach(quote -> System.out.println(quote.getAsk()));

		yahooFinance.getCurrentData(Arrays.asList("OMV.VI",
				"EBS.VI","DOC.VI","SBO.VI","RBI.VI","VIG.VI","TKA.VI","VOE.VI","FACC.VI","ANDR.VI","VER.VI",
				"WIE.VI","CAI.VI","BG.VI","POST.VI","LNZ.VI","UQA.VI","SPI.VI","ATS.VI","IIA.VI"));
		*/

		return null;
	}


	public void closeConnection() {
		
	}
}
