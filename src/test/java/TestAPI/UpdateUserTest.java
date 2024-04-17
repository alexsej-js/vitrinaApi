package TestAPI;

import DTO.UserUpdateDTO;
import org.apache.http.HttpStatus;
import org.junit.Test;
import service.VitrinaApi;

import static org.hamcrest.CoreMatchers.equalTo;



public class UpdateUserTest {

    @Test
    public void updateStatusAllFieldsBlock() { // Изменения статуса участника на заблокирован

        VitrinaApi vitrinaApi = new VitrinaApi();
        UserUpdateDTO userUpdateStatus = UserUpdateDTO.builder()
                .profile_id(129l)
                .status("blocked")
                .build();

        vitrinaApi.updateUser(userUpdateStatus)
                .statusCode(HttpStatus.SC_OK)
                .body("message", equalTo("Данные успешно обновлены"));

    }

    @Test
    public void updateStatusAllFieldsActive() { // Изменения статуса участника на Активен

        VitrinaApi vitrinaApi = new VitrinaApi();
        UserUpdateDTO userUpdateStatus = UserUpdateDTO.builder()
                .profile_id(129l)
                .status("active")
                .build();

        vitrinaApi.updateUser(userUpdateStatus)
                .statusCode(HttpStatus.SC_OK)
                .body("message", equalTo("Данные успешно обновлены"));

    }

    @Test
    public void updateStatusNoFieldProfileID() { // Изменения статуса участника без поля profile_id

        VitrinaApi vitrinaApi = new VitrinaApi();
        UserUpdateDTO userUpdateStatus = UserUpdateDTO.builder()
                .status("active")
                .build();

        vitrinaApi.updateUser(userUpdateStatus)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"))
                .body("data.profile_id[0]", equalTo("Поле profile_id обязательное"));

    }

    @Test
    public void updateStatusNoFieldStatus() { // Изменения статуса участника без поля status

        VitrinaApi vitrinaApi = new VitrinaApi();
        UserUpdateDTO userUpdateStatus = UserUpdateDTO.builder()
                .profile_id(129l)
                .build();

        vitrinaApi.updateUser(userUpdateStatus)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"));
//                .body("data.status[0]", equalTo("Поле profile_id обязательное"));

    }

    @Test
    public void updateStatusUserAnotherOrganization() { // Изменения статуса участника не принадлежащего организации

        VitrinaApi vitrinaApi = new VitrinaApi();
        UserUpdateDTO userUpdateStatus = UserUpdateDTO.builder()
                .profile_id(103l)
                .status("active")
                .build();

        vitrinaApi.updateUser(userUpdateStatus)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error", equalTo("Ошибка, участник не найден"))
                .body("trace", equalTo("Ошибка, участник не найден"));

    }

    @Test
    public void updateStatusNoUser() { // Изменения статуса не существующего участника

        VitrinaApi vitrinaApi = new VitrinaApi();
        UserUpdateDTO userUpdateStatus = UserUpdateDTO.builder()
                .profile_id(103015l)
                .status("active")
                .build();

        vitrinaApi.updateUser(userUpdateStatus)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error", equalTo("Ошибка, участник не найден"))
                .body("trace", equalTo("Ошибка, участник не найден"));

    }

}
