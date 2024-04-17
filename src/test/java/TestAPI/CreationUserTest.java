package TestAPI;

import DTO.UserDTO;
import org.apache.http.HttpStatus;
import org.junit.Test;
import service.VitrinaApi;

import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;


public class CreationUserTest {
    Random random = new Random();
    int numPhone = random.nextInt(999999999-900000000) + 900000000;
    int year = random.nextInt(2005-1923) + 1923;
    int month = random.nextInt(12-10) + 10;
    int day = random.nextInt(31 - 10)  + 10;
    int numMail = random.nextInt(100000-1) + 1;

    @Test
    public void checkCreateUser() { // Проверка создания участника с заполнением всех полей
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userAllFields = UserDTO.builder()
                .first_name("Олег")
                .last_name("Сергеев")
                .patronymic("Тест")
                .date_of_birth(year + "-" + month + "-" + day)
                .gender("муж")
                .phone("+79" + numPhone)
                .email("java" + "-" + numMail + "@test.ru"  )
                .external_id("testJava" + numMail)
                .build();

        vitrinaApi.createUser(userAllFields)
                .statusCode(HttpStatus.SC_OK)
                .body("message", equalTo("Участник зарегистрирован"));

    }

    @Test
    public void checkCreateUserNoFields() { // Отправка пустого запроса при регистрации участника (-)
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userAllFields = UserDTO.builder()

                .build();

        vitrinaApi.createUser(userAllFields)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"))
                .body("data.first_name[0]", equalTo("Поле имя обязательное"));
    }

    @Test
    public void checkCreateUserNoRequiredFields() {  // Проверка создания участника с заполнением только обязательных полей
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userNoRequiredFields = UserDTO.builder()
                .first_name("Олег")
                .last_name("Сергеев")
                .phone("+79" + numPhone)
                .email("java" + "-" + numMail + "@test.ru"  )
                .build();

        vitrinaApi.createUser(userNoRequiredFields)
                .statusCode(HttpStatus.SC_OK)
                .body("message", equalTo("Участник зарегистрирован"));
    }

    @Test
    public void checkCreateUserNoFirstName() { // Проверка создания участника без обязательного поля firs_name
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userNoFirstName = UserDTO.builder()
                .last_name("Сергеев")
                .patronymic("Тест")
                .date_of_birth(year + "-" + month + "-" + day)
                .gender("муж")
                .phone("+79" + numPhone)
                .email("java" + "-" + numMail + "@test.ru"  )
                .external_id("testJava" + numMail)
                .build();

        vitrinaApi.createUser(userNoFirstName)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"))
                .body("data.first_name[0]", equalTo("Поле имя обязательное"));;
    }

    @Test
    public void checkCreateUserNoLastName() { // Проверка создания участника без обязательного поля last_name
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userNoLastName = UserDTO.builder()
                .first_name("Олег")
                .patronymic("Тест")
                .date_of_birth(year + "-" + month + "-" + day)
                .gender("муж")
                .phone("+79" + numPhone)
                .email("java" + "-" + numMail + "@test.ru"  )
                .external_id("testJava" + numMail)
                .build();

        vitrinaApi.createUser(userNoLastName)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"))
                .body("data.last_name[0]", equalTo("Поле фамилия обязательное"));
    }

    @Test
    public void checkCreateUserNoPhone() { // Проверка создания участника без обязательного поля phone
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userNoLastName = UserDTO.builder()
                .first_name("Олег")
                .last_name("Сергеев")
                .patronymic("Тест")
                .date_of_birth(year + "-" + month + "-" + day)
                .gender("муж")
                .email("java" + "-" + numMail + "@test.ru"  )
                .external_id("testJava" + numMail)
                .build();

        vitrinaApi.createUser(userNoLastName)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"))
                .body("data.phone[0]", equalTo("Поле телефон обязательное"));
    }

    @Test
    public void checkCreateUserNoEmail() { // Проверка создания участника без обязательного поля Email
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userAllFields = UserDTO.builder()
                .first_name("Олег")
                .last_name("Сергеев")
                .patronymic("Тест")
                .date_of_birth(year + "-" + month + "-" + day)
                .gender("муж")
                .phone("+79" + numPhone)
                .external_id("testJava" + numMail)
                .build();

        vitrinaApi.createUser(userAllFields)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"))
                .body("data.email[0]", equalTo("Поле email обязательное"));;
    }

