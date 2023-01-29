package deck.build.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import deck.build.entity.User;
import deck.build.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		User user = new User();
		user.setEmail("lssln97@gmail.com");
		user.setPassword("password12");
		user.setFirstName("LSS");
		user.setLastName("LN");
		
		User userSaved = userRepository.save(user);
		User userFound = entityManager.find(User.class, userSaved.getId());
		
		assertThat(userFound.getEmail()).isEqualTo(user.getEmail());
	}
	
	@Test
	public void testFindUserByEmail() {
//		User user = new User();
//		user.setEmail("testfind@gmail.com");
//		user.setPassword("password12");
//		user.setFirstName("LSS");
//		user.setLastName("LN");
//		
//		userRepository.save(user);
		
		String email = "testfind@gmail.com";
		User userFound = userRepository.findByEmail(email);
		
		assertThat(userFound).isNotNull();
	}
}
