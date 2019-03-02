package pl.edu.pjatk.tau;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
@RunWith(JUnit4.class)
public class AddTest{
    Add a  = new Add();
    @Test
    public void addingCheck(){
        Assert.assertEquals(4.0, a.add(2.0, 2.0),0.001);
    }

    @Test
    public void loopCheckSum(){
        Assert.assertEquals(1.0, a.loopSum(),0.001);
    }
}
