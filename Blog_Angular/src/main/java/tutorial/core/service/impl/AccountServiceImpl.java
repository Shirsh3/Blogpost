package tutorial.core.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tutorial.core.model.Account;
import tutorial.core.model.Blog;
import tutorial.core.service.AccountService;
import tutorial.core.service.exception.AccountExistsException;
import tutorial.core.service.exception.AccountNotFoundException;
import tutorial.core.service.exception.BlogExistsException;
import tutorial.core.service.repo.AccountRepo;
import tutorial.core.service.repo.BlogRepo;
import tutorial.core.util.AccountList;
import tutorial.core.util.SecureDataSource;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	private static Log logger=LogFactory.getLog(AccountServiceImpl.class);
	@Autowired
	AccountRepo accountRepo;
	
	@Autowired
	BlogRepo blogRepo;
	
	public Account createAccount(Account data) {
		// TODO Auto-generated method stub
		AccountServiceImpl.logger.info("Account creation starts");
		Account account= accountRepo.createAccount(data);
		
		if(account.getUsername()!=null){
			Account isAccountExists=accountRepo.findAccountByName(account.getUsername());
			
			if(isAccountExists==null){
				AccountServiceImpl.logger.error("Exception");
				throw new AccountExistsException("Account already exists with name " + isAccountExists.getUsername());
			}
		}
		return account;
	}

	public Account findAccount(Long accountId) {
		// TODO Auto-generated method stub
		return accountRepo.findAccount(accountId);
	}

	public Blog createBlog(Long accountId, Blog data) {
		
		Blog isBlogExisstByTitle=null;
		Account isAccountExists=null;
		/*
		 * 1) Check if blog already Exists
		 */
		if(data!=null){
			isBlogExisstByTitle=blogRepo.findBlogByTitle(data.getTitle());
			
			if(isBlogExisstByTitle!=null){
				throw new BlogExistsException();
			}
		}
		/*
		 * Check if account exists.
		 */
		if(accountId!=null){
			
			isAccountExists=accountRepo.findAccount(accountId);
			if(isAccountExists==null){
				
				throw new AccountNotFoundException();
			}
		}
		
		/*
		 * Create new blog associated with given account 
		 */
		Blog createdBlog=blogRepo.createBlog(data);
		createdBlog.setOwner(isAccountExists);
		
		return createdBlog;
	}

	public AccountList findAllAccounts() {
		// TODO Auto-generated method stub
		return new AccountList(accountRepo.findAllAccounts());
	}

}
