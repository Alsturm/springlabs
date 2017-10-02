package aop;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import lab.aop.AopLog;
import lab.model.Bar;
import lab.model.Customer;
import lab.model.CustomerBrokenException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context.xml")

public class AopAspectJExceptionTest {

	@Autowired
	private Bar bar;
    
	@Autowired
    private Customer customer;

    @BeforeAll
    public void setUp() throws Exception {
        
//        customer.setBroke(true);
    }

    @Test
    public void testAfterThrowingAdvice() {
 
    	bar.sellSquishee(customer);
    	
        assertTrue("Customer is not broken ", AopLog.getStringValue().contains("Hmmm..."));
        System.out.println(AopLog.getStringValue());
    }
}