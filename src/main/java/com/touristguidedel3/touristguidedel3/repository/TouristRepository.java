package com.touristguidedel3.touristguidedel3.repository;

import com.touristguidedel3.touristguidedel3.model.Tags;
import java.util.ArrayList;
import com.touristguidedel3.touristguidedel3.model.Cities;
import com.touristguidedel3.touristguidedel3.model.TouristAttraction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TouristRepository {

    private final JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TouristAttraction> getAllAttractions() {
        String sql = "SELECT * FROM tourist_attraction";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            TouristAttraction attraction = new TouristAttraction();

            attraction.setName(rs.getString("name"));
            attraction.setDescription(rs.getString("description"));
            attraction.setCity(Cities.valueOf(rs.getString("city")));
            attraction.setPrice(rs.getDouble("price"));
            return attraction;
        });
    }

    public void addAttraction(TouristAttraction attraction) {
        String sql = "INSERT INTO tourist_attraction (name, description, city, price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                attraction.getName(),
                attraction.getDescription(),
                attraction.getCity().name(),
                attraction.getPrice());

        Integer attractionId = jdbcTemplate.queryForObject(
                "SELECT id FROM tourist_attraction WHERE name = ?",
                Integer.class, attraction.getName()
        );
        if (attraction.getTags() != null) {
            for (Tags tag : attraction.getTags()) {
                jdbcTemplate.update(
                        "INSERT INTO attraction_tag (attraction_id, tag_id) VALUES (?, ?)",
                        attractionId, getTagId(tag)
                );
            }
        }
    }

    public void deleteAttraction(String name) {
        String sql = "DELETE FROM tourist_attraction WHERE name = ?";
        jdbcTemplate.update(sql, name);
    }

    public void updateAttraction(TouristAttraction attraction) {
        String sql = "UPDATE tourist_attraction SET description = ?, city = ?, price = ? WHERE name = ?";
        jdbcTemplate.update(sql,
                attraction.getDescription(),
                attraction.getCity().name(),
                attraction.getPrice(),
                attraction.getName());

        Integer attractionId = jdbcTemplate.queryForObject(
                "SELECT id FROM tourist_attraction WHERE name = ?",
                Integer.class, attraction.getName()
        );
        jdbcTemplate.update("DELETE FROM attraction_tag WHERE attraction_id = ?", attractionId);
        if (attraction.getTags() != null) {
            for (Tags tag : attraction.getTags()) {
                jdbcTemplate.update(
                        "INSERT INTO attraction_tag (attraction_id, tag_id) VALUES (?, ?)",
                        attractionId,
                        getTagId(tag)
                );
            }
        }

    }

    public TouristAttraction getAttractionByName(String name) {
        String sql = "SELECT * FROM tourist_attraction WHERE LOWER(name) = LOWER(?)";

        TouristAttraction attraction = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            TouristAttraction a = new TouristAttraction();
            a.setName(rs.getString("name"));
            a.setDescription(rs.getString("description"));
            a.setCity(Cities.valueOf(rs.getString("city")));
            a.setPrice(rs.getDouble("price"));
            return a;
        }, name);

        // Hent tags
        List<Tags> tags = getTagsForAttraction(name);
        attraction.setTags(tags);

        return attraction;
    }

    public List<Tags> getTagsForAttraction(String attractionName) {
        String sql = """
        SELECT t.name
        FROM tag t
        JOIN attraction_tag at ON t.id = at.tag_id
        JOIN tourist_attraction ta ON ta.id = at.attraction_id
        WHERE ta.name = ?
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> Tags.valueOf(rs.getString("name")), attractionName);
    }

    private Integer getTagId(Tags tag) {
        return jdbcTemplate.queryForObject(
                "SELECT id FROM tag WHERE name = ?",
                Integer.class, tag.name()
        );
    }
}