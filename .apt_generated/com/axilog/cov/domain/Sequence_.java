package com.axilog.cov.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Sequence.class)
public abstract class Sequence_ {

	public static volatile SingularAttribute<Sequence, Long> id;
	public static volatile SingularAttribute<Sequence, Long> currentNumber;

	public static final String ID = "id";
	public static final String CURRENT_NUMBER = "currentNumber";

}

