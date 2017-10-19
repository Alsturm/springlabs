package aop;

import lab.aop.AopLog;
import lab.model.Bar;
import lab.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration("classpath:aop.xml")

public class AopAspectJExceptionTest {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("aop.xml");
//    @Autowired
	private Bar bar = (Bar) ctx.getBean("bar");
    
//	@Autowired
//    private Customer customer;
    private Customer customer = (Customer) ctx.getBean("customer");

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