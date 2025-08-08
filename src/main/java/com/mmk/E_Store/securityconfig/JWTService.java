package com.mmk.E_Store.securityconfig;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;


@Service
public class JWTService {

    private String secretkey = "";

    public JWTService()  {
        KeyGenerator keygen  = null;
        try {
            keygen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk  = keygen.generateKey();
            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }


    }


    public String generatetoken(String username) {

        Map<String,Object> claims  =  new HashMap<>();

        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt( new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() * 60 * 60 * 30))
                .and()
                .signWith(getkey())
                .compact();
    }

    private Key getkey() {
        byte[] keybyte  = Base64.getDecoder().decode(secretkey);
        return Keys.hmacShaKeyFor(keybyte);
    }

}
