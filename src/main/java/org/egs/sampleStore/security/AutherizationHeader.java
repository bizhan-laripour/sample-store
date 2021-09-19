package org.egs.sampleStore.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Base64;
import java.util.List;
import java.util.Map;


@Component
public class AutherizationHeader {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    public String getRoleFromToken() {
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        ObjectMapper objectMapper = new ObjectMapper();
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
                    .parseClaimsJws(token.substring(7))
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        Map<String, Object> map =
                objectMapper.convertValue(claims, Map.class);
        List<Object> c = (List<Object>) map.get("auth");
        Map<String, String> hash = (Map<String, String>) c.get(0);

        return hash.get("authority");
    }

    public String getUsernameFromToken(String token){
        ObjectMapper objectMapper = new ObjectMapper();
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        Map<String, Object> map =
                objectMapper.convertValue(claims, Map.class);
        return map.get("sub").toString();


    }

    public String getUsernameFromWitoutToken(){
        ObjectMapper objectMapper = new ObjectMapper();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");

        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes()))
                    .parseClaimsJws(token.substring(7))
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        Map<String, Object> map =
                objectMapper.convertValue(claims, Map.class);
        return map.get("sub").toString();


    }
}
