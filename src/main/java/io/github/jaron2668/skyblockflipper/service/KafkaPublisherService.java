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
package io.github.jaron2668.skyblockflipper.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.jaron2668.skyblocksharedmodels.Flip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaPublisherService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaPublisherService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final String TOPIC_NEW = "flipper-newflip";
    private final String TOPIC_ENDED = "flipper-endedflip";

    public KafkaPublisherService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishNewFlip(Flip flip) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String json = mapper.writeValueAsString(flip);
            kafkaTemplate.send(TOPIC_NEW, flip.getAuctionUuid().toString(), json);
            LOG.info("Published new flip");
        } catch (Exception e) {
            LOG.error("Couldn't publish kafka event with topic {}.", TOPIC_NEW, e);
        }
    }

    public void publishEndedFlip(UUID auctionUuid) {
        try {
            kafkaTemplate.send(TOPIC_ENDED, auctionUuid.toString());
        } catch (Exception e) {
            LOG.error("Couldn't publish kafka event with topic {}.", TOPIC_ENDED, e);
        }
    }

}
