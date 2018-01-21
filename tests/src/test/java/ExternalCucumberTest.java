import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)

@CucumberOptions(
        features = {"src/test/java/CreateUser.feature",
                "src/test/java/PayAtMerchant.feature",
                "src/test/java/RequestToken.feature",
                "src/test/java/TokenDoubleSpend.feature"
        }
)
public class ExternalCucumberTest {

}
