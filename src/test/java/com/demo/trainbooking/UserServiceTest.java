//import com.demo.booking.train.dto.UserRequest;
//import com.demo.booking.train.exception.UserAlreadyExistsException;
//import com.demo.booking.train.exception.UserNotFoundException;
//import com.demo.booking.train.impl.UserServiceImpl;
//import com.demo.booking.train.model.User;
//import com.demo.booking.train.service.UserService;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//import org.mockito.Mockito;
//import org.junit.Assert;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.mockito.Mockito.when;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BbCallerApplication.class)
//@ActiveProfiles("test")
//public class UserServiceTest {
//
//    @Autowired
//    private UserService userService;
//
//    @Test
//    public void testCreateUserSuccess() {
//        UserRequest request = new UserRequest();
//        request.setEmail("test@example.com");
//        request.setFirstName("test");
//        request.setLastName("t");
//
//        User user = new User(); // Initialize with appropriate values
//        user.setEmail("test@example.com");
//
////        when(userService.createUser(request)).thenReturn(user);
//
//        User result = userService.createUser(request);
//        Assert.assertNotNull(result);
//        Assert.assertEquals("test@example.com", result.getEmail());
//    }
//
//    @Test
//    public void testCreateUserAlreadyExists() {
//        UserRequest request = new UserRequest();
//        request.setEmail("test@example.com");
//
////        when(userService.createUser(request)).thenThrow(new UserAlreadyExistsException("test@example.com"));
//
//        Assert.assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(request));
//    }
//
//}