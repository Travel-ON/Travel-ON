package com.travel.travel_on.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.Date;


    @Component
    public class JwtTokenProvider {
        private static String secretKey = "travel_on_secretKey@";
        private static int expirationTime= 60 * 60 * 1000;

        public static final String TOKEN_PREFIX = "Bearer ";
        public static final String HEADER_STRING = "Authorization";

        public static JWTVerifier getVerifier() {
            return JWT
                    .require(Algorithm.HMAC512(secretKey.getBytes()))
                    .build();
        }

        public static String getToken(String userId) {
            Date expires = JwtTokenProvider.getTokenExpiration(expirationTime);
            return JWT.create()
                    .withSubject(userId)
                    .withExpiresAt(expires)
                    .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                    .sign(Algorithm.HMAC512(secretKey.getBytes()));
        }

        public static Date getTokenExpiration(int expirationTime) {
            Date now = new Date();
            return new Date(now.getTime() + expirationTime);
        }

        public static void handleError(String token) {
            JWTVerifier verifier = JWT
                    .require(Algorithm.HMAC512(secretKey.getBytes()))
                    .build();

            try {
                verifier.verify(token.replace(TOKEN_PREFIX, ""));
            } catch (AlgorithmMismatchException ex) {
                throw ex;
            } catch (InvalidClaimException ex) {
                throw ex;
            } catch (SignatureGenerationException ex) {
                throw ex;
            } catch (SignatureVerificationException ex) {
                throw ex;
            } catch (TokenExpiredException ex) {
                throw ex;
            } catch (JWTCreationException ex) {
                throw ex;
            } catch (JWTDecodeException ex) {
                throw ex;
            } catch (JWTVerificationException ex) {
                throw ex;
            } catch (Exception ex) {
                throw ex;
            }
        }
    }