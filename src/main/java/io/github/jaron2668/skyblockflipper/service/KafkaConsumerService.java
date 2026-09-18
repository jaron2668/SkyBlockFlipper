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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.jaron2668.skyblocksharedmodels.AuctionActive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaConsumerService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final String TOPIC_NEW = "updater-newauction";
    private final String TOPIC_ENDED = "updater-endedauction";

    @Autowired
    private AuctionProcessorService auctionProcessor;


    @KafkaListener(topics = TOPIC_NEW, groupId = "flipper-group")
    public void listenNewActiveAuction(String message) {
        AuctionActive auction;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            auction = mapper.readValue(message, AuctionActive.class);
        } catch (JsonProcessingException e) {
            LOG.error("Couldn't read AuctionActive object from kafka event with topic {}",TOPIC_NEW,e);
            return;
        }
        if (auction == null) {
            LOG.error("Deserialized active auction is null. This should not happen.");
            return;
        }
        auctionProcessor.processNewAuctions(auction);
    }

    @KafkaListener(topics = TOPIC_ENDED, groupId = "flipper-group")
    public void listenNewEndedAuction(String message) {
        UUID uuid;
        try {
            uuid = UUID.fromString(message);
        } catch (IllegalArgumentException e) {
            LOG.error("Couldn't read UUID from kafka event with topic {}",TOPIC_ENDED,e);
            return;
        }
        auctionProcessor.processEndedAuctions(uuid);
    }

}
