package stockanalyzer.ui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import stockanalyzer.ctrl.Controller;
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

	/*
	public void getDataFromCtrl4(String userinputstock){
		try {
			ctrl.process(userinputsock);
		} catch (yahooFinanceIOException e) {
			e.printStackTrace();
		}
	}
	 */


	public void getDataForCustomInput() {
		
	}


	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interface");
		menu.setTitel("Wählen Sie aus:");
		menu.insert("a", "Choice 1", this::getDataFromCtrl1);
		menu.insert("b", "Choice 2", this::getDataFromCtrl2);
		menu.insert("c", "Choice 3", this::getDataFromCtrl3);
		//menu.insert("d", "Choice User Imput:",this::getDataForCustomInput);
		//menu.insert("z", "Choice User Imput:", i->i::getDataFromCtrl4);
		//menu.insert("z", "Choice User Imput:",this::getDataFromCtrl4);
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
