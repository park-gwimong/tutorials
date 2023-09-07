package sample.api.test.e2e;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import org.junit.jupiter.api.*;
import org.springframework.restdocs.payload.FieldDescriptor;
import sample.api.domain.dto.RequestDeviceDTO;
import sample.api.domain.enums.device.classification.os.DeviceOsClassification;
import sample.api.domain.enums.device.classification.status.DeviceStatusClassification;
import sample.api.domain.enums.device.classification.type.DeviceTypeClassification;
import sample.api.domain.enums.device.code.status.DeviceStatusCode;
import org.hamcrest.Matchers;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Arrays;
import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

/**
 * The type Device controller test.
 */
@DisplayName("Device Controller Test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DeviceControllerTest extends BaseControllerTest {

    private final String DOC_PATH = "DeviceControllerTest/" + DEFAULT_DOC_PATH;
    private final String SNIPPET_TAG = "Device";

    private final String REQUEST_MAPPING_PATH = "/api/devices";
    private final Schema REQUEST_FIELDS_SCHEMA = Schema.schema("RequestDeviceDto");

    private final List<FieldDescriptor> REQUEST_FIELDS = Arrays.asList(
            fieldWithPath("sn").type(JsonFieldType.STRING).description("시리얼 번호"),
            fieldWithPath("typeClassification").type(JsonFieldType.STRING).description("타입 분류"),
            fieldWithPath("osClassification").type(JsonFieldType.STRING).description("OS 분류"),
            fieldWithPath("statusClassification").type(JsonFieldType.STRING).description("상태 분류"),
            fieldWithPath("statusCode").type(JsonFieldType.STRING).description("상태 코드"),
            fieldWithPath("educationInstitution").type(JsonFieldType.STRING).description("교육 기관")
                    .optional(),
            fieldWithPath("modelName").type(JsonFieldType.STRING).description("모델 이름").optional(),
            fieldWithPath("macAddress").type(JsonFieldType.STRING).description("Mac Address").optional(),
            fieldWithPath("deviceUsed").type(JsonFieldType.STRING).description("단말기 사용 이력").optional(),
            fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일").optional(),
            fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정일").optional()
    );

    private final Schema RESPONSE_FIELDS_SCHEMA = Schema.schema("ResponseDeviceDto");

    private final List<FieldDescriptor> RESPONSE_FIELDS = Arrays.asList(
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("ID"),
            fieldWithPath("sn").type(JsonFieldType.STRING).description("시리얼 번호"),
            fieldWithPath("typeClassification").type(JsonFieldType.STRING).description("타입 분류"),
            fieldWithPath("osClassification").type(JsonFieldType.STRING).description("OS 분류"),
            fieldWithPath("statusClassification").type(JsonFieldType.STRING).description("상태 분류"),
            fieldWithPath("statusCode").type(JsonFieldType.STRING).description("상태 코드"),
            fieldWithPath("educationInstitution").type(JsonFieldType.STRING).description("교육 기관")
                    .optional(),
            fieldWithPath("modelName").type(JsonFieldType.STRING).description("모델 이름").optional(),
            fieldWithPath("macAddress").type(JsonFieldType.STRING).description("Mac Address").optional(),
            fieldWithPath("deviceUsed").type(JsonFieldType.STRING).description("단말기 사용 이력").optional(),
            fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일"),
            fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정일").optional()
    );

    private final Schema RESPONSE_FIELDS_PAGE_SCHEMA = Schema.schema("Page<ResponseDeviceDto>");

    private final List<FieldDescriptor> RESPONSE_FIELDS_PAGE = Arrays.asList(
            fieldWithPath("content").description("내용"),
            fieldWithPath("content.[].createdAt").description("내용").optional(),
            fieldWithPath("content.[].updatedAt").description("내용").optional(),
            fieldWithPath("content.[].id").description("내용").optional(),
            fieldWithPath("content.[].statusClassification").description("내용").optional(),
            fieldWithPath("content.[].typeClassification").description("내용").optional(),
            fieldWithPath("content.[].osClassification").description("내용").optional(),
            fieldWithPath("content.[].sn").description("내용").optional(),
            fieldWithPath("content.[].modelName").description("내용").optional(),
            fieldWithPath("content.[].educationInstitution").description("내용").optional(),
            fieldWithPath("content.[].macAddress").description("내용").optional(),
            fieldWithPath("content.[].statusCode").description("내용").optional(),
            fieldWithPath("content.[].deviceUsed").description("내용").optional(),
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
            fieldWithPath("last").description("타입 분류"),
            fieldWithPath("totalPages").description("OS 분류"),
            fieldWithPath("totalElements").description("상태 분류"),
            fieldWithPath("size").description("상태 코드"),
            fieldWithPath("number").description("교육 기관"),
            fieldWithPath("sort").description("모델 이름"),
            fieldWithPath("sort.empty").description("모델 이름"),
            fieldWithPath("sort.sorted").description("모델 이름"),
            fieldWithPath("sort.unsorted").description("모델 이름"),
            fieldWithPath("first").description("Mac Address"),
            fieldWithPath("numberOfElements").description("단말기 사용 이력"),
            fieldWithPath("empty").description("생성일")
    );


    @Nested
    class Describe_create_api {
        @Nested
        class Context_without_required_fields {
            @Test
            void it_returns_bad_request() {

                RequestDeviceDTO dto =
                        RequestDeviceDTO
                                .builder()
                                .build();

                given(DeviceControllerTest.this.spec)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Content-type", "application/json")
                        .body(dto)
                        .log()
                        .all()
                        .when()
                        .post(REQUEST_MAPPING_PATH)
                        .then()
                        .statusCode(HttpStatus.BAD_REQUEST.value());
            }
        }

        @Nested
        class Context_with_contains_required_fields {
            @Test
            @DisplayName("Test the API that create the device to the system.")
            void it_returns_what_was_created() {

                RequestDeviceDTO requestDeviceDTO =
                        RequestDeviceDTO.builder()
                                .statusClassification(DeviceStatusClassification.NEW_DEVICE)
                                .osClassification(DeviceOsClassification.WINDOWS_OS)
                                .typeClassification(DeviceTypeClassification.NOTEBOOK)
                                .sn("999999").build();

                given(DeviceControllerTest.this.spec)
                        .filter(document(DeviceControllerTest.this.DOC_PATH,
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag(SNIPPET_TAG)
                                                .requestSchema(REQUEST_FIELDS_SCHEMA)
                                                .requestFields(REQUEST_FIELDS)
                                                .responseSchema(RESPONSE_FIELDS_SCHEMA)
                                                .responseFields(RESPONSE_FIELDS)
                                                .summary("CREATE")
                                                .description("API to request create of device ")
                                                .build()
                                )))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Content-type", "application/json")
                        .body(requestDeviceDTO)
                        .log()
                        .all()
                        .when()
                        .post(REQUEST_MAPPING_PATH)
                        .then()
                        .statusCode(HttpStatus.OK.value())
                        .body("statusClassification",
                                Matchers.equalTo(DeviceStatusClassification.NEW_DEVICE.name()))
                        .body("osClassification",
                                Matchers.equalTo(DeviceOsClassification.WINDOWS_OS.name()))
                        .body("typeClassification",
                                Matchers.equalTo(DeviceTypeClassification.NOTEBOOK.name()))
                        .body("sn",
                                Matchers.equalTo("999999"))
                        .body("modelName",
                                Matchers.nullValue())
                        .body("educationInstitution",
                                Matchers.nullValue())
                        .body("macAddress",
                                Matchers.nullValue())
                        .body("statusCode",
                                Matchers.equalTo(DeviceStatusCode.POSSESSION.name()))
                        .body("deviceUsed",
                                Matchers.nullValue());
            }
        }
    }

    @Nested
    class Describe_read_api {

        private final int contentId = 1;
        private final int withoutContentId = 10;

        @Nested
        class Context_without_content {
            @Test
            void it_returns_no_contents() {
                given(DeviceControllerTest.this.spec)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Content-type", "application/json")
                        .log()
                        .all()
                        .when()
                        .get(REQUEST_MAPPING_PATH + "/{deviceId}", Describe_read_api.this.withoutContentId)
                        .then()
                        .statusCode(HttpStatus.NO_CONTENT.value());
            }
        }

        @Nested
        class Context_with_contents {
            @Test
            void it_returns_content_normally() {

                given(DeviceControllerTest.this.spec)
                        .filter(document(DeviceControllerTest.this.DOC_PATH,
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag(SNIPPET_TAG)
                                                .requestSchema(null)
                                                .responseSchema(RESPONSE_FIELDS_SCHEMA)
                                                .responseFields(RESPONSE_FIELDS)
                                                .summary("READ")
                                                .description("API to request device information")
                                                .build()
                                ),
                                pathParameters(
                                        parameterWithName("deviceId").description("Device ID")
                                )))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Content-type", "application/json")
                        .log()
                        .all()
                        .when()
                        .get(REQUEST_MAPPING_PATH + "/{deviceId}", Describe_read_api.this.contentId)
                        .then()
                        .statusCode(HttpStatus.OK.value())
                        .body("statusClassification",
                                Matchers.equalTo(DeviceStatusClassification.NEW_DEVICE.name()))
                        .body("osClassification",
                                Matchers.equalTo(DeviceOsClassification.WINDOWS_OS.name()))
                        .body("typeClassification",
                                Matchers.equalTo(DeviceTypeClassification.TABLET.name()))
                        .body("sn",
                                Matchers.equalTo("000001"))
                        .body("modelName",
                                Matchers.equalTo("Test1"))
                        .body("educationInstitution",
                                Matchers.is("1"))
                        .body("macAddress",
                                Matchers.nullValue())
                        .body("statusCode",
                                Matchers.equalTo(DeviceStatusCode.RENTAL.name()))
                        .body("deviceUsed",
                                Matchers.nullValue());
            }
        }

        @Test
        void it_returns_content_list_normally() {
            given(DeviceControllerTest.this.spec)
                    .queryParam("page, 10")
                    .queryParam("size, 0")
                    .filter(document(DeviceControllerTest.this.DOC_PATH,
                            resource(
                                    ResourceSnippetParameters.builder()
                                            .tag(SNIPPET_TAG)
                                            .requestSchema(null)
                                            .responseSchema(RESPONSE_FIELDS_PAGE_SCHEMA)
                                            .responseFields(RESPONSE_FIELDS_PAGE)
                                            .summary("READ")
                                            .description("API to request device list")
                                            .build()

                            )))
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .header("Content-type", "application/json")
                    .log()
                    .all()
                    .when()
                    .get(REQUEST_MAPPING_PATH)
                    .then()
                    .statusCode(HttpStatus.OK.value());
        }
    }


    @Nested
    class Describe_update_api {
        private final int contentId = 2;
        private final int withoutContentId = 20;

        @Nested
        class Context_without_content {
            @Test
            void it_returns_bad_request() {

                RequestDeviceDTO dto =
                        RequestDeviceDTO
                                .builder()
                                .build();

                given(DeviceControllerTest.this.spec)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Content-type", "application/json")
                        .body(dto)
                        .log()
                        .all()
                        .when()
                        .patch(REQUEST_MAPPING_PATH + "/{deviceId}", Describe_update_api.this.withoutContentId)
                        .then()
                        .statusCode(HttpStatus.BAD_REQUEST.value());
            }
        }

        @Nested
        class Context_with_contains_updated_field {
            RequestDeviceDTO requestDeviceDTO = RequestDeviceDTO.builder()
                    .statusClassification(DeviceStatusClassification.REAPER_DEVICE)
                    .osClassification(DeviceOsClassification.IOS_OS)
                    .typeClassification(DeviceTypeClassification.TABLET)
                    .sn("000099")
                    .modelName("testModel1")
                    .build();

            @Test
            void it_returns_what_was_updated() {
                given(DeviceControllerTest.this.spec)
                        .filter(document(DeviceControllerTest.this.DOC_PATH,
                                resource(
                                        ResourceSnippetParameters.builder()
                                                .tag(SNIPPET_TAG)
                                                .requestSchema(REQUEST_FIELDS_SCHEMA)
                                                .requestFields(REQUEST_FIELDS)
                                                .responseSchema(RESPONSE_FIELDS_SCHEMA)
                                                .responseFields(RESPONSE_FIELDS)
                                                .summary("UPDATE")
                                                .description("API to request update of device")
                                                .build()
                                ),
                                pathParameters(
                                        parameterWithName("deviceId").description("Device ID")
                                )))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Content-type", "application/json")
                        .body(requestDeviceDTO)
                        .log()
                        .all()
                        .when()
                        .patch(REQUEST_MAPPING_PATH + "/{deviceId}", Describe_update_api.this.contentId)
                        .then()
                        .statusCode(HttpStatus.OK.value())
                        .body("statusClassification",
                                Matchers.equalTo(DeviceStatusClassification.REAPER_DEVICE.name()))
                        .body("osClassification",
                                Matchers.equalTo(DeviceOsClassification.IOS_OS.name()))
                        .body("typeClassification",
                                Matchers.equalTo(DeviceTypeClassification.TABLET.name()))
                        .body("sn",
                                Matchers.equalTo("000099"))
                        .body("modelName",
                                Matchers.equalTo("testModel1"));
            }
        }

        @Nested
        class Describe_delete_api {
            private final int contentId = 3;
            private final int withoutContentId = 20;

            @Nested
            class Context_without_content {
                @Test
                void it_returns_bad_request() {

                    given(DeviceControllerTest.this.spec)
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .header("Content-type", "application/json")
                            .log()
                            .all()
                            .when()
                            .patch(REQUEST_MAPPING_PATH + "/{deviceId}", Describe_delete_api.this.withoutContentId)
                            .then()
                            .statusCode(HttpStatus.BAD_REQUEST.value());
                }
            }

            @Nested
            class Context_with_content {
                @Test
                void it_returns_no_content() {
                    given(DeviceControllerTest.this.spec)
                            .filter(document(DeviceControllerTest.this.DOC_PATH,
                                    resource(
                                            ResourceSnippetParameters.builder()
                                                    .tag(SNIPPET_TAG)
                                                    .requestSchema(null)
                                                    .responseSchema(null)
                                                    .summary("DELETE")
                                                    .description("API to request deletion of device information")
                                                    .build()

                                    ),
                                    pathParameters(
                                            parameterWithName("deviceId").description("Device ID")
                                    )))
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .header("Content-type", "application/json")
                            .log()
                            .all()
                            .when()
                            .delete(REQUEST_MAPPING_PATH + "/{deviceId}", Describe_delete_api.this.contentId)
                            .then()
                            .statusCode(HttpStatus.NO_CONTENT.value());
                }
            }
        }
    }
}