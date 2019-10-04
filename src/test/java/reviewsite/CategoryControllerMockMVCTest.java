package reviewsite;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.mockito.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerMockMVCTest {

	@Resource
	private MockMvc mvc;

	@MockBean
	private CategoryRepository categoryRepo;

	@MockBean
	private ReviewRepository reviewRepo;

	@Mock
	private Category category1;
	
	@Mock
	private Category category2;

	@Mock
	private Review review1;
	
	@Mock
	private Review review2;
	
	@Test
	public void shouldRouteToSingleCategoryView() throws Exception {
		long testCategoryId = 1;
		when(categoryRepo.findById(testCategoryId)).thenReturn(Optional.of(category1));
		mvc.perform(get("/category?id=1")).andExpect(view().name(is("category")));
	}

	@Test
	public void shouldBeOkForSinlgeCategory() throws Exception {
		long testCategoryId = 1;
		when(categoryRepo.findById(testCategoryId)).thenReturn(Optional.of(category1));
		mvc.perform(get("/category?id=1")).andExpect(status().isOk());
	}

	@Test
	public void shouldNotBeOkForSinlgeCategory() throws Exception {
		mvc.perform(get("/category?id=1")).andExpect(status().isNotFound());
	}

	@Test
	public void shouldPutSingleCategoryIntoModel() throws Exception {
		when(categoryRepo.findById(1L)).thenReturn(Optional.of(category1));
		mvc.perform(get("/category?id=1")).andExpect(model().attribute("categories", is(category1)));
	}
	
	@Test
	public void shouldRoutetoAllCategoriesView() throws Exception {
		mvc.perform(get("/show-categories?id=1")).andExpect(view().name(is("categories")));
	}
	
	@Test
	public void shouldBeOkForAllCategories() throws Exception {
		mvc.perform(get("/show-categories?id=1")).andExpect(status().isOk());
	}
	
	@Test
	public void shouldPutAllCategoriesIntoModel() throws Exception {
		Collection<Category> allCategories = Arrays.asList(category1, category2);
		when(categoryRepo.findAll()).thenReturn(allCategories);
		mvc.perform(get("/show-categories?id=1")).andExpect(model().attribute("categories", is(allCategories)));
	}
	
	@Test
	public void shouldRouteToSingleReviewView() throws Exception{
		long testReviewId = 1;
		when(reviewRepo.findById(testReviewId)).thenReturn(Optional.of(review1));
		mvc.perform(get("/review?id=1")).andExpect(view().name(is("review")));
	}
	
	@Test
	public void shouldBeOkForSinlgeReview() throws Exception {
		long testReviewId = 1;
		when(reviewRepo.findById(testReviewId)).thenReturn(Optional.of(review1));
		mvc.perform(get("/review?id=1")).andExpect(status().isOk());
	}
	
	@Test
	public void shouldNotBeOkForSinlgeReview() throws Exception {
		mvc.perform(get("/review?id=1")).andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldPutSingleReviewIntoModel() throws Exception {
		when(reviewRepo.findById(1L)).thenReturn(Optional.of(review1));
		mvc.perform(get("/review?id=1")).andExpect(model().attribute("reviews", is(review1)));
	}
	
	@Test
	public void shouldBeOkForAllReviews() throws Exception {
		mvc.perform(get("/reviews")).andExpect(status().isOk());
	}
	
	@Test
	public void shouldRouteToAllReviewsView() throws Exception {
		mvc.perform(get("/reviews")).andExpect(view().name(is("reviews")));
	}
	
	@Test
	public void shouldPutAllReviewsIntoModel() throws Exception {
		Collection<Review> allReviews = Arrays.asList(review1, review2);
		when(reviewRepo.findAll()).thenReturn(allReviews);
		mvc.perform(get("/reviews")).andExpect(model().attribute("reviews", is(allReviews)));
	}

}
