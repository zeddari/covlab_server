package com.axilog.cov.dto.topology.command;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrafficDto {

	private String timestamp;
	private double incomingTotal;
	private double outcomingTotal;
	private double incomingAccess;
	private double outcomingAccess;
	private double incomingCustomer;
	private double outcomingCustomer;
	
	@Override
	public String toString() {
		DecimalFormat formatter = (DecimalFormat)NumberFormat.getNumberInstance(Locale.US);
		formatter.applyPattern("###.#");
		return "<li>TimeStamp: <b>"+this.timestamp+"</b></li>"
				+ "<li>Incoming Total Traffic: <b>"+formatter.format(this.incomingTotal)+" GB</b></li>"
				+ "<li>Outoming Total Tarffic: <b>"+formatter.format(this.outcomingTotal)+" GB</b></li><br>"
				+ "<li>Incoming Access Traffic: <b>"+formatter.format(this.incomingAccess)+" GB</b></li>"
				+ "<li>Outoming Access Traffic: <b>"+formatter.format(this.outcomingAccess)+" GB</b></li><br>"
				+ "<li>Incoming Customer Traffic: <b>"+formatter.format(this.incomingCustomer)+" GB</b></li>"
				+ "<li>Outoming Customer Traffic: <b>"+formatter.format(this.outcomingCustomer)+" GB</b></li><br>";
	}
	
	
	public Map<String, String> toMap() {
		DecimalFormat formatter = (DecimalFormat)NumberFormat.getNumberInstance(Locale.US);
		formatter.applyPattern("###.#");
		SortedMap<String, String> vals = new TreeMap<>();
		
		 vals.put("01- TimeStamp", this.timestamp);
		 vals.put("04- Incoming Total Traffic", formatter.format(this.incomingTotal));
		 vals.put("05- Outcoming Total Tarffic", formatter.format(this.outcomingTotal));
		 vals.put("06- Incoming Access Traffic", formatter.format(this.incomingAccess));
		 vals.put("07- Outcoming Access Traffic", formatter.format(this.outcomingAccess));
		 vals.put("08- Incoming Customer Traffic", formatter.format(this.incomingCustomer));
		 vals.put("09- Outcoming Customer Traffic", formatter.format(this.outcomingCustomer));
		return vals;
	}
}
