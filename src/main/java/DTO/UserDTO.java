
package DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Accessors(chain = true)

public class UserDTO {

    private String first_name;
    private String last_name;
    private String patronymic;
    private String phone;
    private String email;
    private String gender;
    private String date_of_birth;
    private String external_id;

}
