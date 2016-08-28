package tutorial.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import tutorial.core.model.BlogEntry;
import tutorial.core.util.BlogEntryList;
import tutorial.rest.mvc.BlogController;
import tutorial.rest.mvc.BlogEntryController;
import tutorial.rest.resources.BlogEntryListResource;
import tutorial.rest.resources.BlogEntryResource;

public class BlogEntryListResourceAsm extends ResourceAssemblerSupport<BlogEntryList, BlogEntryListResource> {

	public BlogEntryListResourceAsm() {
		super(BlogController.class,BlogEntryListResource.class);
		// TODO Auto-generated constructor stub
	}

	public BlogEntryListResource toResource(BlogEntryList entity) {
		// TODO Auto-generated method stub
		BlogEntryListResource resource=new BlogEntryListResource();
		List<BlogEntryResource> resources=new BlogEntryResourceAsm().toResources(entity.getBlogEntryList());
		resource.setEntries(resources);
		
		 resource.add(linkTo(methodOn(BlogController.class).getAllBlogEntries(entity.getBlogId())).withSelfRel());
		return resource;
	}
	

}
