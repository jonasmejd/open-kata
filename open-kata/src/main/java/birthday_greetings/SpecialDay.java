package birthday_greetings;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SpecialDay {


    public static String planningBirthDay() throws IOException {
        String jsonEmployee = new String(Files.readAllBytes(Paths.get("src/main/resources/employee.json")), StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Employee> employees = objectMapper.readValue(jsonEmployee, new TypeReference<List<Employee>>(){});

        return jsonEmployee;
    }

}
