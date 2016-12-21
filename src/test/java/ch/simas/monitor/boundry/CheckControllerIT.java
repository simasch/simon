package ch.simas.monitor.boundry;

import ch.simas.monitor.xml.Hosts;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckControllerIT {

    @Autowired
    private CheckController checkController;
    
    @Test
    public void getLatest() {
        Hosts latest = checkController.getLatest();
        
        Assert.assertNotNull(latest);
    }

}
