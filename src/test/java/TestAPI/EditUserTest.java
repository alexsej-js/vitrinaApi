package TestAPI;

import DTO.UserEditDTO;
import org.apache.http.HttpStatus;
import org.junit.Test;
import service.VitrinaApi;

import static org.hamcrest.CoreMatchers.equalTo;

public class EditUserTest {


    @Test
    public void checkEditUserAllFields() { // Редактирование участника с заполнением/редактированием всех полей (+)

        VitrinaApi vitrinaApi = new VitrinaApi();
        UserEditDTO editUser = UserEditDTO.builder()
                .profile_id(128L)
                .phone("+79165702569")
                .first_name("Александр")
                .last_name("Невский")
                .patronymic("Тестович")
                .date_of_birth("2000-09-05")
                .email("test@test789.ru")
                .gender("жен")
                .external_id("test-81946216")
                .build();

        vitrinaApi.editUser(editUser)
                .body("profile_id", equalTo(128))
                .body("external_id", equalTo("test-81946216"))
                .body("message", equalTo("Данные успешно обновлены"))
                .statusCode(HttpStatus.SC_OK);

    }

    @Test
    public void checkEditUserAllRequiredFields() { // Редактирование данных участника с заполнение/редактированием только обязательных полей(+)

        VitrinaApi vitrinaApi = new VitrinaApi();
        UserEditDTO editUser = UserEditDTO.builder()
                .profile_id(128L)
                .phone("+79165702569")
                .first_name("Егор")
                .last_name("Невский")
                .email("test@test789.ru")
                .build();

        vitrinaApi.editUser(editUser)
                .statusCode(HttpStatus.SC_OK)
                .body("profile_id", equalTo(128))
               .body("external_id", equalTo("test-81946216"))
                .body("message", equalTo("Данные успешно обновлены"));

    }

    @Test
    public void checkEditUserNoProfileIdField() { // Редактирование данных участника без поля profile_id(-)

        VitrinaApi vitrinaApi = new VitrinaApi();
        UserEditDTO editUser = UserEditDTO.builder()
                .phone("+79165702569")
                .first_name("Егор")
                .last_name("Невский")
                .email("test@test789.ru")
                .build();

        vitrinaApi.editUser(editUser)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("data.profile_id[0]", equalTo("Поле profile_id обязательное"))
                .body("message", equalTo("Ошибка валидации данных"));

    }

    @Test
    public void checkEditUserNoFirstNameField() { // Редактирование данных участника без поля first_name(-)

        VitrinaApi vitrinaApi = new VitrinaApi();
        UserEditDTO editUser = UserEditDTO.builder()
                .profile_id(128L)
                .phone("+79165702569")
//                .first_name("Егор")
                .last_name("Невский")
                .email("test@test789.ru")
                .build();

        vitrinaApi.editUser(editUser)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("data.first_name[0]", equalTo("Поле имя обязательное"))
                .body("message", equalTo("Ошибка валидации данных"));

    }
}
