package rastak.train.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Utils {

    Logger logger = LoggerFactory.getLogger(Utils.class);

    private String generateRandomString() {
        logger.info("GenerateRandomString() has called");

        Random random = new Random();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder returnValue = new StringBuilder();
        int length = Constanst.PUBLIC_ID_LENGTH;
        for (int i = 0; i < length; i++) {
            returnValue.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return returnValue.toString();
    }

    public String getPublicId() {
        return generateRandomString();
    }
}
