package tutorial.core.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tutorial.core.model.Account;
import tutorial.core.model.Blog;
import tutorial.core.model.BlogEntry;
import tutorial.core.service.AccountService;
import tutorial.core.service.BlogEntryService;
import tutorial.core.service.exception.AccountExistsException;
import tutorial.core.service.repo.AccountRepo;
import tutorial.core.service.repo.BlogEntryRepo;
import tutorial.core.util.AccountList;

@Service
@Transactional
public class BlogEntryServiceImpl implements BlogEntryService {

	@Autowired
	BlogEntryRepo blogEntryRepo;
	public BlogEntry findBlogEntry(Long blogId) {
		// TODO Auto-generated method stub
		return blogEntryRepo.findBlogEntry(blogId);
	}

	public BlogEntry deleteBlogEntry(Long blogId) {

		return blogEntryRepo.deleteBlogEntry(blogId);
	}

	public BlogEntry updateBlogEntry(Long blogId,BlogEntry entry) {
		
		return blogEntryRepo.updateBlogEntry(blogId,entry);
	}

}
