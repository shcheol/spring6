package hcs.hellospring.data;

import hcs.hellospring.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;

public class OrderRepository {

	@PersistenceContext
	EntityManager em;

	public void save(Order order){

//		EntityTransaction transaction = em.getTransaction();
//		transaction.begin();
//		try {
			em.persist(order);
//
//			transaction.commit();
//		}catch (RuntimeException e){
//			if (transaction.isActive()) transaction.rollback();
//			throw e;
//		}finally {
//			if (em.isOpen())em.close();
//		}
	}
}
