package tutorial.rest.resources.asm;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import tutorial.core.util.BlogEntryList;
import tutorial.core.util.BlogList;
import tutorial.rest.mvc.BlogController;
import tutorial.rest.resources.BlogEntryListResource;
import tutorial.rest.resources.BlogEntryResource;
import tutorial.rest.resources.BlogListResource;
import tutorial.rest.resources.BlogResource;

public class BlogListResourceAsm extends ResourceAssemblerSupport<BlogList, BlogListResource>{

	public BlogListResourceAsm() {
		super(BlogController.class,BlogListResource.class);
		// TODO Auto-generated constructor stub
	}

	public BlogListResource toResource(BlogList entity) {
		// TODO Auto-generated method stub
		BlogListResource resource=new  BlogListResource();
		resource.setBlogs(new BlogResourceAsm().toResources(entity.getBlogs()));
		return resource;
	}
	
}
