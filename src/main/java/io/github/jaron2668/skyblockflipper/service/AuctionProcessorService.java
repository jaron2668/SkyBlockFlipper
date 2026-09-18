/*
 * Copyright (c) 2026 jaron2668
 *
 * This file is part of https://github.com/jaron2668/SkyBlockFlipper
 * and subject to the terms of the GNU General Public License, version 3.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * SPDX-License-Identifier: GPL-3.0-only
 *
 */
package io.github.jaron2668.skyblockflipper.service;

import io.github.jaron2668.skyblockflipper.Config;
import io.github.jaron2668.skyblockflipper.util.Parser;
import io.github.jaron2668.skyblocksharedmodels.AuctionActive;
import io.github.jaron2668.skyblocksharedmodels.Flip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AuctionProcessorService {

    private static final Logger LOG = LoggerFactory.getLogger(AuctionProcessorService.class);

    @Autowired
    private FlipperEngineService flipperEngine;
    @Autowired
    private KafkaPublisherService kafkaPublisher;

    private final List<UUID> activeFlips = new ArrayList<>();

    public void processNewAuctions(AuctionActive auction) {
        long estimatedProfit = flipperEngine.estimateProfit(auction);
        float estimatedProfitPercentage = ((float)estimatedProfit / auction.getPrice());
        if (estimatedProfit < Config.MIN_PROFIT || estimatedProfitPercentage < Config.MIN_PROFIT_PERCENTAGE)
            return;
        Flip flip = Parser.parseFlip(auction, estimatedProfit);
        if (flip == null) {
            LOG.error("Parser#parseFlip returned null.");
            return;
        }
        kafkaPublisher.publishNewFlip(flip);
        activeFlips.add(flip.getAuctionUuid());
    }

    public void processEndedAuctions(UUID auctionUuid) {
        if (!activeFlips.contains(auctionUuid))
            return;
        kafkaPublisher.publishEndedFlip(auctionUuid);
        activeFlips.remove(auctionUuid);
    }

}
