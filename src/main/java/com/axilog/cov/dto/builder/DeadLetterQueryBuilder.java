package com.axilog.cov.dto.builder;

import java.util.Iterator;
import java.util.Map.Entry;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.NativeExecutionQuery;

import com.axilog.cov.util.paging.SortCriteria;
import com.axilog.cov.util.paging.SortCriteria.Direction;

/**
 * @author a.zeddari
 *
 */
public class DeadLetterQueryBuilder {

	private StringBuilder sql;
	private NativeExecutionQuery nativeQuery;

	/**
	 * A private constructor with a initial select sql
	 * 
	 * @param sql
	 */
	private DeadLetterQueryBuilder(final StringBuilder sql, final NativeExecutionQuery nativeQuery) {
		this.sql = sql;
		this.nativeQuery = nativeQuery;
	}

	/**
	 * Create a instance of DeadLetterQueryBuilder builder
	 * 
	 * @return
	 */
	public static DeadLetterQueryBuilder create(final RuntimeService runtimeService) {
		final StringBuilder sql = new StringBuilder(
				"SELECT DISTINCT D.ID_, P.NAME_, D.* FROM ACT_RU_DEADLETTER_JOB D join ACT_RU_EXECUTION E on D.PROCESS_INSTANCE_ID_ = E.PROC_INST_ID_ join ACT_RE_PROCDEF P on P.ID_=E.PROC_DEF_ID_");

		return new DeadLetterQueryBuilder(sql, runtimeService.createNativeExecutionQuery());
	}

	public static DeadLetterQueryBuilder count(final RuntimeService runtimeService) {
		final StringBuilder sql = new StringBuilder(
				"SELECT count(DISTINCT D.ID_) as totelElement FROM ACT_RU_DEADLETTER_JOB D join ACT_RU_EXECUTION E on D.PROCESS_INSTANCE_ID_ = E.PROC_INST_ID_ join ACT_RE_PROCDEF P on P.ID_=E.PROC_DEF_ID_");
		return new DeadLetterQueryBuilder(sql, runtimeService.createNativeExecutionQuery());
	}

	public DeadLetterQueryBuilder where(final String businessKey, final String exceptionMessage,
			final String processDefinitionName, final String dueDate) {
		if (businessKey != null || exceptionMessage != null || processDefinitionName != null || dueDate != null) {
			sql.append(" WHERE 1=1 ");

			businessKey(businessKey);
			exceptionMessage(exceptionMessage);
			processDefinitionName(processDefinitionName);
			dueDate(dueDate);
		}
		return this;
	}

	/**
	 * Set bisnessKey to Builder instance
	 * 
	 * @param bisnessKey
	 * @return
	 */
	public DeadLetterQueryBuilder businessKey(final String businessKey) {
		if (businessKey != null && !businessKey.isEmpty()) {
			sql.append(" and E.BUSINESS_KEY_ like #{BUSINESS_KEY}");
			this.nativeQuery.parameter("BUSINESS_KEY", businessKey);
		}
		return this;
	}

	/**
	 * @param exceptionMessage
	 * @return
	 */
	public DeadLetterQueryBuilder exceptionMessage(final String exceptionMessage) {
		if (exceptionMessage != null && !exceptionMessage.isEmpty()) {
			sql.append(" and D.EXCEPTION_MSG_ like #{EXCEPTIONMESSAGE}");
			this.nativeQuery.parameter("EXCEPTIONMESSAGE", exceptionMessage);
		}
		return this;
	}

	/**
	 * @param processDefinitionName
	 * @return
	 */
	public DeadLetterQueryBuilder processDefinitionName(final String processDefinitionName) {
		if (processDefinitionName != null && !processDefinitionName.isEmpty()) {
			sql.append(" and P.NAME_ like #{PROCESSDEFINITIONNAME}");
			this.nativeQuery.parameter("PROCESSDEFINITIONNAME", processDefinitionName);
		}
		return this;
	}

	/**
	 * @param dueDate
	 * @return
	 */
	public DeadLetterQueryBuilder dueDate(final String dueDate) {
		if (dueDate != null) {
			sql.append(" and D.DUEDATE_ like #{DUEDATE}");
			this.nativeQuery.parameter("DUEDATE", dueDate);
		}
		return this;
	}

	public DeadLetterQueryBuilder sortBy(SortCriteria sortCriteria) {
		if (sortCriteria != null && sortCriteria.iterator().hasNext()) {

			sql.append(" ORDER BY ");
			for (Iterator<Entry<String, Direction>> sortByIterator = sortCriteria.iterator(); sortByIterator
					.hasNext();) {
				Entry<String, Direction> sortByEntry = sortByIterator.next();

				if (sortByEntry != null) {
					String sortBy = converToTableField(sortByEntry.getKey());
					String direction = sortByEntry.getValue().name();

					sql.append(sortBy + " " + direction);
					if (sortByIterator.hasNext()) {
						sql.append(" , ");
					}
				}
			}
		}

		return this;
	}

	private String converToTableField(String sortBy) {
		String sort;
		switch (sortBy) {
		case "jobId":
			sort = "ID_";
			break;

		case "jobDuedate":
			sort = "DUEDATE_";
			break;
		case "executionId":
			sort = "EXECUTION_ID_";
			break;
		case "processInstanceId":
			sort = "PROCESS_INSTANCE_ID_";
			break;
		default:
			throw new IllegalArgumentException("Invalid sort field: " + sortBy);
		}
		return sort;
	}

	/**
	 * Build a final sql select quesry
	 * 
	 * @return
	 */
	public NativeExecutionQuery query() {
		return this.nativeQuery.sql(sql.toString());
	}

}
