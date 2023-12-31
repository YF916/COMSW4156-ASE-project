package com.example.demotest;


import com.example.demotest.controller.DispatchController;
import com.example.demotest.exceptions.RequestAlreadyHandledException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.example.demotest.controller.DispatchHistoryController;
import com.example.demotest.model.DispatchHistory;
import com.example.demotest.model.Responder;
import com.example.demotest.model.User;
import com.example.demotest.repository.DispatchHistoryRepository;
import com.example.demotest.repository.ResponderRepository;
import com.example.demotest.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.security.core.userdetails.UserDetails;


@ExtendWith(MockitoExtension.class)
public class DispatchControllerTest {

    @Mock
    private ResponderRepository responderRepository;

    @Mock
    private DispatchHistoryRepository dispatchHistoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDetails userDetails;


    @InjectMocks
    private DispatchController dispatchController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Write individual test methods here for each functionality
    @Test
    public void testGetAllRequests() {
        when(dispatchHistoryRepository.findAllByStatusOrderByEmergencyLevelAsc("pending")).thenReturn(new ArrayList<>());

        Iterable<DispatchHistory> result = dispatchController.getAllRequests();

        verify(dispatchHistoryRepository).findAllByStatusOrderByEmergencyLevelAsc("pending");
        assertNotNull(result);
    }

    @Test
    public void testGetRequestByRadius() {
        when(dispatchHistoryRepository.findAllByLatitudeBetweenAndLongitudeBetweenAndStatusEqualsOrderByEmergencyLevel(anyDouble(), anyDouble(), anyDouble(), anyDouble(), eq("pending")))
                .thenReturn(new ArrayList<>());

        Iterable<DispatchHistory> result = dispatchController.getRequestByRadius(1.0, 1.0, 0.5);

        verify(dispatchHistoryRepository).findAllByLatitudeBetweenAndLongitudeBetweenAndStatusEqualsOrderByEmergencyLevel(anyDouble(), anyDouble(), anyDouble(), anyDouble(), eq("pending"));
        assertNotNull(result);
    }

    @Test
    public void testDispatchResponder_Success() {
        when(userDetails.getUsername()).thenReturn("responderUsername");
        when(responderRepository.getReferenceById("responderUsername")).thenReturn(new Responder());
        DispatchHistory history = new DispatchHistory();
        history.setStatus("pending");
        when(dispatchHistoryRepository.getReferenceById(1)).thenReturn(history);

        String result = dispatchController.dispatchResponder(userDetails, 1);

        assertEquals("Accepted", result);
        verify(dispatchHistoryRepository).save(history);
    }

    @Test
    public void testDispatchResponder_AlreadyHandled() {
        when(userDetails.getUsername()).thenReturn("responderUsername");
        when(responderRepository.getReferenceById("responderUsername")).thenReturn(new Responder());
        DispatchHistory history = new DispatchHistory();
        history.setStatus("dispatched");
        when(dispatchHistoryRepository.getReferenceById(1)).thenReturn(history);

        assertThrows(RequestAlreadyHandledException.class, () -> {
            dispatchController.dispatchResponder(userDetails, 1);
        });
    }

    private DispatchHistory createHistoryWithRating(Responder responder, Double rating) {
        DispatchHistory history = new DispatchHistory();
        history.setResponder(responder);
        history.setRating(rating);
        return history;
    }

    private DispatchHistory createHistoryWithResponder(Responder responder) {
        DispatchHistory history = new DispatchHistory();
        history.setResponder(responder);
        return history;
    }

    /*@Test
    public void testGetRateRecommend_WithRatings() {
        List<DispatchHistory> histories = new ArrayList<>();
        Responder highestRatedResponder = new Responder(); // Assuming this is the correct way to instantiate a Responder
        highestRatedResponder.setName("responder2"); // Assuming a setter method for ID
        histories.add(createHistoryWithRating(new Responder(), 3.0));
        histories.add(createHistoryWithRating(highestRatedResponder, 4.0));
        when(userRepository.getReferenceById("userName")).thenReturn(new User());
        when(dispatchHistoryRepository.findByCaller(any(User.class))).thenReturn(histories);

        Responder result = dispatchController.getRateRecommend("userName");

        assertEquals("responder2", result.getName()); // Assuming getId exists
    }*/

    /*@Test
    public void testGetRateRecommend_NoRatings() {
        when(userRepository.getReferenceById("userName")).thenReturn(new User());
        when(dispatchHistoryRepository.findByCaller(any(User.class))).thenReturn(new ArrayList<>());

        Responder result = dispatchController.getRateRecommend("userName");

        assertNull(result);
    }*/

