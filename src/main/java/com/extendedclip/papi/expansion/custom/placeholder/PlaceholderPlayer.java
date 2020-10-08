/*
 *
 * CustomExpansion-Expansion
 * Copyright (C) 2019 Ryan McCarthy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */
package com.extendedclip.papi.expansion.custom.placeholder;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PlaceholderPlayer {

    private static final Map<String, Placeholder> PLACEHOLDERS = new HashMap<>();

    private final UUID playerUUID;
    private String name;

    public PlaceholderPlayer(UUID uuid) {
        this.playerUUID = uuid;
    }

    public PlaceholderPlayer(UUID uuid, String name) {
        this.playerUUID = uuid;
        this.name = name;
    }

    public UUID getUUID() {
        return playerUUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(playerUUID);
    }

    public Map<String, Placeholder> getPlaceholders() {
        return PLACEHOLDERS;
    }

    public void setPlaceholders(Map<String, Placeholder> placeholders) {
        PLACEHOLDERS.clear();
        PLACEHOLDERS.putAll(placeholders);
    }

    public Placeholder getPlaceholder(String id) {
        return Optional.ofNullable(PLACEHOLDERS.get(id)).orElse(null);
    }

    public void setPlaceholder(String s, Placeholder p) {
        PLACEHOLDERS.put(s, p);
    }

    public boolean containsValidPlaceholder(String s) {
        return PLACEHOLDERS.containsKey(s) && PLACEHOLDERS.get(s) != null;
    }

    public boolean removePlaceholder(String s) {
        return PLACEHOLDERS.remove(s) != null;
    }
}
