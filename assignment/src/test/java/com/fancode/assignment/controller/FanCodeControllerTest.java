package com.fancode.assignment.controller;
import com.fancode.assignment.model.User;
import com.fancode.assignment.service.ApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class FanCodeControllerTest {

    @Mock
    private ApiService apiService;

    @InjectMocks
    private FanCodeController fanCodeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckFanCodeUsers() {
        User user = new User();
        user.setId(1);
        user.setName("Leanne Graham");
        when(apiService.getFanCodeUsers()).thenReturn(Arrays.asList(user));
        when(apiService.isUserCompletingMoreThanHalfTasks(1)).thenReturn(true);

        ResponseEntity<List<String>> response = fanCodeController.checkFanCodeUsers();
        List<String> result = response.getBody();

        assertEquals(1, result.size());
        assertEquals("Leanne Graham", result.get(0));
    }
}
