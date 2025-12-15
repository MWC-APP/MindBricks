package ch.inf.usi.mindbricks.game;

import androidx.annotation.Nullable;

public record TileAsset(String id, String displayName, String assetPath,
                        @Nullable Integer tileIndex, TileType type, int price) {

    public boolean isTilesetTile() {
        return tileIndex != null;
    }
}
