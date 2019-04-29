package cc.dt.mch.demo.pojo;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
public class User {
    private Integer id;
    private String password;

    private String gender;

    private Integer age;

    private String nation;

    private String maritalStatus;

    private Date birth;

    private String idNumber;

    private String educationBackground;

    private String universityDegree;
}

