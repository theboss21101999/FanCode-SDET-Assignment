package com.fancode.assignment.service;
import com.fancode.assignment.model.Todo;
import com.fancode.assignment.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ApiServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @InjectMocks
    private ApiService apiService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        apiService = new ApiService(restTemplateBuilder);
    }

    @Test
    public void testGetUsers() {
        User[] users = new User[]{
                new User() {{
                    setId(1);
                    setName("Leanne Graham");
                    setUsername("Bret");
                    setEmail("Sincere@april.biz");
                    setAddress(new Address() {{
                        setGeo(new Geo() {{
                            setLat("-37.3159");
                            setLng("81.1496");
                        }});
                    }});
                }}
        };
        ResponseEntity<User[]> responseEntity = new ResponseEntity<>(users, HttpStatus.OK);
        when(restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users", User[].class)).thenReturn(responseEntity);

        List<User> result = apiService.getUsers();
        assertEquals(1, result.size());
        assertEquals("Leanne Graham", result.get(0).getName());
    }

    @Test
    public void testGetTodos() {
        Todo[] todos = new Todo[]{
                new Todo() {{
                    setUserId(1);
                    setId(1);
                    setTitle("delectus aut autem");
                    setCompleted(false);
                }}
        };
        ResponseEntity<Todo[]> responseEntity = new ResponseEntity<>(todos, HttpStatus.OK);
        when(restTemplate.getForEntity("https://jsonplaceholder.typicode.com/todos", Todo[].class)).thenReturn(responseEntity);

        List<Todo> result = apiService.getTodos();
        assertEquals(1, result.size());
        assertEquals("delectus aut autem", result.get(0).getTitle());
    }

    @Test
    public void testGetFanCodeUsers() {
        User[] users = new User[]{
                new User() {{
                    setId(1);
                    setName("Leanne Graham");
                    setUsername("Bret");
                    setEmail("Sincere@april.biz");
                    setAddress(new Address() {{
                        setGeo(new Geo() {{
                            setLat("-37.3159");
                            setLng("81.1496");
                        }});
                    }});
                }},
                new User() {{
                    setId(2);
                    setName("Ervin Howell");
                    setUsername("Antonette");
                    setEmail("Shanna@melissa.tv");
                    setAddress(new Address() {{
                        setGeo(new Geo() {{
                            setLat("-43.9509");
                            setLng("-34.4618");
                        }});
                    }});
                }}
        };
        ResponseEntity<User[]> responseEntity = new ResponseEntity<>(users, HttpStatus.OK);
        when(restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users", User[].class)).thenReturn(responseEntity);

        List<User> result = apiService.getFanCodeUsers();
        assertEquals(1, result.size());
        assertEquals("Leanne Graham", result.get(0).getName());
    }

    @Test
    public void testIsUserCompletingMoreThanHalfTasks() {
        Todo[] todos = new Todo[]{
                new Todo() {{
                    setUserId(1);
                    setId(1);
                    setTitle("delectus aut autem");
                    setCompleted(false);
                }},
                new Todo() {{
                    setUserId(1);
                    setId(2);
                    setTitle("quis ut nam facilis et officia qui");
                    setCompleted(true);
                }},
                new Todo() {{
                    setUserId(1);
                    setId(3);
                    setTitle("fugiat veniam minus");
                    setCompleted(true);
                }}
        };
        ResponseEntity<Todo[]> responseEntity = new ResponseEntity<>(todos, HttpStatus.OK);
        when(restTemplate.getForEntity("https://jsonplaceholder.typicode.com/todos", Todo[].class)).thenReturn(responseEntity);

        boolean result = apiService.isUserCompletingMoreThanHalfTasks(1);
        assertEquals(true, result);
    }
}

