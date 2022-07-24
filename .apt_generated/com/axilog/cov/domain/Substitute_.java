package com.axilog.cov.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Substitute.class)
public abstract class Substitute_ {

	public static volatile SingularAttribute<Substitute, String> substituteCategory;
	public static volatile SingularAttribute<Substitute, Product> product;
	public static volatile SingularAttribute<Substitute, Double> impactFactor;
	public static volatile SingularAttribute<Substitute, Long> id;
	public static volatile SingularAttribute<Substitute, String> substituteCode;
	public static volatile SingularAttribute<Substitute, String> substituteDescription;

	public static final String SUBSTITUTE_CATEGORY = "substituteCategory";
	public static final String PRODUCT = "product";
	public static final String IMPACT_FACTOR = "impactFactor";
	public static final String ID = "id";
	public static final String SUBSTITUTE_CODE = "substituteCode";
	public static final String SUBSTITUTE_DESCRIPTION = "substituteDescription";

}

