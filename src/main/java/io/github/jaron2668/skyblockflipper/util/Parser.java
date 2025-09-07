package io.github.jaron2668.skyblockflipper.util;

import io.github.jaron2668.skyblocksharedmodels.AuctionActive;
import io.github.jaron2668.skyblocksharedmodels.Flip;

public class Parser {

    public static Flip parseFlip(AuctionActive flipAuction, long estimatedProfit) {
        Flip flip = new Flip();
        flip.setAuctionUuid(flipAuction.getUuid());
        flip.setEstimatedProfit(estimatedProfit);
        flip.setPrice(flipAuction.getPrice());
        flip.setUpSince(flipAuction.getStartTime());
        flip.setItemDisplayName(flipAuction.getItem().getDisplayName());
        return flip;
    }

}
