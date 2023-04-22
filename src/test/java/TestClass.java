import org.scalatest.compatible.Assertion;
import Model.Government;
import Model.User;
import jdk.jfr.Experimental;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

public class TestClass {
    @Test
    public void testGovernment() {
        User user = new User("kiarash", "123", "dahga", "lakh;g;", "afjhgk");
        Government government = new Government(user, 0);
    }

}