package stockanalyzer.ctrl;

import yahooApi.YahooFinance;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.YahooResponse;
import yahoofinance.Stock;

import java.io.IOException;
import java.util.*;

public class Controller {
	YahooFinance yahooFinance = new YahooFinance();
	List<String> tickers = Arrays.asList("AAPL", "AMZN", "TSLA", "GOOG", "OMV.VI",
			"EBS.VI","DOC.VI","SBO.VI","RBI.VI","VIG.VI","TKA.VI","VOE.VI","FACC.VI","ANDR.VI","VER.VI",
			"WIE.VI","CAI.VI","BG.VI","POST.VI","LNZ.VI","UQA.VI","SPI.VI","ATS.VI","IIA.VI");



	public void process(String ticker) {
		System.out.println("Start process");

		Stock stock = null;
		try {
			stock = yahoofinance.YahooFinance.get("AAPL");
			stock.getHistory().forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			getData(ticker);	//1) Daten laden
		} catch (Exception e) {
			e.printStackTrace();
		}


		//2) Daten Analyse
		//Was soll man analysieren??? Angabe unschlüssig
	}
	

	public Object getData(String searchString /*Wozu? Ich kann mit dem doch keine get-Funktionen rausfiltern oder?*/) {
		//String data = yahooFinance.requestData(tickers);
		YahooResponse response = yahooFinance.getCurrentData(tickers);
		QuoteResponse quotes = response.getQuoteResponse();
		System.out.println("Aktienkurse: " + quotes.getResult().stream().count());
		System.out.println("Aktienkurse mit \"A\" im Symbol: " + quotes.getResult().stream().filter(result -> result.getSymbol().contains("A")).count());
		System.out.println("Symbol\t\tDay High\t\t50 Day Avrg\t\t");
		quotes.getResult().forEach(quote -> System.out.println(quote.getSymbol() + ":\t\t" + quote.getRegularMarketDayHigh() + " \t\t" +  quote.getFiftyDayAverage()));
		return null;
	}


	public void closeConnection() {

	}
}
