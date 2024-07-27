package hcs.hellospring.data;

import hcs.hellospring.order.Order;
import hcs.hellospring.order.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class JpaOrderRepository implements OrderRepository {

	@PersistenceContext
	EntityManager em;

	@Override
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
