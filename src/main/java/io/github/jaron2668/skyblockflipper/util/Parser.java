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
