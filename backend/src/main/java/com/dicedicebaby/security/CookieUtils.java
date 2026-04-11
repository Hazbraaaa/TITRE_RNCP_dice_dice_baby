package com.dicedicebaby.security;

import com.dicedicebaby.config.Constant;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {

  public void addTokenToCookie(
      String newToken, String existingCookie, HttpServletResponse response) {
    String updatedValue =
        (existingCookie == null || existingCookie.isEmpty())
            ? newToken
            : existingCookie + Constant.SEPARATOR + newToken;

    Cookie cookie = new Cookie(Constant.COOKIE_NAME, updatedValue);
    cookie.setHttpOnly(true);
    // Change setSecure to true when in PROD
    cookie.setSecure(false);
    cookie.setPath("/");
    cookie.setMaxAge(7 * 24 * 60 * 60);

    // Add cookie to response Http
    response.addCookie(cookie);
  }

  public void deleteTokenToCookie(
      String tokenToDelete, String existingCookie, HttpServletResponse response) {
    List<String> tokens = new ArrayList<>(Arrays.asList(existingCookie.split(Constant.SEPARATOR)));

    tokens.remove(tokenToDelete);

    String updatedValue = String.join(Constant.SEPARATOR, tokens);

    // Create final cookie
    Cookie cookie = new Cookie(Constant.COOKIE_NAME, updatedValue);
    cookie.setHttpOnly(true);
    // ------ Change setSecure to true when in PROD ------
    cookie.setSecure(false);
    cookie.setPath("/");

    // If cookie is empty kill it, otherwise reset it
    if (updatedValue.isEmpty()) {
      cookie.setMaxAge(0);
    } else {
      cookie.setMaxAge(7 * 24 * 60 * 60);
    }

    // Add cookie to response Http
    response.addCookie(cookie);
  }

  public void updateTokenToCookie(
      String oldToken, String newToken, String existingCookie, HttpServletResponse response) {
    // If no cookie, add token to new cookie
    if (existingCookie == null || existingCookie.isEmpty()) {
      addTokenToCookie(newToken, null, response);
      return;
    }

    List<String> tokens = new ArrayList<>(Arrays.asList(existingCookie.split(Constant.SEPARATOR)));

    // Search for old token index in list
    int index = tokens.indexOf(oldToken);
    // If old token found replace with the new, otherwise add new token (expiration or bad
    // manipulation)
    if (index != -1) {
      tokens.set(index, newToken);
    } else {
      tokens.add(newToken);
    }

    String updatedValue = String.join(Constant.SEPARATOR, tokens);

    // Create final cookie
    Cookie cookie = new Cookie(Constant.COOKIE_NAME, updatedValue);
    cookie.setHttpOnly(true);
    // ------ Change setSecure to true when in PROD ------
    cookie.setSecure(false);
    cookie.setPath("/");
    cookie.setMaxAge(7 * 24 * 60 * 60);

    // Add cookie to response Http
    response.addCookie(cookie);
  }
}
