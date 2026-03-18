package com.touristguidedel3.touristguidedel3.controller;

import com.touristguidedel3.touristguidedel3.model.Cities;
import com.touristguidedel3.touristguidedel3.model.Tags;
import com.touristguidedel3.touristguidedel3.model.TouristAttraction;
import com.touristguidedel3.touristguidedel3.service.TouristService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
class TouristControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TouristService touristService;

    @Test
    void getAllAttractions() throws Exception {
        List<TouristAttraction> attractions = new ArrayList<>();
        attractions.add(new TouristAttraction("Legoland", "Forlystelsespark", Cities.VEJLE, List.of(Tags.ENTERTAINMENT, Tags.KID_FRIENDLY), 299.0));

        when(touristService.getAllAttractions()).thenReturn(attractions);

        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractionsList"))
                .andExpect(model().attributeExists("attractions"))
                .andExpect(model().attribute("attractions", attractions));
    }

    @Test
    void saveAttraction() throws Exception{
        TouristAttraction touristAttraction = new TouristAttraction("Legoland", "Forlystelsespark", Cities.VEJLE, List.of(Tags.ENTERTAINMENT, Tags.KID_FRIENDLY), 299.0);

        mockMvc.perform(post("/attractions/save")
                .param("name", touristAttraction.getName())
                .param("description", touristAttraction.getDescription())
                .param("city", touristAttraction.getCity().toString())
                .param("tags", touristAttraction.getTags().get(0).toString())
                .param("tags", touristAttraction.getTags().get(1).toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));

        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);
        verify(touristService).addAttraction(captor.capture());

        TouristAttraction captured = captor.getValue();
        assertEquals("Legoland", captured.getName());
        assertEquals("Forlystelsespark", captured.getDescription());
        assertEquals(Cities.VEJLE, captured.getCity());
        assertEquals(List.of(Tags.ENTERTAINMENT, Tags.KID_FRIENDLY), captured.getTags());

    }
}