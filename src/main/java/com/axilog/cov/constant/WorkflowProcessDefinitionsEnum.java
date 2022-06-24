package com.axilog.cov.constant;

public enum WorkflowProcessDefinitionsEnum {
	
	NEW_EQIUPMENT_DEFINITION_ID("new-equip");
	
	private String definitionId;

	private WorkflowProcessDefinitionsEnum(String definitionId) {
		this.definitionId = definitionId;
	}

	public String getDefinitionId() {
		return definitionId;
	}

}
