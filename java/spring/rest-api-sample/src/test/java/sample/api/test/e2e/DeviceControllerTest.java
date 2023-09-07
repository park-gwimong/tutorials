package sample.api.test.e2e;

import sample.api.domain.entity.DeviceEntity;
import sample.api.domain.enums.device.classification.os.DeviceOsClassification;
import sample.api.domain.enums.device.classification.status.DeviceStatusClassification;
import sample.api.domain.enums.device.classification.type.DeviceTypeClassification;
import sample.api.domain.enums.device.code.status.DeviceStatusCode;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

/**
 * The type Device controller test.
 */
class DeviceControllerTest extends BaseControllerTest {

    private static final Snippet REQUEST_FIELDS = requestFields(
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
            fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일").optional(),
            fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정일").optional()
    );

    private static final Snippet RESPONSE_FIELDS = responseFields(
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

    private static final Snippet RESPONSE_FIELDS_PAGE = responseFields(
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

    /**
     * Device create api test.
     */
    @Test
    @DisplayName("Test the API that create the device to the system.")
    void deviceCreateApiTest() {

        DeviceEntity newDeviceEntity = DeviceEntity.builder()
                .statusClassification(DeviceStatusClassification.NEW_DEVICE)
                .osClassification(DeviceOsClassification.WINDOWS_OS)
                .typeClassification(DeviceTypeClassification.NOTEBOOK)
                .sn("999999").build();

        given(this.spec)
                .filter(document(DEFAULT_DOC_PATH, REQUEST_FIELDS, RESPONSE_FIELDS))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Content-type", "application/json")
                .body(newDeviceEntity)
                .log()
                .all()
                .when()
                .post("/api/devices")
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

    /**
     * Device read api test.
     */
    @Test
    @DisplayName("Test the API that read a device information.")
    void deviceReadApiTest() {

        given(this.spec)
                .filter(document(DEFAULT_DOC_PATH,
                        pathParameters(
                                parameterWithName("deviceId").description("Device ID")
                        ),
                        RESPONSE_FIELDS))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Content-type", "application/json")
                .log()
                .all()
                .when()
                .get("/api/devices/{deviceId}", 1)
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

    /**
     * Device read all api test.
     */
    @Test
    @DisplayName("Test the API that read all device information.")
    void deviceReadAllApiTest() {

        given(this.spec)
                .queryParam("page, 10")
                .queryParam("size, 0")
                .filter(document(DEFAULT_DOC_PATH,
                        RESPONSE_FIELDS_PAGE))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Content-type", "application/json")
                .log()
                .all()
                .when()
                .get("/api/devices")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    /**
     * Device update api test.
     */
    @Test
    @DisplayName("Test the API that update the device to the system.")
    void deviceUpdateApiTest() {

        DeviceEntity targetDeviceEntity = DeviceEntity.builder()
                .statusClassification(DeviceStatusClassification.REAPER_DEVICE)
                .osClassification(DeviceOsClassification.IOS_OS)
                .typeClassification(DeviceTypeClassification.TABLET)
                .sn("000099").build();

        targetDeviceEntity.setModelName("testModel1");
        targetDeviceEntity.setMacAddress("0000-0000-0000-0000");
        targetDeviceEntity.setEducationInstitution("10");

        given(this.spec)
                .filter(document(DEFAULT_DOC_PATH,
                        pathParameters(
                                parameterWithName("deviceId").description("Device ID")
                        ),
                        REQUEST_FIELDS, RESPONSE_FIELDS))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Content-type", "application/json")
                .body(targetDeviceEntity)
                .log()
                .all()
                .when()
                .patch("/api/devices/{deviceId}", 1)
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
                        Matchers.equalTo("testModel1"))
                .body("educationInstitution",
                        Matchers.equalTo("10"))
                .body("macAddress",
                        Matchers.equalTo("0000-0000-0000-0000"))
                .body("statusCode",
                        Matchers.equalTo(DeviceStatusCode.POSSESSION.name()))
                .body("deviceUsed",
                        Matchers.nullValue());
    }

    /**
     * Device delete api test.
     */
    @Test
    @DisplayName("Test the API that delete device information.")
    void deviceDeleteApiTest() {

        given(this.spec)
                .filter(document(DEFAULT_DOC_PATH,
                        pathParameters(
                                parameterWithName("deviceId").description("Device ID")
                        )))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("Content-type", "application/json")
                .log()
                .all()
                .when()
                .delete("/api/devices/{deviceId}", 1)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}