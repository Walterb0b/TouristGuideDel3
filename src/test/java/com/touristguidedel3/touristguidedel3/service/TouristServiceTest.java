package com.touristguidedel3.touristguidedel3.service;

import com.touristguidedel3.touristguidedel3.model.Cities;
import com.touristguidedel3.touristguidedel3.model.TouristAttraction;
import com.touristguidedel3.touristguidedel3.repository.TouristRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TouristServiceTest {

    @Mock
    private TouristRepository repository;

    @InjectMocks
    private TouristService service;

    @Test
    void getAllAttraction() {
        List<TouristAttraction> mockList = List.of(
                new TouristAttraction("Test", "Desc", Cities.COPENHAGEN, List.of(), 0.0)
        );
        when(repository.getAllAttractions()).thenReturn(mockList);
        List<TouristAttraction> result = service.getAllAttractions();

        assertEquals(1, result.size());
        verify(repository).getAllAttractions();

    }

    @Test
    void getAttractionByName() {
        TouristAttraction attraction = new TouristAttraction(
                "Tivoli", "Forlystelsespark", Cities.COPENHAGEN, List.of(), 100.0
        );
        when(repository.getAttractionByName("Tivoli")).thenReturn(attraction);
        TouristAttraction result = service.getAttractionByName("Tivoli");

        assertEquals("Tivoli", result.getName());
        verify(repository).getAttractionByName("Tivoli");
    }
}