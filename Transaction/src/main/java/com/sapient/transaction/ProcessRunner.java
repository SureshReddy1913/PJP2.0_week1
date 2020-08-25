package com.sapient.transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessRunner {

	public static void main(String[] args) {
		
	FeeProcessing feeProcessing = new FeeProcessing();
	FileIO fileIO=new FileIO();
	List<InputTransaction> inputTransactions=new ArrayList<>();
	Map<OutputTransaction, Integer> map=new HashMap<>();
	
	
	inputTransactions=fileIO.get_transactions();
	map = feeProcessing.feeProcessing(inputTransactions);
	
	ArrayList<OutputTransaction> ar = new ArrayList<OutputTransaction>(map.keySet());
	Collections.sort(ar, new SortClass()); 
	
	fileIO.enterTransaction(ar,map);
	}
}

class SortClass implements Comparator<OutputTransaction>{
	public int compare(OutputTransaction ot1,OutputTransaction ot2) {
		if(ot1.getClient_id().equals(ot2.getClient_id())){
			if(ot1.getTrans_type().equals(ot2.getTrans_type())) {
				if(ot1.getTrans_date().equals(ot2.getTrans_date())) {
					return (ot1.getPriority().compareTo(ot2.getPriority()));
				}
				return (ot1.getTrans_date().compareTo(ot2.getTrans_date()));
			}
			return (ot1.getTrans_type().compareTo(ot2.getTrans_type()));
		}
		return (ot1.getClient_id().compareTo(ot2.getClient_id()));
	}
}


