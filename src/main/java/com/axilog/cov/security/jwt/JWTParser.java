package com.axilog.cov.security.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.axilog.cov.enums.UserGroupsEnum;
import com.axilog.cov.exception.base.BadRequestException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.SigningKeyResolverAdapter;

public class JWTParser {

	private static final String JWT_BEARER = "Bearer ";
	private static final String GROUPS = "auth";
	private static final String USER = "sub";

	private Claims claims;

	public JWTParser(String jwtHeader, Key pk) {
		String jwtToken = parseJwtToken(jwtHeader);
		this.claims = parseStringToken(jwtToken, pk);
	}

	public String getUserId() throws BadRequestException {
		if(claims.containsKey(USER)
				&& !claims.get(USER).toString().isEmpty() ) {
			return claims.get(USER).toString();
		}
		throw new BadRequestException(
				"ERR_TOKEN", "Unable to retreive a user subject in the jwt token!");
	}

	@SuppressWarnings("unchecked")
	public List<String> getGroups() {
		String authorizeGroups = claims.get(GROUPS, String.class);
		if (authorizeGroups.contains(",")) return Arrays.asList(claims.get(GROUPS, String.class).split(","));
		return Arrays.asList(claims.get(GROUPS, String.class));
	}


	private static Claims parseStringToken(final String token, final Key tokenSigningKey) {
		if (tokenSigningKey == null)
			return Jwts.parser().parseClaimsJws(token).getBody();
		return Jwts.parser()
		        .setSigningKey(tokenSigningKey)
		        .parseClaimsJws(token)
		        .getBody();
	}

	private static Claims decode(String jwtToken) {

		final Claims[] claims = new Claims[1];
		try {
			// This hacky thing is to allow getting the claims without caring about the
			// signature.
			Jwts.parser().setSigningKeyResolver(new SigningKeyResolverAdapter() {
				@Override
				public byte[] resolveSigningKeyBytes(JwsHeader header, Claims claimz) {
					claims[0] = claimz;
					return "dummy".getBytes(); // implement me
				}
			}).parseClaimsJws(jwtToken);
		} catch (SignatureException e) {
			// Don't care in this case
		}

		return claims[0];
	}

	private String parseJwtToken(String authHeader) {
		return authHeader != null ? authHeader.replace(JWT_BEARER, "") : null;
	}

	public String checkAndGetAuthorizedGroup() {
		List<String> groups = getGroups();
		Optional<String> groupOption = groups.stream()
				.filter(gr -> gr.equalsIgnoreCase(UserGroupsEnum.DRIVERS.getLabel())
						|| gr.equalsIgnoreCase(UserGroupsEnum.SUPERVISOR.getLabel())
						|| gr.equalsIgnoreCase(UserGroupsEnum.INTEGRATORS.getLabel())
						|| gr.equalsIgnoreCase(UserGroupsEnum.ADMINISTRATORS.getLabel())
						)
				.findFirst();

		if (groupOption.isPresent()) {
			return groupOption.get().toUpperCase();
		}
		throw new IllegalArgumentException("No Adjudication group found in " + groups);
	}

	public void checkAuthorizedGroup() {
		List<String> groups = getGroups();

		boolean anyMatch = groups.stream().anyMatch(gr -> gr.equalsIgnoreCase(UserGroupsEnum.DRIVERS.getLabel())
				|| gr.equalsIgnoreCase(UserGroupsEnum.SUPERVISOR.getLabel())
				|| gr.equalsIgnoreCase(UserGroupsEnum.INTEGRATORS.getLabel())
				|| gr.equalsIgnoreCase(UserGroupsEnum.ADMINISTRATORS.getLabel()));

		if (!anyMatch) {
			throw new IllegalArgumentException("No Adjudication group found in " + groups);
		}
	}
}
