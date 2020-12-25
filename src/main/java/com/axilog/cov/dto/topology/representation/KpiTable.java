package com.axilog.cov.dto.topology.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KpiTable {

	String nb_2g_sites;
	String tch_2g_sites; 		//sum tch 
	String nb_3g_sites;
	String tpt_3g_sites;		//max
	String nb_tdd_sites;		
	String tpt_tdd_sites; 		//max
	String nb_fdd_sites; 
	String tpt_fdd_sites;		//max
	String nb_link;				//nb_edge
	String utiliz_lnk;			//max_utilisation on the network
	String utiliz_csg_upe_lnk;	       //max_utilisation on the network fro csg upe
	String nb_network_sites; 
	String tpt_network;			//max des max
	
}
