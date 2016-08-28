package tutorial.core.service.repo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tutorial.core.model.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/business-context.xml")
public class AccountRepoTest {

	@Autowired
	AccountRepo accountRepo;

	private Account account;

	@Before
	@Transactional
	public void setup(){

		account=new Account();
		account.setUsername("Shirsh");
		account.setPassword("testPassword");
	}

	@Ignore
	@Test
	@Transactional
	public void shouldFindAccountById() {

		
		Account findAccount = accountRepo.findAccount(account.getAccountId());
		
		assertNotNull(findAccount);
		assertNotNull(findAccount.getUsername());
		assertEquals(account.getUsername(),findAccount.getUsername());
	}

	@Test
	@Transactional
	public void shouldFindAccountByName() {

		accountRepo.createAccount(account);
		Account findAccount = accountRepo.findAccountByName(account.getUsername());
		assertNotNull(findAccount);
		assertNotNull(findAccount);
		assertNotNull(findAccount.getUsername());
		assertEquals(account.getUsername(),findAccount.getUsername());
	}
	

	@Transactional
	@Test
	public void shouldFindAllAccounts() {

		assertNotNull(accountRepo.findAllAccounts());
	}
	
	
	
}
