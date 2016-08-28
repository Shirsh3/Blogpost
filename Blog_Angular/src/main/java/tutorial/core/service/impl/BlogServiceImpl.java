package tutorial.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tutorial.core.model.Account;
import tutorial.core.model.Blog;
import tutorial.core.model.BlogEntry;
import tutorial.core.service.AccountService;
import tutorial.core.service.BlogService;
import tutorial.core.service.exception.AccountExistsException;
import tutorial.core.service.exception.BlogNotFoundException;
import tutorial.core.service.repo.AccountRepo;
import tutorial.core.service.repo.BlogEntryRepo;
import tutorial.core.service.repo.BlogRepo;
import tutorial.core.util.AccountList;
import tutorial.core.util.BlogEntryList;
import tutorial.core.util.BlogList;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

	@Autowired
	BlogRepo blogRepo;
	
	@Autowired
	BlogEntryRepo blogEntryRepo;
	
	public BlogEntry createBlogEntry(Long blogId, BlogEntry data) {

		/*
		 * 1) FInd the associated blog
		 */
		Blog blog=blogRepo.findBlog(blogId);
		if(blog==null){
			throw new BlogNotFoundException();
		}
		/*
		 * 2) Create new BlogEntry against Find blogid.
		 */
		BlogEntry createdBlogEntry=blogEntryRepo.createBlogEntry(data);
		createdBlogEntry.setBlog(blog);
		return createdBlogEntry;
		
	}

	public Blog findBlog(Long blogId) {
		// TODO Auto-generated method stub
		if(blogId!=null){
			
			return blogRepo.findBlog(blogId);
		}
		return null;
	}

	public BlogEntryList findAllBlogEntries(Long blogId) {
		// TODO Auto-generated method stub
		if(blogId!=null){
			
			/*
			 * First find if blog exists.
			 */
			Blog isBlogExists=blogRepo.findBlog(blogId);
			if(isBlogExists==null){
				
				throw new BlogNotFoundException();
			}
			
			return new BlogEntryList(blogId,blogEntryRepo.findByBlogId(blogId));
			
		}
		
		return null;
	}

	public BlogList findAllBlogs() {
		
		return new BlogList(blogRepo.findAllBlogs());
	}
	
	
}
