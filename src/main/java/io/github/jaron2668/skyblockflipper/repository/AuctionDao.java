package io.github.jaron2668.skyblockflipper.repository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class AuctionDao {

    private final JdbcTemplate jdbc;

    private static final Logger LOG = LoggerFactory.getLogger(AuctionDao.class);

    public AuctionDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

}
