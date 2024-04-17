package service;

import DTO.UserDTO;
import DTO.UserEditDTO;
import DTO.UserShowDTO;
import DTO.UserUpdateDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
public class VitrinaApi {

    private RequestSpecification spec;
    private final static String BASE_URI = "https://api-dev.vitrinapromo.ru/api/profile";
    private final static String REGISTRATION = "/registration";
    private final static String SHOW = "/get";
    private final static String UPDATE_STATUS = "/update-status";
    private final static String EDIT = "/edit";

    public VitrinaApi() {
        spec = given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .header("Api-id","6ccd18f8-7448-4483-9f1a-6567e3ca0ac7")
                .header("X-Sign","8f62a6a0932be52da9ad8143fd04c06b")
                .header("X-Timestamp", 5546l)
                .log().all();
    }

    public ValidatableResponse createUser(UserDTO user){

       return   given(spec)
                    .body(user)
                .when()
                    .post(REGISTRATION)
                .then()
                    .log().all();

    }

    public ValidatableResponse showUser(UserShowDTO showUser) {

        return given(spec)
                    .body(showUser)
                .when()
                    .get(SHOW)
                .then()
                    .log().all();
    }
    public ValidatableResponse updateUser(UserUpdateDTO updateUser) {

        return given(spec)
                    .body(updateUser)
                .when()
                    .post(UPDATE_STATUS)
                .then()
                    .log().all();
    }

    public ValidatableResponse editUser(UserEditDTO editUser) {

        return given(spec)
                        .body(editUser)
                .when()
                    .post(EDIT)
                .then()
                    .log().all();
    }

}

