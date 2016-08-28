package tutorial.rest.mvc;


import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import tutorial.core.model.Account;
import tutorial.core.model.Blog;
import tutorial.core.service.AccountService;
import tutorial.core.service.exception.AccountExistsException;
import tutorial.core.service.exception.AccountNotFoundException;
import tutorial.core.service.exception.BlogExistsException;
import tutorial.core.service.impl.AccountServiceImpl;
import tutorial.rest.mvc.exception.BadRequestException;
import tutorial.rest.mvc.exception.ConflictException;
import tutorial.rest.resources.AccountResource;
import tutorial.rest.resources.BlogResource;
import tutorial.rest.resources.asm.AccountResourceAsm;
import tutorial.rest.resources.asm.BlogResourceAsm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RequestMapping("/rest/accounts")
@Controller
public class AccountController{

	private static Log logger=LogFactory.getLog(AccountServiceImpl.class);
	AccountService service;

	@Autowired
	public AccountController(AccountService service) {
		this.service = service;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<AccountResource> createAccount(@RequestBody AccountResource sentAccount){
		
		AccountController.logger.info("In AccountController, will hit service layer for account creation.");
		try{
			Account createdAccount=service.createAccount(sentAccount.toAccount());
			AccountResource createAccountResource=new AccountResourceAsm().toResource(createdAccount);
			HttpHeaders headers=new HttpHeaders();
			headers.setLocation(URI.create(createAccountResource.getLink("self").getHref()));
			logger.info("In AccountController, account successully created with id:"+ createdAccount.getAccountId());
			return new ResponseEntity<AccountResource>(createAccountResource,headers,HttpStatus.CREATED);
		}catch(AccountExistsException ex){
			AccountController.logger.error(ex.getMessage(),ex); 
			throw new ConflictException(ex);
		}
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<AccountResource> getAccount(@PathVariable long id){
		
		Account existingAccount=service.findAccount(id);
		if(existingAccount==null){
			return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
		}
		AccountResource createAccount=new AccountResourceAsm().toResource(existingAccount);
		
		return new ResponseEntity<AccountResource>(createAccount,HttpStatus.OK);
	}
	
	@RequestMapping(value="/{accountId}/blogs", method=RequestMethod.POST)
	public ResponseEntity<BlogResource> createBlog(@PathVariable Long accountId,@RequestBody BlogResource data){
		
		try{
			/*
			 * while creating 1) Create using service methods 
			 * 	2) Redirect using httpheaders
			 */
			Blog createdBlog=service.createBlog(accountId, data.toBlog());
			BlogResource resource=new BlogResourceAsm().toResource(createdBlog);
			HttpHeaders headers=new HttpHeaders();
			headers.setLocation(URI.create(resource.getLink("self").getHref()));
			return new ResponseEntity<BlogResource>(resource,headers,HttpStatus.CREATED);
					
		}catch(AccountNotFoundException ex){
			throw new BadRequestException(ex);
		}catch(BlogExistsException e){
			throw new ConflictException();
		}
	}
	
	
}
