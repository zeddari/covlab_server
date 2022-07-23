package com.axilog.cov.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ImportHistory.class)
public abstract class ImportHistory_ {

	public static volatile SingularAttribute<ImportHistory, String> result;
	public static volatile SingularAttribute<ImportHistory, String> jobId;
	public static volatile SingularAttribute<ImportHistory, String> fileName;
	public static volatile SingularAttribute<ImportHistory, Date> importedAt;
	public static volatile SingularAttribute<ImportHistory, String> nupcoCode;
	public static volatile SingularAttribute<ImportHistory, Integer> id;
	public static volatile SingularAttribute<ImportHistory, String> outlet;
	public static volatile SingularAttribute<ImportHistory, String> message;
	public static volatile SingularAttribute<ImportHistory, String> imported_by;
	public static volatile SingularAttribute<ImportHistory, String> status;

	public static final String RESULT = "result";
	public static final String JOB_ID = "jobId";
	public static final String FILE_NAME = "fileName";
	public static final String IMPORTED_AT = "importedAt";
	public static final String NUPCO_CODE = "nupcoCode";
	public static final String ID = "id";
	public static final String OUTLET = "outlet";
	public static final String MESSAGE = "message";
	public static final String IMPORTED_BY = "imported_by";
	public static final String STATUS = "status";

}

