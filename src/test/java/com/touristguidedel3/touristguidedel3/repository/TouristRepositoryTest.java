package com.touristguidedel3.touristguidedel3.repository;


import com.touristguidedel3.touristguidedel3.model.Cities;
import com.touristguidedel3.touristguidedel3.model.TouristAttraction;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class TouristRepositoryTest {

    @Autowired
    private TouristRepository repo;

    @Test
    void readAll() {
        List<TouristAttraction> all = repo.getAllAttractions();

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(2);
        assertThat(all.get(0).getName()).isEqualTo("Tivoli");
        assertThat(all.get(1).getName()).isEqualTo("Rundetaarn");
    }


    @Test
    void insertAndReadBack() {
        repo.addAttraction(new TouristAttraction("Rumskib", "Forlystelsespark", Cities.COPENHAGEN, new ArrayList<>(), 150.0));
        var tivoli = repo.getAttractionById(3L);
        assertThat(tivoli).isNotNull();
        assertThat(tivoli.getName()).isEqualTo("Rumskib");
    }
}
