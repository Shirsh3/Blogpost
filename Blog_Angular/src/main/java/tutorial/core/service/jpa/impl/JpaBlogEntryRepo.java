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
import tutorial.core.model.BlogEntry;
import tutorial.core.service.exception.AccountExistsException;
import tutorial.core.service.repo.AccountRepo;
import tutorial.core.service.repo.BlogEntryRepo;

@Repository
public class JpaBlogEntryRepo  implements BlogEntryRepo{

	@PersistenceContext
	EntityManager em;

	public BlogEntry findBlogEntry(Long blogId) {
		// TODO Auto-generated method stub
		return em.find(BlogEntry.class, blogId);
	}

	public BlogEntry deleteBlogEntry(Long blogId) {
		// TODO Auto-generated method stub
		BlogEntry entity=em.find(BlogEntry.class, blogId);
		if(entity!=null){
			em.remove(entity);
			return entity;
		}
		return null;
	}

	public BlogEntry updateBlogEntry(Long blogId,BlogEntry data) {
		// TODO Auto-generated method stub
		BlogEntry entity=em.find(BlogEntry.class, blogId);
		if(entity!=null && data!=null){
			entity.setContent(data.getContent());
			entity.setTitle(data.getTitle());
			return entity;
		}
		return null;
	}

	public BlogEntry createBlogEntry(BlogEntry data) {

		if(data!=null){
			
			em.persist(data);
			return data;
		}
		return null;
	}

	public List<BlogEntry> findByBlogId(Long blogId) {
		// TODO Auto-generated method stub
		Query query=em.createQuery("Select b from BlogEntry b where b.blogId :=blogId");
		query.setParameter("blogId", blogId);
		
		return query.getResultList();
		
		
	}

	
}
