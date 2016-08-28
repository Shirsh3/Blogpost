package tutorial.rest.mvc;


import java.net.URI;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import tutorial.core.model.Blog;
import tutorial.core.model.BlogEntry;
import tutorial.core.service.BlogEntryService;
import tutorial.core.service.BlogService;
import tutorial.core.service.exception.BlogExistsException;
import tutorial.core.service.exception.BlogNotFoundException;
import tutorial.core.service.impl.AccountServiceImpl;
import tutorial.core.util.BlogEntryList;
import tutorial.core.util.BlogList;
import tutorial.rest.mvc.exception.ConflictException;
import tutorial.rest.mvc.exception.NotFoundException;
import tutorial.rest.resources.BlogEntryListResource;
import tutorial.rest.resources.BlogEntryResource;
import tutorial.rest.resources.BlogListResource;
import tutorial.rest.resources.BlogResource;
import tutorial.rest.resources.asm.BlogEntryListResourceAsm;
import tutorial.rest.resources.asm.BlogEntryResourceAsm;
import tutorial.rest.resources.asm.BlogListResourceAsm;
import tutorial.rest.resources.asm.BlogResourceAsm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RequestMapping("/rest/blogs")
@Controller
public class BlogController{

	private static Logger  logger=Logger.getLogger(AccountServiceImpl.class.getName());
	BlogService service;
	@Autowired
	public BlogController(BlogService service) {
		this.service = service;
	}
	
	@RequestMapping(value="/{blogId}/blog-entries",method=RequestMethod.POST)
	public ResponseEntity<BlogEntryResource> createBlogEntry(@PathVariable Long blogId,
		@RequestBody BlogEntryResource blogEntryResource){
			
		BlogEntry entry=null;	
		try{

			entry=service.createBlogEntry(blogId,blogEntryResource.toBlogEntry());
			BlogEntryResource resource=new BlogEntryResourceAsm().toResource(entry);
			HttpHeaders headers=new HttpHeaders();
			headers.setLocation(URI.create(resource.getLink("self").getHref()));
			return new ResponseEntity<BlogEntryResource>(resource,headers,HttpStatus.CREATED);
		}catch(BlogNotFoundException ex){
			throw new NotFoundException();
		}
	}
	@RequestMapping(value="/{blogId}",method=RequestMethod.GET)	
	public ResponseEntity<BlogResource> getBlog(@PathVariable Long blogId){
		
		try{
		
			Blog blog=service.findBlog(blogId);
			BlogResource resource=new BlogResourceAsm().toResource(blog);
			return new ResponseEntity<BlogResource>(resource, HttpStatus.OK);
		}catch(BlogNotFoundException ex){
			throw new NotFoundException();
		}
	}
	
	@RequestMapping(value="/{blogId}/blog-entries",method=RequestMethod.GET)
	public ResponseEntity<BlogEntryListResource> getAllBlogEntries(@PathVariable Long blogId){
		
		try{
			BlogEntryList blogEntryList= service.findAllBlogEntries(blogId);
			BlogEntryListResource resource=new BlogEntryListResourceAsm().toResource(blogEntryList);
			return new ResponseEntity<BlogEntryListResource>(resource,HttpStatus.OK);
		}catch(BlogNotFoundException ex){
		throw new NotFoundException(ex);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<BlogListResource> getAllBlogs(){
		
		try{
			BlogList blogs=service.findAllBlogs();
			BlogListResource resource=new BlogListResourceAsm().toResource(blogs);
			return new ResponseEntity<BlogListResource>(resource,HttpStatus.OK);
		}catch(BlogNotFoundException ex){
			throw new NotFoundException(ex);
		}
	}
	
	
}
