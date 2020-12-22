package com.axilog.cov.dto.topology.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoordinateDto {
	private String lattitude;
	private String longitude;
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoordinateDto other = (CoordinateDto) obj;
		if (lattitude == null) {
			if (other.lattitude != null)
				return false;
		} else if (!lattitude.equals(other.lattitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lattitude == null) ? 0 : lattitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		return result;
	}
	
	
}
