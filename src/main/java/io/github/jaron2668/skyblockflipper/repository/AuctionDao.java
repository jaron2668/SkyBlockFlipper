/*
 * Copyright (c) 2026 jaron2668
 *
 * This file is part of https://github.com/jaron2668/SkyblockSharedModels
 * and subject to the terms of the GNU General Public License, version 3.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: GPL-3.0-only
 *
 */
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
