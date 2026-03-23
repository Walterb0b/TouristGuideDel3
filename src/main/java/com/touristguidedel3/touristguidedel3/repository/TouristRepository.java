package com.touristguidedel3.touristguidedel3.repository;

import com.touristguidedel3.touristguidedel3.model.Tags;
import com.touristguidedel3.touristguidedel3.model.Cities;
import com.touristguidedel3.touristguidedel3.model.TouristAttraction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TouristRepository {

    private final JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<TouristAttraction> attractionRowMapper = (rs, rowNum) -> {
        TouristAttraction attraction = new TouristAttraction();
        attraction.setId(rs.getLong("id"));
        attraction.setName(rs.getString("name"));
        attraction.setDescription(rs.getString("description"));
        attraction.setCity(Cities.valueOf(rs.getString("city")));
        attraction.setPrice(rs.getDouble("price"));
        return attraction;
    };

    public List<TouristAttraction> getAllAttractions() {
        String sql = "SELECT * FROM tourist_attraction";
        return jdbcTemplate.query(sql, attractionRowMapper);
    }

    public void addAttraction(TouristAttraction attraction) {
        String sql = "INSERT INTO tourist_attraction (name, description, city, price) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                attraction.getName(),
                attraction.getDescription(),
                attraction.getCity().name(),
                attraction.getPrice());

        Long attractionId = jdbcTemplate.queryForObject(
                "SELECT id FROM tourist_attraction WHERE name = ?",
                Long.class, attraction.getName()
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

    public void deleteAttraction(Long id) {
        String sql = "DELETE FROM tourist_attraction WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateAttraction(TouristAttraction attraction) {
        String sql = "UPDATE tourist_attraction SET description = ?, city = ?, price = ? WHERE name = ?";
        jdbcTemplate.update(sql,
                attraction.getDescription(),
                attraction.getCity().name(),
                attraction.getPrice(),
                attraction.getName());

        Long attractionId = jdbcTemplate.queryForObject(
                "SELECT id FROM tourist_attraction WHERE name = ?",
                Long.class, attraction.getName()
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

    private TouristAttraction getSingleAttraction(String sql, Object param) {
        TouristAttraction attraction = jdbcTemplate.queryForObject(sql, attractionRowMapper, param);
        attraction.setTags(getTagsForAttraction(attraction.getName()));
        return attraction;
    }

    public TouristAttraction getAttractionByName(String name) {
        return getSingleAttraction("SELECT * FROM tourist_attraction WHERE LOWER(name) = LOWER(?)", name);
    }

    public TouristAttraction getAttractionById(Long id) {
        return getSingleAttraction("SELECT * FROM tourist_attraction WHERE id = ?", id);
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