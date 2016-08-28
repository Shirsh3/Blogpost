package tutorial.core.service.repo;

import java.util.List;

import tutorial.core.model.Account;
import tutorial.core.model.Blog;

public interface AccountRepo {

	public Account createAccount(Account data);
	public Account findAccount(Long accountId);
	public Account findAccountByName(String name);
	public List<Account> findAllAccounts();
}
