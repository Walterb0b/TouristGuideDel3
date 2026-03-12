package com.touristguide.touristguidedel3.repository;

import com.touristguide.touristguidedel3.model.Tag;
import com.touristguide.touristguidedel3.model.TouristAttraction;
import com.touristguide.touristguidedel3.model.TouristAttractionRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    private final List<TouristAttraction> attractions = new ArrayList<>();

    private final JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //======
    //Create
    //======
    public void addAttraction(TouristAttraction attraction) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO tourist_attraction (name, description, city, price) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, attraction.getName());
            ps.setString(2, attraction.getDescription());
            ps.setString(3, attraction.getCity().name().trim());
            ps.setBigDecimal(4, attraction.getPrice());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        String insertTagSql = "INSERT INTO tourist_attraction_tag (attraction_id, tag_id) VALUES (?, ?)";
        for (Tag tag : attraction.getTags()) {
            // Tjek om tag findes
            List<Long> tagIds = jdbcTemplate.query(
                    "SELECT id FROM tag WHERE name = ?",
                    (rs, rowNum) -> rs.getLong("id"),
                    tag.name()
            );

            Long tagId;
            if (tagIds.isEmpty()) {
                // Opret tag hvis det ikke findes
                jdbcTemplate.update("INSERT INTO tag (name) VALUES (?)", tag.name());
                tagId = jdbcTemplate.queryForObject(
                        "SELECT id FROM tag WHERE name = ?",
                        Long.class,
                        tag.name()
                );
            } else {
                tagId = tagIds.get(0);
            }

            jdbcTemplate.update(insertTagSql, id, tagId);
        }
    }

    //=====
    //Read
    //=====

    public List<TouristAttraction> getAllAttractions() {

        String sqlAttractions = "SELECT * FROM tourist_attraction ORDER BY id";
        List<TouristAttraction> attractions = jdbcTemplate.query(sqlAttractions, new TouristAttractionRowMapper());

        // Hent tags for hver attraktion via join-tabel
        String sqlTags = """
        SELECT t.name
        FROM tag t
        JOIN tourist_attraction_tag tat ON t.id = tat.tag_id
        WHERE tat.attraction_id = ?
    """;

        for (TouristAttraction ta : attractions) {
            List<Tag> tags = jdbcTemplate.query(sqlTags,
                    (rs, rowNum) -> Tag.valueOf(rs.getString("name")),
                    ta.getId());
            ta.setTags(tags);
        }

        return attractions;
    }


    public TouristAttraction getAttractionById(Long id) {
        String sql = "SELECT * FROM tourist_attraction WHERE id = ?";
        TouristAttraction attraction = jdbcTemplate.queryForObject(sql, new TouristAttractionRowMapper(), id);

        attraction.setTags(findTagsByAttractionId(id));
        return attraction;
    }

    public List<Tag> findTagsByAttractionId(Long id) {
        String sql = """
                SELECT t.name
                FROM tag t
                JOIN tourist_attraction_tag tat ON t.id = tat.tag_id
                WHERE tat.attraction_id = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> Tag.valueOf(rs.getString("name").trim().toUpperCase()), id);
    }


    //======
    //Update
    //======

    public void updateAttraction(TouristAttraction updatedAttraction) {
        String sql = "UPDATE tourist_attraction SET name = ?, description = ?, city = ?, price = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                updatedAttraction.getName(),
                updatedAttraction.getDescription(),
                updatedAttraction.getCity().name(),
                updatedAttraction.getPrice(),
                updatedAttraction.getId());

        jdbcTemplate.update("DELETE FROM tourist_attraction_tag WHERE attraction_id = ?", updatedAttraction.getId());

        String insertTag = "INSERT INTO tourist_attraction_tag (attraction_id, tag_id) VALUES (?, ?)";
        for (Tag tag : updatedAttraction.getTags()) {
            Long tagId = jdbcTemplate.queryForObject(
                    "SELECT id FROM tag WHERE name = ?",
                    Long.class,
                    tag.name()
            );
            jdbcTemplate.update(insertTag, updatedAttraction.getId(), tagId);
        }
    }

    //======
    //Delete
    //======

    public void deleteAttraction(Long id) {
        // Slet join-tabel entries først
        jdbcTemplate.update("DELETE FROM tourist_attraction_tag WHERE attraction_id = ?", id);
        // Slet attraction
        jdbcTemplate.update("DELETE FROM tourist_attraction WHERE id = ?", id);
    }

}
