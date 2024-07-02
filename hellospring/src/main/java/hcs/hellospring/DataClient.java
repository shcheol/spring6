package hcs.hellospring;

import hcs.hellospring.data.OrderRepository;
import hcs.hellospring.order.Order;
import hcs.hellospring.payment.PaymentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.RollbackException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

public class DataClient {

	public static void main(String[] args) {
		BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
		OrderRepository emf = beanFactory.getBean(OrderRepository.class);

		JpaTransactionManager tm = beanFactory.getBean(JpaTransactionManager.class);

		try {
			new TransactionTemplate(tm).execute((TransactionCallback<Order>) status -> {

				Order order = new Order("100", BigDecimal.TEN);
				emf.save(order);
				System.out.println(order);

				Order order2 = new Order("100", BigDecimal.TEN);
				emf.save(order2);

				return null;
			});
		} catch (DataIntegrityViolationException e) {
			System.out.println(" recover");
		}

	}
}
