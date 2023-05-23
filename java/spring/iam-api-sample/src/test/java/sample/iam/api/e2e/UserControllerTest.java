package sample.iam.api.e2e;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import sample.iam.api.domain.entity.UserEntity;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.context.TestPropertySource;

/**
 * The type User controller test.
 */
@TestPropertySource(locations = "/token.yml")
class UserControllerTest extends BaseControllerTest {

  @Value("${token}")
  private String validToken;


  private static final Snippet REQUEST_HEADERS = requestHeaders(
      headerWithName(HttpHeaders.AUTHORIZATION).description("인증")

  );
  private static final Snippet REQUEST_FIELDS = requestFields(
      fieldWithPath("id").type(JsonFieldType.NUMBER).description("식별자").optional(),
      fieldWithPath("userId").type(JsonFieldType.STRING).description("아이디"),
      fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
      fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
      fieldWithPath("password").type(JsonFieldType.STRING).description("비밀 번호").optional(),
      fieldWithPath("encryptedPassword").type(JsonFieldType.STRING).description("비밀 번호(암호화)")
          .optional(),
      fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일").optional(),
      fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("갱신일").optional()
  );

  private static final Snippet RESPONSE_FIELDS = responseFields(
      fieldWithPath("id").type(JsonFieldType.NUMBER).description("식별자").optional(),
      fieldWithPath("userId").type(JsonFieldType.STRING).description("아이디"),
      fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
      fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
      fieldWithPath("password").type(JsonFieldType.STRING).description("비밀 번호").optional(),
      fieldWithPath("encryptedPassword").type(JsonFieldType.STRING).description("비밀 번호(암호화)")
          .optional(),
      fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일"),
      fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("갱신일").optional()
  );

  private static final Snippet RESPONSE_FIELDS_PAGE = responseFields(
      fieldWithPath("content").description("내용"),
      fieldWithPath("content.[].createdAt").description("생성일").optional(),
      fieldWithPath("content.[].updatedAt").description("갱신일").optional(),
      fieldWithPath("content.[].id").description("식별자").optional(),
      fieldWithPath("content.[].userId").description("아이디").optional(),
      fieldWithPath("content.[].name").description("이름").optional(),
      fieldWithPath("content.[].email").description("이메일").optional(),
      fieldWithPath("content.[].password").description("비밀 번호").optional(),
      fieldWithPath("content.[].encryptedPassword").description("암호화된 비밀 번호").optional(),
      fieldWithPath("pageable").description("페이징"),
      fieldWithPath("pageable.sort").description("페이징"),
      fieldWithPath("pageable.sort.empty").description("페이징"),
      fieldWithPath("pageable.sort.sorted").description("페이징"),
      fieldWithPath("pageable.sort.unsorted").description("페이징"),
      fieldWithPath("pageable.offset").description("페이징"),
      fieldWithPath("pageable.pageSize").description("페이징"),
      fieldWithPath("pageable.pageNumber").description("페이징"),
      fieldWithPath("pageable.paged").description("페이징"),
      fieldWithPath("pageable.unpaged").description("페이징"),
      fieldWithPath("last").description(""),
      fieldWithPath("totalPages").description("총 페이지 수"),
      fieldWithPath("totalElements").description("총 요소 수"),
      fieldWithPath("size").description("사이즈"),
      fieldWithPath("number").description("숫자"),
      fieldWithPath("sort").description("정렬"),
      fieldWithPath("sort.empty").description("정렬"),
      fieldWithPath("sort.sorted").description("정렬"),
      fieldWithPath("sort.unsorted").description("정렬"),
      fieldWithPath("first").description("첫 페이지"),
      fieldWithPath("numberOfElements").description("요소 수"),
      fieldWithPath("empty").description("")
  );

  @BeforeEach
  void setUp() {
  }

  /**
   * User create api test.
   */
  @Test
  @Order(1)
  @DisplayName("Test the API that create the user to the system.")
  void createUserApiTest() {

    UserEntity newUserEntity = UserEntity.builder()
        .userId("test2")
        .email("test2@test.com")
        .name("test2")
        .password("test")
        .build();

    given(this.spec)
        .filter(document(DEFAULT_DOC_PATH, REQUEST_FIELDS, RESPONSE_FIELDS))
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .header("Content-type", "application/json")
        .body(newUserEntity)
        .log()
        .all()
        .when()
        .post("/api/users")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("userId",
            Matchers.equalTo(newUserEntity.getUserId()))
        .body("name",
            Matchers.equalTo(newUserEntity.getName()))
        .body("email",
            Matchers.equalTo(newUserEntity.getEmail()));
  }

  /**
   * User read api test.
   */
  @Test
  @Order(2)
  @DisplayName("Test the API that read a user information.")
  void readUserApiTest() {
    given(this.spec)
        .filter(document(DEFAULT_DOC_PATH,
            REQUEST_HEADERS,
            pathParameters(
                parameterWithName("userId").description("User ID")
            ),
            RESPONSE_FIELDS))
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .header("authorization", this.validToken)
        .log()
        .all()
        .when()
        .get("/api/users/{userId}", 1)
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("userId",
            Matchers.equalTo("test"))
        .body("name",
            Matchers.equalTo("test"))
        .body("email",
            Matchers.equalTo("test@test.com"));
  }

  /**
   * User read all api test.
   */
  @Test
  @Order(3)
  @DisplayName("Test the API that read all user information.")
  void userReadAllApiTest() {

    given(this.spec)
        .queryParam("page, 10")
        .queryParam("size, 0")
        .filter(document(DEFAULT_DOC_PATH,
            RESPONSE_FIELDS_PAGE))
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .header("Content-type", "application/json")
        .header(HttpHeaders.AUTHORIZATION, validToken)
        .log()
        .all()
        .when()
        .get("/api/users")
        .then()
        .statusCode(HttpStatus.OK.value());
  }

  /**
   * User update api test.
   */
  @Test
  @Order(4)
  @DisplayName("Test the API that update the user to the system.")
  void userUpdateApiTest() {

    UserEntity targetUserEntity = UserEntity.builder()
        .userId("test2")
        .name("test2")
        .email("test2@test.com")
        .build();

    given(this.spec)
        .filter(document(DEFAULT_DOC_PATH,
            pathParameters(
                parameterWithName("userId").description("User ID")
            ),
            REQUEST_FIELDS, RESPONSE_FIELDS))
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .header("Content-type", "application/json")
        .header(HttpHeaders.AUTHORIZATION, validToken)
        .body(targetUserEntity)
        .log()
        .all()
        .when()
        .patch("/api/users/{userId}", 1)
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("name",
            Matchers.equalTo("test2"))
        .body("email",
            Matchers.equalTo("test2@test.com"));
  }

  /**
   * User delete api test.
   */
  @Test
  @DisplayName("Test the API that delete user information.")
  void userDeleteApiTest() {

    given(this.spec)
        .filter(document(DEFAULT_DOC_PATH,
            pathParameters(
                parameterWithName("userId").description("User ID")
            )))
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .header("Content-type", "application/json")
        .header(HttpHeaders.AUTHORIZATION, validToken)
        .log()
        .all()
        .when()
        .delete("/api/users/{userId}", 1)
        .then()
        .statusCode(HttpStatus.OK.value())
        .body(Matchers.equalTo("true"));
  }
}