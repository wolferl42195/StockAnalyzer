package stockanalyzer.ui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import stockanalyzer.ctrl.Controller;
import stockanalyzer.downloader.Downloader;
import stockanalyzer.downloader.ParallelDownloader;
import stockanalyzer.downloader.SequentialDownloader;
import yahooApi.yahooFinanceIOException;

public class UserInterface 
{

	private Controller ctrl = new Controller();

	public void getDataFromCtrl1(){
		try {
			ctrl.process("MSFT");
		} catch (yahooFinanceIOException e) {
			e.printStackTrace();
		}
	}

	public void getDataFromCtrl2(){
		try {
			ctrl.process("GOOG");
		} catch (yahooFinanceIOException e) {
			e.printStackTrace();
		}
	}

	public void getDataFromCtrl3(){
		try {
			ctrl.process("SONY");
		} catch (yahooFinanceIOException e) {
			e.printStackTrace();
		}
	}

	public void getDataForCustomInput() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Geben Sie die gewünschte Aktie ein: ");
		String userinputstock = sc.next();

		try {
			ctrl.process(userinputstock);
		} catch (yahooFinanceIOException e) {
			e.printStackTrace();
		}

	}

	public void getDownloadedTickers(){
		try {
			Downloader downloader;
			List<String> tickers = new ArrayList<>();

			Scanner sc = new Scanner(System.in);
			System.out.println("Geben Sie die gewünschten Aktie ein und tippen Sie nach jeder Angabe auf [ENTER]. Zum Bestätigen drücken Sie [.]: ");

			while (true) {
				String ticker = sc.next();
				if (ticker.equals("."))
					break;
				else{
					tickers.add(ticker);
				}
			}

			System.out.print("Für sequenziellen Download: [S] ; Für parallelen Download: [P]: ");

			String downloadselection = sc.next();
			if (downloadselection.equals("S") || downloadselection.equals("s")){
				downloader = new SequentialDownloader();
				System.out.println("Sequential Download started...");
			}
			else {
				downloader = new ParallelDownloader();
				System.out.println("Parallel Download started...");
			}

			ctrl.downloadtickers(tickers, downloader);

		} catch (yahooFinanceIOException e) {
			e.printStackTrace();
		}
	}



	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interface");
		menu.setTitel("Wählen Sie aus:");
		menu.insert("a", "Choice 1", this::getDataFromCtrl1);
		menu.insert("b", "Choice 2", this::getDataFromCtrl2);
		menu.insert("c", "Choice 3", this::getDataFromCtrl3);
		menu.insert("d", "Choice User Imput:",this::getDataForCustomInput);
		menu.insert("e", "Download Tickers:",this::getDownloadedTickers);
		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		ctrl.closeConnection();
		System.out.println("Program finished");
	}


	protected String readLine() 
	{
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
		} catch (IOException e) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 
	{
		Double number = null;
		while(number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
			}catch(NumberFormatException e) {
				number=null;
				System.out.println("Please enter a valid number:");
				continue;
			}
			if(number<lowerlimit) {
				System.out.println("Please enter a higher number:");
				number=null;
			}else if(number>upperlimit) {
				System.out.println("Please enter a lower number:");
				number=null;
			}
		}
		return number;
	}
}
