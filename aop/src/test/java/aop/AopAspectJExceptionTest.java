package aop;

import lab.aop.AopLog;
import lab.model.Bar;
import lab.model.Customer;
import lab.model.CustomerBrokenException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:aop.xml")

class AopAspectJExceptionTest {

    private final ApplicationContext ctx = new ClassPathXmlApplicationContext("aop.xml");
    @Autowired
	private Bar bar = (Bar) ctx.getBean("bar");
    
	@Autowired
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer.setBroke(true);
    }

    @Test
    void testAfterThrowingAdvice() {
//        customer.setBroke(true);
    	assertThrows(CustomerBrokenException.class, () -> bar.sellSquishee(customer));
    	
        assertTrue(AopLog.getStringValue().contains("Hmmm..."), "Customer is not broken ");
        System.out.println(AopLog.getStringValue());
    }

    @AfterEach
    void tearDown() {
        customer.setBroke(false);
    }
}