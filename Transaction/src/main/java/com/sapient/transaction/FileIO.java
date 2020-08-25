package com.sapient.transaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.opencsv.CSVWriter;

public class FileIO {
	
	private static String input_file = "Sample_Data_Fee_Calculator.csv";
	private static String output_file = "output.csv";
	public List<InputTransaction> get_transactions(){
		
		List<InputTransaction> inputTransactions=new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(input_file))) {
			String data;
			int count = 0;
			while((data = br.readLine()) != null) {
				count++;
				if(count == 1)
					continue;
				
				InputTransaction inputTransaction = new InputTransaction();
				String [] row = data.split(","); 
				inputTransaction.setTrans_id(row[0]);
				inputTransaction.setClient_id(row[1]);
				inputTransaction.setSecurity_id(row[2]);
				inputTransaction.setTransaction_type(row[3]);
				inputTransaction.setTrans_date(new SimpleDateFormat("M/dd/yyyy")
						.parse(row[4]));
				inputTransaction.setMarket_value(Double.parseDouble(row[5]));
				inputTransaction.setPriority_flag(row[6]);
				inputTransactions.add(inputTransaction);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return inputTransactions;
	}
	
	
	public void enterTransaction(ArrayList<OutputTransaction> ar, Map<OutputTransaction, Integer> map) {
		File file = new File(output_file);
		try (CSVWriter csvWriter = new CSVWriter(new FileWriter(file))) {
			
			String [] list = {"Client Id", "Transaction Type", "Transaction Date",
					"Priority", "Processing Fee"};
			csvWriter.writeNext(list);
			for (OutputTransaction ot : ar) {
				String [] row = new String[5];
				row[0] = ot.getClient_id(); 
				row[1] = ot.getTrans_type(); 
				row[2] = getDateAsString(ot.getTrans_date()); 
				row[3] = ot.getPriority();
				row[4] = Integer.toString(map.get(ot));
				csvWriter.writeNext(row);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String getDateAsString(Date trans_date) {
		return new SimpleDateFormat("M/dd/yyyy").format(trans_date);
	}

}
