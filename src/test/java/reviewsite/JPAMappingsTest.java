package reviewsite;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class JPAMappingsTest {

	@Resource
	private TestEntityManager entityManager;

	@Resource
	private ReviewRepository reviewRepo;

	@Resource
	private CategoryRepository categoryRepo;

	@Test
	public void shouldSaveAndLoadReview() {
		Review review = reviewRepo.save(new Review("review"));
		long reviewId = review.getId();

		entityManager.flush(); // forces JPA to hit database when we try to find it
		entityManager.clear();

		Optional<Review> result = reviewRepo.findById(reviewId);
		review = result.get();
		assertThat(review.getName(), is("review"));
	}

	@Test
	public void shouldGenerateReviewId() {
		Review review = reviewRepo.save(new Review("review"));
		long reviewId = review.getId();

		entityManager.flush(); // forces JPA to hit database when we try to find it
		entityManager.clear();

		assertThat(reviewId, is(greaterThan(0L)));
	}

	@Test
	public void shouldSaveAndLoadCategory() {
		Category category = categoryRepo.save(new Category("category name", "description"));
		long categoryId = category.getId();

		entityManager.flush(); // forces JPA to hit database when we try to find it
		entityManager.clear();

		Optional<Category> result = categoryRepo.findById(categoryId);
		category = result.get();
		assertThat(category.getName(), is("category name"));
	}

	@Test
	public void shouldEstablishCategoryToReviewsRelationships() {
		// review is not the owner so we create these first
		Review review1 = reviewRepo.save(new Review("review 1"));
		Review review2 = reviewRepo.save(new Review("review 2"));

		Category category = new Category("OO Catergories", "description", review1, review2);
		category = categoryRepo.save(category);
		long categoryId = category.getId();

		entityManager.flush(); // forces JPA to hit database when we try to find it
		entityManager.clear();

		Optional<Category> result = categoryRepo.findById(categoryId);
		category = result.get();

		assertThat(category.getReviews(), containsInAnyOrder(review1, review2));
	}

	@Test
	public void shouldFindCategoriesForReview() {
		Review review1 = reviewRepo.save(new Review("review1"));

		Category category1 = categoryRepo.save(new Category("OO Categories", "Description", review1));
		Category category2 = categoryRepo.save(new Category("Category2", "Description", review1));

		entityManager.flush(); // forces JPA to hit database when we try to find it
		entityManager.clear();

		Collection<Category> categoriesForReview = categoryRepo.findByReviewsContains(review1);

		assertThat(categoriesForReview, containsInAnyOrder(category1, category2));
	}

	@Test
	public void shouldFindCategoriesForReviewId() {
		Review review1 = reviewRepo.save(new Review("review1"));
		long reviewId = review1.getId();

		Category category1 = categoryRepo.save(new Category("OO Categories", "Description", review1));
		Category category2 = categoryRepo.save(new Category("Category2", "Description", review1));

		entityManager.flush(); // forces JPA to hit database when we try to find it
		entityManager.clear();

		Collection<Category> categoriesForReview = categoryRepo.findByReviewsId(reviewId);

		assertThat(categoriesForReview, containsInAnyOrder(category1, category2));
	}

}