    @Test
    public void checkCreateUserIncorrectPhone() { // Проверка валидации поля "phone", при регистрации участника (-)
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userAllFields = UserDTO.builder()
                .first_name("Олег")
                .last_name("Сергеев")
                .patronymic("Тест")
                .date_of_birth(year + "-" + month + "-" + day)
                .gender("муж")
                .phone("+79")
                .email("java" + "-" + numMail + "@test.ru"  )
                .external_id("testJava" + numMail)
                .build();

        vitrinaApi.createUser(userAllFields)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"))
                .body("data.phone[0]", equalTo("Неверный формат телефона"));
    }

    @Test
    public void checkCreateUserIncorrectPhone1() { // Проверка валидации поля "phone", при регистрации участника (-)
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userAllFields = UserDTO.builder()
                .first_name("Олег")
                .last_name("Сергеев")
                .patronymic("Тест")
                .date_of_birth(year + "-" + month + "-" + day)
                .gender("муж")
                .phone("+7998254")
                .email("java" + "-" + numMail + "@test.ru"  )
                .external_id("testJava" + numMail)
                .build();

        vitrinaApi.createUser(userAllFields)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"))
                .body("data.phone[0]", equalTo("Неверный формат телефона"));;
    }

    @Test
    public void checkCreateUserIncorrectPhone2() { // Проверка валидации поля "phone", при регистрации участника (-)
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userAllFields = UserDTO.builder()
                .first_name("Олег")
                .last_name("Сергеев")
                .patronymic("Тест")
                .date_of_birth(year + "-" + month + "-" + day)
                .gender("муж")
                .phone("+89168524578")
                .email("java" + "-" + numMail + "@test.ru"  )
                .external_id("testJava" + numMail)
                .build();

        vitrinaApi.createUser(userAllFields)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"))
                .body("data.phone[0]", equalTo("Неверный формат телефона"));;;
    }

    @Test
    public void checkCreateUserIncorrectPhone3() { // Проверка валидации поля "phone", при регистрации участника (-)
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userAllFields = UserDTO.builder()
                .first_name("Олег")
                .last_name("Сергеев")
                .patronymic("Тест")
                .date_of_birth(year + "-" + month + "-" + day)
                .gender("муж")
                .phone("null")
                .email("java" + "-" + numMail + "@test.ru"  )
                .external_id("testJava" + numMail)
                .build();

        vitrinaApi.createUser(userAllFields)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"))
                .body("data.phone[0]", equalTo("Неверный формат телефона"));;;
    }

    @Test
    public void checkCreateUserIncorrectPhone4() { // Проверка валидации поля "phone", при регистрации участника (-)
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userAllFields = UserDTO.builder()
                .first_name("Олег")
                .last_name("Сергеев")
                .patronymic("Тест")
                .date_of_birth(year + "-" + month + "-" + day)
                .gender("муж")
                .phone("+89168524578")
                .email("java" + "-" + numMail + "@test.ru"  )
                .external_id("testJava" + numMail)
                .build();

        vitrinaApi.createUser(userAllFields)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"))
                .body("data.phone[0]", equalTo("Неверный формат телефона"));
    }

    @Test
    public void checkCreateUserIncorrectPhone5() { // Проверка валидации поля "phone", при регистрации участника ввести номер уже зарегистрированного участника.(-)
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userAllFields = UserDTO.builder()
                .first_name("Олег")
                .last_name("Сергеев")
                .patronymic("Тест")
                .date_of_birth(year + "-" + month + "-" + day)
                .gender("муж")
                .phone("+79165710801") // номер уже зарегистирован
                .email("java" + "-" + numMail + "@test.ru"  )
                .external_id("testJava" + numMail)
                .build();


        vitrinaApi.createUser(userAllFields)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("trace", equalTo("Ошибка, данный номер телефона уже зарегистрирован"))
                .body("error", equalTo("Ошибка, данный номер телефона уже зарегистрирован"));
    }

