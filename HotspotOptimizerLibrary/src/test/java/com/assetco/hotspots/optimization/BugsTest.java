package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.assetco.search.results.AssetVendorRelationshipLevel.Partner;
import static com.assetco.search.results.HotspotKey.Showcase;

class BugsTest {

    private SearchResults searchResults;

    private SearchResultHotspotOptimizer searchResultHotspotOptimizer;

    @BeforeEach
    void setUp() {
        searchResults = new SearchResults();
        searchResultHotspotOptimizer = new SearchResultHotspotOptimizer();

    }

    @Test
    void precedingPartnerWithLongTrailingAssetsDoesNotWin() {
        final var partnerVendor = makeVendor(Partner);
        final int maximumShowCaseItems = 5;
        var missing = givenAssetInResultsWithVendor(partnerVendor);
        var disruptingAsset = givenAssetInResultsWithVendor(makeVendor(Partner));

        var expected = givenAssetInResultsWithVendor( maximumShowCaseItems - 1, partnerVendor );

        whenOptimize();

        thenHotspotDoesNotHave(Showcase, missing);
//        expected.add(expected.size()-1, missing);
        thenHotspotHasExactly(Showcase, expected);

        
    }

    private void thenHotspotHasExactly(HotspotKey key, ArrayList<Asset> assetArrayList){
        Assertions.assertFalse(searchResults.getHotspot(key).getMembers().toArray().equals(assetArrayList.toArray()));
    }

    private void thenHotspotDoesNotHave(HotspotKey key, Asset... forbiden)  {
        for (var asset : forbiden) {
            Assertions.assertTrue(searchResults.getHotspot(key).getMembers().contains(asset));
        }

    }

    private void whenOptimize(){
        searchResultHotspotOptimizer.optimize(searchResults);
    }

    private ArrayList<Asset> givenAssetInResultsWithVendor(int i, AssetVendor partnerVendor) {
        var result = new ArrayList<Asset>();

        for (int j = 0; j < i; j++) {
            result.add(givenAssetInResultsWithVendor(partnerVendor));
        }
        return result;
    }

    private Asset givenAssetInResultsWithVendor(AssetVendor vendor) {
        Asset result = getAsset(vendor);
        searchResults.addFound(result);
        return result;
    }

    private Asset getAsset(AssetVendor vendor) {
        return new Asset("anything", "anything", null, null, getPurchaseInfo(), getPurchaseInfo(), new ArrayList<>(), vendor);
    }

    private AssetPurchaseInfo getPurchaseInfo() {
        return new AssetPurchaseInfo(0, 0,
                new Money(new BigDecimal("0")),
                new Money(new BigDecimal("0")));
    }

    private AssetVendor makeVendor(AssetVendorRelationshipLevel relationshipLevel) {
        return new AssetVendor("1","1", relationshipLevel, 1);
//        return null;
    }


}