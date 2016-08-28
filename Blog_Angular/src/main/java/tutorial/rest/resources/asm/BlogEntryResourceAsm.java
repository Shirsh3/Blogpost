package tutorial.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import tutorial.core.model.BlogEntry;
import tutorial.rest.mvc.AccountController;
import tutorial.rest.mvc.BlogController;
import tutorial.rest.mvc.BlogEntryController;
import tutorial.rest.resources.BlogEntryResource;

public class BlogEntryResourceAsm extends ResourceAssemblerSupport<BlogEntry, BlogEntryResource> {

	
	public BlogEntryResourceAsm(){
		
		super(BlogEntryController.class,BlogEntryResource.class);
	}
	
	public BlogEntryResource toResource(BlogEntry entry) {

		BlogEntryResource res=new BlogEntryResource();
		res.setTitle(entry.getTitle());
		res.setContent(entry.getContent());
		res.setEntryId(entry.getId());
		res.add(linkTo(methodOn(BlogEntryController.class).getExistingBlogEntry(entry.getId())).withSelfRel());
		
		if(entry.getBlog()!=null){
			
			res.add(linkTo(methodOn(BlogController.class).getBlog(entry.getBlog().getId())).withRel("blog"));
		}
		
		return res;
	}

}
