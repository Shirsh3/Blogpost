package tutorial.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import tutorial.core.model.Account;
import tutorial.rest.mvc.AccountController;
import tutorial.rest.mvc.BlogController;
import tutorial.rest.resources.AccountResource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

public class AccountResourceAsm extends ResourceAssemblerSupport<Account, AccountResource> {

	public AccountResourceAsm() {
		super(AccountController.class,AccountResource.class);
		// TODO Auto-generated constructor stub
	}

	public AccountResource toResource(Account account) {

		AccountResource resource=new AccountResource();
		resource.setPassword(account.getPassword());
		resource.setUsername(account.getUsername());
		
		resource.add(linkTo(methodOn(AccountController.class).getAccount(account.getAccountId())).withSelfRel());
		resource.add(linkTo(methodOn(BlogController.class).getAllBlogs()).withRel("blogs"));
		
		return resource;
	}

}
