package reviewsite;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CategoryPopulator implements CommandLineRunner {
	
	@Resource
	private CategoryRepository categoryRepo;
	
	@Resource
	private ReviewRepository reviewRepo;

	@Override
	public void run(String... args) throws Exception {

		Review review1 = new Review("this is review1");
		review1 = reviewRepo.save(review1);
		Review review2 = new Review("this is review2");
		review2 = reviewRepo.save(review2);
		Review review3 = new Review("this is review3");
		review3 = reviewRepo.save(review3);
		
		Category category1 = new Category("Category 1","This is the first category", review1);
		category1 = categoryRepo.save(category1);
		
		Category category2 = new Category("Category 2","This is the second category", review2, review3);
		category2 = categoryRepo.save(category2);
	}
	
	

}
