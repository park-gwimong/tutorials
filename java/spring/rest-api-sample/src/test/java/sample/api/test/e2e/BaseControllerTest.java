package sample.api.test.e2e;


import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.TestPropertySource;

/**
 * The type Base controller test.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "/application-test.properties")
public abstract class BaseControllerTest {

  /**
   * The constant DEFAULT_DOC_PATH.
   */
  protected static final String DEFAULT_DOC_PATH = "{class_name}/{method_name}/";

  /**
   * The Spec.
   */
  protected RequestSpecification spec;

  /**
   * The Port.
   */
  @LocalServerPort
  int port;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  /**
   * Sets up rest docs.
   *
   * @param restDocumentation the rest documentation
   */
  @BeforeEach
  void setUpRestDocs(RestDocumentationContextProvider restDocumentation) {
    this.spec = new RequestSpecBuilder()
        .setPort(port)
        .addFilter(documentationConfiguration(restDocumentation)
            .operationPreprocessors()
            .withRequestDefaults(prettyPrint())
            .withResponseDefaults(prettyPrint()))
        .build();
  }
}