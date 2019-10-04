package reviewsite;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

public class CategoryControllerTest {
	
	@InjectMocks
	private CategoryController underTest;
	
	@Mock
	private Category category1;
	
	@Mock
	private Category category2;
	
	@Mock
	private Review review1;
	
	@Mock 
	private Review review2;
	
	@Mock
	private CategoryRepository categoryRepo;
	
	@Mock
	private ReviewRepository reviewRepo;
	
	@Mock
	private Model model;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldAddSingleCategoryToModel() throws CategoryNotFoundException {
		long testCategoryId = 1;
		when(categoryRepo.findById(testCategoryId)).thenReturn(Optional.of(category1));
		
		underTest.findOneCategory(testCategoryId, model);
		verify(model).addAttribute("categories", category1);
	}
	
	@Test
	public void shouldAddAllCategoriesToModel() {
		Collection<Category> allCategories = Arrays.asList(category1, category2);
		when(categoryRepo.findAll()).thenReturn(allCategories);
		
		underTest.findAllCategories(model);
		verify(model).addAttribute("categories", allCategories);
	}
	
	@Test
	public void shouldAddSingleReviewToModel() throws ReviewNotFoundException {
		long testReviewId = 1;
		when(reviewRepo.findById(testReviewId)).thenReturn(Optional.of(review1));
		
		underTest.FindOneReview(testReviewId, model);
		
		verify(model).addAttribute("reviews", review1);
	}
	
	@Test
	public void shouldAddAllReviewsToModel() {
		Collection<Review> allReviews = Arrays.asList(review1, review2);
		when(reviewRepo.findAll()).thenReturn(allReviews);
		
		underTest.findAllReviews(model);
		verify(model).addAttribute("reviews",allReviews);
		
	}

}
