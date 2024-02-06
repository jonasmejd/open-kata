package birthday_greetings;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

class EmployeeTest {

    @Test
    public void testBirthday() throws Exception {
        Employee employee = new Employee("foo", "bar", "1990/01/31", "a@b.c");
        Assertions.assertThat(employee.isBirthday(new XDate("2008/01/30"))).isFalse();
        Assertions.assertThat(employee.isBirthday(new XDate("2008/01/31"))).isTrue();
    }

    @Test
    public void equality() throws Exception {
        Employee base = new Employee("First", "Last", "1999/09/01", "first@last.com");
        Employee same = new Employee("First", "Last", "1999/09/01", "first@last.com");
        Employee different = new Employee("First", "Last", "1999/09/01", "boom@boom.com");

        Assertions.assertThat((base).equals(null));
        Assertions.assertThat((base).equals(""));
        Assertions.assertThat((base).equals(same));
        Assertions.assertThat((base).equals(different));
    }

}