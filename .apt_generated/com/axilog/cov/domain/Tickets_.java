package com.axilog.cov.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Tickets.class)
public abstract class Tickets_ {

	public static volatile SingularAttribute<Tickets, Product> product;
	public static volatile SingularAttribute<Tickets, LocalDate> ticketDueDate;
	public static volatile SingularAttribute<Tickets, Long> ticketNo;
	public static volatile SingularAttribute<Tickets, String> ticketPriority;
	public static volatile SingularAttribute<Tickets, LocalDate> ticketCreatedOn;
	public static volatile SingularAttribute<Tickets, String> ticketStatus;
	public static volatile SingularAttribute<Tickets, String> ticketType;
	public static volatile SingularAttribute<Tickets, LocalDate> ticketUpdateAt;
	public static volatile SingularAttribute<Tickets, Long> id;
	public static volatile SingularAttribute<Tickets, Outlet> outlet;

	public static final String PRODUCT = "product";
	public static final String TICKET_DUE_DATE = "ticketDueDate";
	public static final String TICKET_NO = "ticketNo";
	public static final String TICKET_PRIORITY = "ticketPriority";
	public static final String TICKET_CREATED_ON = "ticketCreatedOn";
	public static final String TICKET_STATUS = "ticketStatus";
	public static final String TICKET_TYPE = "ticketType";
	public static final String TICKET_UPDATE_AT = "ticketUpdateAt";
	public static final String ID = "id";
	public static final String OUTLET = "outlet";

}

