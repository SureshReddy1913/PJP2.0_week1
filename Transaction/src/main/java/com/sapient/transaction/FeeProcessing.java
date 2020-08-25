package com.sapient.transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class IntraDayCheck{
	String client_id;
	String security_id;
	Date trans_date;
	IntraDayCheck(String client_id,String security_id,Date trans_date){
		this.client_id=client_id;
		this.security_id=security_id;
		this.trans_date=trans_date;
	}
}


public class FeeProcessing {

	private static final String WITHDRAW = "WITHDRAW";
	private static final String BUY = "BUY";
	private static final String SELL = "SELL";
	private static final String Y = "Y";
	
	public Map<OutputTransaction, Integer> feeProcessing(List<InputTransaction> inputTransactions){
		List<IntraDayCheck> buyMap=new ArrayList<>();
		List<IntraDayCheck> sellMap=new ArrayList<>();
		Map<OutputTransaction, Integer> map=new HashMap<>();
		int amount;
		for(InputTransaction it: inputTransactions) {
			amount=0;
			if(it.getPriority_flag().equals(Y)) {
				amount=500;
			}
			else {
				if(it.getTransaction_type().equals(SELL)||it.getTransaction_type().equals(WITHDRAW)) {
					amount=100;
					if(it.getTransaction_type().equals(SELL)) {
						IntraDayCheck intraDayCheck=new IntraDayCheck(it.getClient_id(),
								it.getSecurity_id(),it.getTrans_date());
						if(buyMap.contains(intraDayCheck)) {
							amount=amount+10;
							buyMap.remove(intraDayCheck);
						}
						else
						{
							sellMap.add(intraDayCheck);
						}
					}
				}
				else {
					amount=50;
					if(it.getTransaction_type().equals(BUY)) {
						IntraDayCheck intraDayCheck=new IntraDayCheck(it.getClient_id(),
								it.getSecurity_id(),it.getTrans_date());
						if(sellMap.contains(intraDayCheck)) {
							amount=amount+10;
							sellMap.remove(intraDayCheck);
						}
						else
						{
							buyMap.add(intraDayCheck);
						}
					}
				}
			}
			map.put(getOutputTrans(it), amount);
		}
		
		return map;
	}

	private OutputTransaction getOutputTrans(InputTransaction it) {
		OutputTransaction ot=new OutputTransaction();
		ot.setClient_id(it.getClient_id());
		ot.setPriority(it.getPriority_flag());
		ot.setTrans_date(it.getTrans_date());
		ot.setTrans_type(it.getTransaction_type());
		return ot;
	}
	
}
