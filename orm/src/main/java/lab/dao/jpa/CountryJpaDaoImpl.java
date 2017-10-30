package lab.dao.jpa;

import lab.dao.CountryDao;
import lab.model.Country;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CountryJpaDaoImpl extends AbstractJpaDao implements CountryDao {

	@Override
	public void save(Country country) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.merge(country); //merge
		transaction.commit();
		if (em != null) {
			em.close();
		}
	}

	@Override
	public List<Country> getAllCountries() {
		List<Country> result = new ArrayList<>();
		EntityManager em = emf.createEntityManager();
		if (em != null) {
			result = em.createQuery("SELECT c from Country c", Country.class).getResultList();
			em.close();
		}
		return result;
	}

	@Override
	public Country getCountryByName(String name) {
		EntityManager em = emf.createEntityManager();
		Country country = null;
		if (em != null) {
			country = em
					.createQuery("SELECT c FROM Country c WHERE c.name LIKE :name",
							Country.class).setParameter("name", name)
					.getSingleResult();
		}
		return country;
	}

}
