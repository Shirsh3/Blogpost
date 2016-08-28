package tutorial.core.service;

import java.util.List;

import tutorial.core.model.Account;
import tutorial.core.model.Blog;
import tutorial.core.service.exception.BlogNotFoundException;
import tutorial.core.util.AccountList;


public interface AccountService {

	public Account createAccount(Account data);
	public Account findAccount(Long accountId);
	public Blog createBlog(Long accountId,Blog data);
	public AccountList findAllAccounts();
	
}
