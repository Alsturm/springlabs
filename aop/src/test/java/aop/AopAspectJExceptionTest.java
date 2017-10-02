package aop;

import lab.aop.AopLog;
import lab.model.Bar;
import lab.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration("classpath:application-context.xml")

public class AopAspectJExceptionTest {

	@Autowired
	private Bar bar;
    
	@Autowired
    private Customer customer;

//    @BeforeAll
//    public void setUp() {
//        customer.setBroke(true);
//    }

    @Test
    public void testAfterThrowingAdvice() {
        customer.setBroke(true);
    	bar.sellSquishee(customer);
    	
        assertTrue(AopLog.getStringValue().contains("Hmmm..."), "Customer is not broken ");
        System.out.println(AopLog.getStringValue());
    }
}