    @Test
    public void checkCreateUserIncorrectEmail() { // Проверка валидации поля "Email", при регистрации участника (-)
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userAllFields = UserDTO.builder()
                .first_name("Олег")
                .last_name("Сергеев")
                .patronymic("Тест")
                .date_of_birth(year + "-" + month + "-" + day)
                .gender("муж")
                .phone("+79" + numPhone)
                .email("test@@test.ru"  )
                .external_id("testJava" + numMail)
                .build();

        vitrinaApi.createUser(userAllFields)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"))
                .body("data.email[0]", equalTo("Неверный формат email"));
    }

    @Test
    public void checkCreateUserIncorrectEmail1() { // Проверка валидации поля "Email", при регистрации участника (-)
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userAllFields = UserDTO.builder()
                .first_name("Олег")
                .last_name("Сергеев")
                .patronymic("Тест")
                .date_of_birth(year + "-" + month + "-" + day)
                .gender("муж")
                .phone("+79" + numPhone)
                .email("@test.ru"  )
                .external_id("testJava" + numMail)
                .build();

        vitrinaApi.createUser(userAllFields)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"))
                .body("data.email[0]", equalTo("Неверный формат email"));
    }

    @Test
    public void checkCreateUserIncorrectEmail2() { // Проверка валидации поля "Email", при регистрации участника (-)
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userAllFields = UserDTO.builder()
                .first_name("Олег")
                .last_name("Сергеев")
                .patronymic("Тест")
                .date_of_birth(year + "-" + month + "-" + day)
                .gender("муж")
                .phone("+79" + numPhone)
                .email("test@test....com"  )
                .external_id("testJava" + numMail)
                .build();

        vitrinaApi.createUser(userAllFields)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"))
                .body("data.email[0]", equalTo("Неверный формат email"));
    }

    @Test
    public void checkCreateUserIncorrectEmail3() { // Проверка валидации поля "Email", при регистрации участника (-)
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userAllFields = UserDTO.builder()
                .first_name("Олег")
                .last_name("Сергеев")
                .patronymic("Тест")
                .date_of_birth(year + "-" + month + "-" + day)
                .gender("муж")
                .phone("+79" + numPhone)
                .email("testtest.com"  )
                .external_id("testJava" + numMail)
                .build();

        vitrinaApi.createUser(userAllFields)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"))
                .body("data.email[0]", equalTo("Неверный формат email"));
    }
    @Test
    public void checkCreateUserIncorrectEmail4() { // Проверка валидации поля "Email", при регистрации участника (-)
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userAllFields = UserDTO.builder()
                .first_name("Олег")
                .last_name("Сергеев")
                .patronymic("Тест")
                .date_of_birth(year + "-" + month + "-" + day)
                .gender("муж")
                .phone("+79" + numPhone)
                .email("te st@test.com"  )
                .external_id("testJava" + numMail)
                .build();

        vitrinaApi.createUser(userAllFields)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"))
                .body("data.email[0]", equalTo("Неверный формат email"));
    }
    @Test
    public void checkCreateUserIncorrectEmail5() { // Проверка валидации поля "Email", при регистрации участника (-)
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userAllFields = UserDTO.builder()
                .first_name("Олег")
                .last_name("Сергеев")
                .patronymic("Тест")
                .date_of_birth(year + "-" + month + "-" + day)
                .gender("муж")
                .phone("+79" + numPhone)
                .email("test@te st.com"  )
                .external_id("testJava" + numMail)
                .build();

        vitrinaApi.createUser(userAllFields)
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("message", equalTo("Ошибка валидации данных"))
                .body("data.email[0]", equalTo("Неверный формат email"));
    }
    @Test
    public void checkCreateUserIncorrectEmail6() { // Создание участника с уже существующим "Email" (-)
        VitrinaApi vitrinaApi = new VitrinaApi();
        UserDTO userAllFields = UserDTO.builder()
                .first_name("Олег")
                .last_name("Сергеев")
                .patronymic("Тест")
                .date_of_birth(year + "-" + month + "-" + day)
                .gender("муж")
                .phone("+79" + numPhone)
                .email("test@test598.ru") // уже зарегистрированный Email
                .external_id("testJava" + numMail)
                .build();

        vitrinaApi.createUser(userAllFields)
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("trace", equalTo("Ошибка, данный email уже зарегистрирован"))
                .body("error", equalTo("Ошибка, данный email уже зарегистрирован"));
    }

}
