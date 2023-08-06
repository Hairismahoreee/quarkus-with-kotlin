package com.learn.core.security.jwt.tool;

import com.learn.core.security.jwt.payload.BodyPayload;
import com.learn.core.exception.FormatException;
import io.jsonwebtoken.Claims;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface JwtTools {
    
    public static Set<String> unWrapRoles(Claims claims, String defaultRoles) {
        var roles = new HashSet<String>();
        var rolesClaim = claims.get(BodyPayload.ROLES);
        if(rolesClaim instanceof List<?>){
            var temp = (List<?>) rolesClaim;
            if(temp.isEmpty() && defaultRoles != null) {
                roles.add(defaultRoles);
            }else {
                var tempAsSet = temp
                    .stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .collect(Collectors.toSet());
                roles.addAll(tempAsSet);
            }
        }
        return roles;
    }

    public static String unWrapBearerToken(String token) {
        var split = token.split(" ");
        if(split.length != 2 || !split[0].equals("Bearer")) {
            throw new FormatException("Token format is wrong");
        }
        return split[1];
    }
}
