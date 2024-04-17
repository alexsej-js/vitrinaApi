package TestAPI;

import DTO.UserShowDTO;
import org.apache.http.HttpStatus;
import org.junit.Test;
import service.VitrinaApi;

import static org.hamcrest.CoreMatchers.equalTo;

public class ShowUserTest {

    @Test
    public void checkShowUserAllFields() { // Проверка получения участника со всеми полями

        VitrinaApi vitrinaApi = new VitrinaApi();
        UserShowDTO showUser = UserShowDTO.builder()
                .profile_id(125l)
                .external_id("testJava23657")
                .build();

        vitrinaApi.showUser(showUser)
                .statusCode(HttpStatus.SC_OK)
                .body("data.status", equalTo("active"))
                .body("data.external_id", equalTo("testJava23657"));

    }
    @Test
    public void checkShowUserNoExternal_id() { // Проверка получения участника по полю profile_id

        VitrinaApi vitrinaApi = new VitrinaApi();
        UserShowDTO showUser = UserShowDTO.builder()
                .profile_id(125l)
                .build();

        vitrinaApi.showUser(showUser)
                .statusCode(HttpStatus.SC_OK)
                .body("data.status", equalTo("active"))
                .body("data.external_id", equalTo("testJava23657"));

    }
    @Test
    public void checkShowUserNoProfile_id() { // Проверка получения участника по полю external_id

        VitrinaApi vitrinaApi = new VitrinaApi();
        UserShowDTO showUser = UserShowDTO.builder()
                .external_id("testJava23657")
                .build();

        vitrinaApi.showUser(showUser)
                .statusCode(HttpStatus.SC_OK)
                .body("data.status", equalTo("active"))
                .body("data.external_id", equalTo("testJava23657"));

    }
    @Test
    public void checkShowUserNoFields() { // Отправка запроса без полей

        VitrinaApi vitrinaApi = new VitrinaApi();
        UserShowDTO showUser = UserShowDTO.builder()
                .build();

        vitrinaApi.showUser(showUser)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"))
                .body("data.profile_id[0]", equalTo("Поле profile_id или external_id обязательное"))
                .body("data.external_id[0]", equalTo("Поле profile_id или external_id обязательное"));

    }
//    @Test
    public void checkShowUserAnotherProfile_id() {

        VitrinaApi vitrinaApi = new VitrinaApi();
        UserShowDTO showUser = UserShowDTO.builder()
                .profile_id(129l)
                .external_id("testJava87631")
                .build();

        vitrinaApi.showUser(showUser)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("trace", equalTo("Ошибка, участник не найден"))
                .body("error", equalTo("Ошибка, участник не найден"));

    }

    @Test
    public void checkShowUserAnotherOrganization() { // Проверка получения участника не пренадлежащего организатору

        VitrinaApi vitrinaApi = new VitrinaApi();
        UserShowDTO showUser = UserShowDTO.builder()
                .profile_id(109l)
                .build();

        vitrinaApi.showUser(showUser)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("trace", equalTo("Ошибка, участник не найден"))
                .body("error", equalTo("Ошибка, участник не найден"));

    }

    @Test
    public void checkShowUserNoRegistration() {  // Проверка получения не существующего участника по полю profile_id

        VitrinaApi vitrinaApi = new VitrinaApi();
        UserShowDTO showUser = UserShowDTO.builder()
                .profile_id(1281648916l)
                .build();

        vitrinaApi.showUser(showUser)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("trace", equalTo("Ошибка, участник не найден"))
                .body("error", equalTo("Ошибка, участник не найден"));

    }

    @Test
    public void checkShowUserNoRegistration1() {  // Проверка получения не существующего участника по полю external_id

        VitrinaApi vitrinaApi = new VitrinaApi();
        UserShowDTO showUser = UserShowDTO.builder()
                .external_id("testJava49494298498498468")
                .build();

        vitrinaApi.showUser(showUser)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("trace", equalTo("Ошибка, участник не найден"))
                .body("error", equalTo("Ошибка, участник не найден"));

    }

}
