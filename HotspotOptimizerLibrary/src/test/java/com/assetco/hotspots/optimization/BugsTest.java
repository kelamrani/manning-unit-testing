package com.assetco.hotspots.optimization;

import com.assetco.search.results.Asset;
import com.assetco.search.results.AssetVendor;
import com.assetco.search.results.AssetVendorRelationshipLevel;
import com.assetco.search.results.HotspotKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.assetco.search.results.AssetVendorRelationshipLevel.Partner;
import static com.assetco.search.results.HotspotKey.Showcase;
import static org.junit.jupiter.api.Assertions.*;

class BugsTest {

    @Test
    void precedingPartnerWithLongTrailingAssetsDoesNotWin() {
        final var partnerVendor = makeVendor(Partner);
        final int maximumShowCaseItems = 5;
        var missing = givenAssetInResultsWithVendor(partnerVendor);
        var disruptingAsset = givenAssetInResultsWithVendor(makeVendor(Partner));

        var expected = givenAssetInResultsWithVendor( maximumShowCaseItems - 1, partnerVendor );

        whenOptimize();

        thenHotspotDoesNotHave(Showcase, missing);
        thenHotspotHasExactly(Showcase, expected);

        
    }

    private void thenHotspotHasExactly(HotspotKey key, ArrayList<Asset> assetArrayList) {
    }

    private void thenHotspotDoesNotHave(HotspotKey key, Asset asset) {
    }

    private void whenOptimize() {
    }

    private ArrayList<Asset> givenAssetInResultsWithVendor(int i, AssetVendor partnerVendor) {
        var result = new ArrayList<Asset>();

        for (int j = 0; j < i; j++) {
            result.add(givenAssetInResultsWithVendor(partnerVendor));
        }
        return result;
    }

    private Asset givenAssetInResultsWithVendor(AssetVendor vendor) {     return null;   }

    private AssetVendor makeVendor(AssetVendorRelationshipLevel relationshipLevel) {       return null;   }


}