    /*@Test
    public void testGetFreqRecommend_VariousFrequencies() {
        List<DispatchHistory> histories = new ArrayList<>();
        Responder mostFrequentResponder = new Responder(); // Assuming this is the correct way to instantiate a Responder
        mostFrequentResponder.setName("responder1"); // Assuming a setter method for ID
        histories.add(createHistoryWithResponder(mostFrequentResponder));
        histories.add(createHistoryWithResponder(new Responder()));
        histories.add(createHistoryWithResponder(mostFrequentResponder));
        when(userRepository.getReferenceById("userName")).thenReturn(new User());
        when(dispatchHistoryRepository.findByCaller(any(User.class))).thenReturn(histories);

        Responder result = dispatchController.getFreqRecommend("userName");

        assertEquals("responder1", result.getName()); // Assuming getId exists
    }*/

    /*@Test
    public void testGetFreqRecommend_NoHistory() {
        when(userRepository.getReferenceById("userName")).thenReturn(new User());
        when(dispatchHistoryRepository.findByCaller(any(User.class))).thenReturn(new ArrayList<>());

        Responder result = dispatchController.getFreqRecommend("userName");

        assertNull(result);
    }*/











//    @InjectMocks
//    private DispatchController dispatchController;
//
//    @Mock
//    private ResponderRepository responderRepository;
//
//    @Captor
//    private ArgumentCaptor<Responder> responderCaptor;
//
//    @Captor
//    private ArgumentCaptor<Double> minLatCaptor;
//
//    @Captor
//    private ArgumentCaptor<Double> maxLatCaptor;
//
//    @Captor
//    private ArgumentCaptor<Double> minLonCaptor;
//
//    @Captor
//    private ArgumentCaptor<Double> maxLonCaptor;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }

    /*@Test
    public void testAddNewResponder_Success() {
        Responder responder = new Responder();
        responder.setName("John Smith");
        responder.setPhone("1234567890");
        responder.setLatitude(40.7128);
        responder.setLongitude(-74.0060);
        responder.setStatus("available");

        when(responderRepository.save(any(Responder.class))).thenReturn(responder);

        dispatchController.addNewResponder(responder);

        // Capture the responder passed to the save method
        verify(responderRepository).save(responderCaptor.capture());
        Responder savedResponder = responderCaptor.getValue();

        // Assert that the properties match
        assertEquals("John Smith", savedResponder.getName());
        assertEquals("1234567890", savedResponder.getPhone());
        assertEquals(40.7128, savedResponder.getLatitude());
        assertEquals(-74.0060, savedResponder.getLongitude());
        assertEquals("available", savedResponder.getStatus());
    }

    @Test
    public void testAddNewResponder_Failure() {
        Responder responder = new Responder();
        responder.setName("Jane Doe");
        responder.setPhone("9876543210");

        when(responderRepository.save(responder)).thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class, () -> dispatchController.addNewResponder(responder));
        verify(responderRepository, times(1)).save(responder);
    }

//    @Test
//    public void testGetAllResponders() {
//        Responder responder1 = new Responder();
//        responder1.setName("John Smith");
//        Responder responder2 = new Responder();
//        responder2.setName("Jane Doe");
//
//        List<Responder> mockResponders = Arrays.asList(responder1, responder2);
//
//        when(responderRepository.findAll()).thenReturn(mockResponders);
//
//        Iterable<Responder> responders = dispatchController.getAllResponders();
//        assertNotNull(responders);
//        assertEquals(2, ((List<Responder>) responders).size());
//        verify(responderRepository, times(1)).findAll();
//    }
    @Test
    public void testGetRespondersByRadius() {
        Responder responder = new Responder();
        List<Responder> mockResponders = Arrays.asList(responder);
        double latitude = 40.0;
        double longitude = 60.0;
        double radius = 5;

        when(responderRepository.findByLatitudeBetweenAndLongitudeBetween(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(mockResponders);

        Iterable<Responder> responders = dispatchController.getRespondersBYRadius(latitude, longitude, radius);

        assertEquals(mockResponders, responders, "The returned responders do not match the expected ones.");

        // Verify and capture that the repository method was called with the correct parameters
        verify(responderRepository).findByLatitudeBetweenAndLongitudeBetween(minLatCaptor.capture(), maxLatCaptor.capture(),
                minLonCaptor.capture(), maxLonCaptor.capture());

        assertEquals(latitude - radius, minLatCaptor.getValue(), "Incorrect minimum latitude.");
        assertEquals(latitude + radius, maxLatCaptor.getValue(), "Incorrect maximum latitude.");
        assertEquals(longitude - radius, minLonCaptor.getValue(), "Incorrect minimum longitude.");
        assertEquals(longitude + radius, maxLonCaptor.getValue(), "Incorrect maximum longitude.");
    }*/


}

