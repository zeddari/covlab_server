package com.axilog.cov.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DeviceOverviewStats.class)
public abstract class DeviceOverviewStats_ {

	public static volatile SingularAttribute<DeviceOverviewStats, String> serialNumber;
	public static volatile SingularAttribute<DeviceOverviewStats, Double> co2;
	public static volatile SingularAttribute<DeviceOverviewStats, Double> temperature;
	public static volatile SingularAttribute<DeviceOverviewStats, Double> humidity;
	public static volatile SingularAttribute<DeviceOverviewStats, Long> id;
	public static volatile SingularAttribute<DeviceOverviewStats, Double> pressure;
	public static volatile SingularAttribute<DeviceOverviewStats, Outlet> outlet;
	public static volatile SingularAttribute<DeviceOverviewStats, String> deviceId;
	public static volatile SingularAttribute<DeviceOverviewStats, Double> differentialPressure;
	public static volatile SingularAttribute<DeviceOverviewStats, LocalDate> timestamp;
	public static volatile SetAttribute<DeviceOverviewStats, Product> products;

	public static final String SERIAL_NUMBER = "serialNumber";
	public static final String CO2 = "co2";
	public static final String TEMPERATURE = "temperature";
	public static final String HUMIDITY = "humidity";
	public static final String ID = "id";
	public static final String PRESSURE = "pressure";
	public static final String OUTLET = "outlet";
	public static final String DEVICE_ID = "deviceId";
	public static final String DIFFERENTIAL_PRESSURE = "differentialPressure";
	public static final String TIMESTAMP = "timestamp";
	public static final String PRODUCTS = "products";

}

