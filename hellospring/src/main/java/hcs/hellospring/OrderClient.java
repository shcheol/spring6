package hcs.hellospring;

import hcs.hellospring.order.Order;
import hcs.hellospring.order.OrderService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;

public class OrderClient {

	public static void main(String[] args) {
		BeanFactory beanFactory = new AnnotationConfigApplicationContext(OrderConfig.class);
		OrderService service = beanFactory.getBean(OrderService.class);

		Order order = service.createOrder("0100", BigDecimal.TEN);
		System.out.println(order);
	}
}
