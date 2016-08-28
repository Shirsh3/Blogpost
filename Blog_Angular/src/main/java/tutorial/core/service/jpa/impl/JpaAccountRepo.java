package tutorial.core.service.jpa.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import tutorial.core.model.Account;
import tutorial.core.model.Blog;
import tutorial.core.service.exception.AccountExistsException;
import tutorial.core.service.repo.AccountRepo;
import tutorial.core.util.SecureDataSource;

@Repository
public class JpaAccountRepo  implements AccountRepo{

	private static Logger  logger=Logger.getLogger(JpaAccountRepo.class.getName());
	
	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public Account createAccount(Account data) {

		logger.log(Level.INFO,"In JpaAccountRepo, creating new Account by persisting in database");
		try{
			em.persist(data);
		}catch(PersistenceException e){
			logger.log(Level.SEVERE,"In JpaAccountRepo, exception exists while persisting Account data");
			e.printStackTrace();
		}
		return data;
	}

	public Account findAccount(Long accountId) {
		// TODO Auto-generated method stub
		return em.find(Account.class, accountId);
		
	}
	
	public Account findAccountByName(String username){
	
		List<Account> accountList=null;
		try{
			Query query=em.createQuery("Select a from Account a where a.username =:name");
			query.setParameter("name", username);
			accountList=query.getResultList();
		}catch(Exception e){
			return null;
		}
		if(accountList.size()>1){
			return null;
		}
		else{
			return accountList.get(0);
		}
		
	}

	public List<Account> findAllAccounts() {
		// TODO Auto-generated method stub
		Query query=em.createQuery("Select a from Account a");
		
		List<Account> resultList = query.getResultList();
		return (List<Account>)resultList;
		
	}
}
