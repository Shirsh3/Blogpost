package tutorial.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import tutorial.core.model.Account;
import tutorial.core.model.Blog;
import tutorial.rest.mvc.AccountController;
import tutorial.rest.mvc.BlogController;
import tutorial.rest.resources.BlogResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

public class BlogResourceAsm extends ResourceAssemblerSupport<Blog, BlogResource> {

	public BlogResourceAsm() {
		super(BlogController.class,BlogResource.class);
		// TODO Auto-generated constructor stub
	}

	public BlogResource toResource(Blog blog) {

		BlogResource resource=new BlogResource();
		resource.setTitle(blog.getTitle());
		resource.add(linkTo(BlogController.class).slash(blog.getId()).withSelfRel());
		resource.add(linkTo(BlogController.class).slash(blog.getId()).slash("blog-entries").withRel("entries"));
		
		if(blog.getOwner()!=null){
			resource.add(linkTo(AccountController.class).slash(blog.getOwner().getAccountId()).withRel("owner"));
		}
		return resource;
	}

}
