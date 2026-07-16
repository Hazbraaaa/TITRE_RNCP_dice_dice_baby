package com.dicedicebaby.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.dicedicebaby.config.Constant;
import com.dicedicebaby.enums.ApiErrorCode;
import com.dicedicebaby.exception.ApiException;
import com.dicedicebaby.repository.AccountRepository;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

public class JwtUtilsTest {

  private static final String JWT_SECRET = "MaCleDeTestSuperSecuriseeDePlusDe32CaracteresPourLeJwt";

  private JwtUtils jwtUtils;

  @BeforeEach
  void setUp() {
    AccountRepository accountRepository = mock(AccountRepository.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    jwtUtils = new JwtUtils(accountRepository, passwordEncoder);

    ReflectionTestUtils.setField(jwtUtils, "secretKey", JWT_SECRET);
  }

  @Test
  void extractValidUsernames_WithMissingCookie_ShouldThrowSessionMissing() {
    assertThatThrownBy(() -> jwtUtils.extractValidUsernames(null))
        .isInstanceOf(ApiException.class)
        .satisfies(
            exception ->
                assertThat(((ApiException) exception).getErrorCode())
                    .isEqualTo(ApiErrorCode.SESSION_MISSING));
  }

  @Test
  void extractValidUsernames_WithInvalidTokens_ShouldThrowSessionInvalid() {
    String cookie = "invalid-token" + Constant.SEPARATOR + "another-invalid-token";

    assertThatThrownBy(() -> jwtUtils.extractValidUsernames(cookie))
        .isInstanceOf(ApiException.class)
        .satisfies(
            exception ->
                assertThat(((ApiException) exception).getErrorCode())
                    .isEqualTo(ApiErrorCode.SESSION_INVALID));
  }

  @Test
  void extractValidUsernames_WithValidTokens_ShouldReturnUsernames() {
    String firstToken = jwtUtils.generateToken("Pingu");
    String secondToken = jwtUtils.generateToken("Pong");

    String cookie = firstToken + Constant.SEPARATOR + secondToken;

    Set<String> result = jwtUtils.extractValidUsernames(cookie);

    assertThat(result).containsExactlyInAnyOrder("pingu", "pong");
  }

  @Test
  void extractValidUsernames_WithValidAndInvalidTokens_ShouldReturnValidUsernames() {
    String validToken = jwtUtils.generateToken("Pingu");

    String cookie = "invalid-token" + Constant.SEPARATOR + validToken;

    Set<String> result = jwtUtils.extractValidUsernames(cookie);

    assertThat(result).containsExactly("pingu");
  }
}
