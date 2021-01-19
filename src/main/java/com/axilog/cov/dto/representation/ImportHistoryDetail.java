package com.axilog.cov.dto.representation;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ImportHistoryDetail {
	
	    private String fileName;
	    private String jobId;
	    private String outlet;
		
	    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	    private Date importedAt;
	    
	    private String imported_by;
	    private String status;
	    private String message;
	    private String nupcoCode;
	    private String result;
	

	
	 

}
