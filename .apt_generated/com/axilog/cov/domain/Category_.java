package com.axilog.cov.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Category.class)
public abstract class Category_ {

	public static volatile SingularAttribute<Category, Long> id;
	public static volatile SingularAttribute<Category, String> categoryCode;
	public static volatile SingularAttribute<Category, Long> categoryId;
	public static volatile SingularAttribute<Category, String> categoryDescription;
	public static volatile SetAttribute<Category, Product> products;

	public static final String ID = "id";
	public static final String CATEGORY_CODE = "categoryCode";
	public static final String CATEGORY_ID = "categoryId";
	public static final String CATEGORY_DESCRIPTION = "categoryDescription";
	public static final String PRODUCTS = "products";

}

