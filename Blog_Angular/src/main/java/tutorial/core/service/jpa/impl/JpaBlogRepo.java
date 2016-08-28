package tutorial.core.service.jpa.impl;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import tutorial.core.model.Account;
import tutorial.core.model.Blog;
import tutorial.core.service.exception.AccountExistsException;
import tutorial.core.service.repo.AccountRepo;
import tutorial.core.service.repo.BlogRepo;

@Repository
public class JpaBlogRepo  implements BlogRepo{

	@PersistenceContext
	EntityManager em;

	public Blog createBlog(Blog data) {
		
		if(data!=null){
			
			em.persist(data);
			return data;
			
		}
		return null;
		
	}

	public List<Blog> findAllBlogs() {

		Query query=em.createQuery("Select b from Blog b ");
		return query.getResultList();
		
	}

	public Blog findBlog(Long id) {
		
		return em.find(Blog.class, id);
	}

	public Blog findBlogByTitle(String title) {
		
		Query query=em.createQuery("Select b from Blog b where b.title =:title");
		query.setParameter("title", title);
		
		List<Blog> blogList=query.getResultList();
		
		if(blogList.size()!=1)
		{
			return null;
		}
		return blogList.get(0);
		
	}

	public List<Blog> findBlogsByAccount(Long accountId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
