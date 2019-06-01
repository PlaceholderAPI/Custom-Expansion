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

import com.extendedclip.papi.expansion.custom.CustomExpansion;
import org.bukkit.OfflinePlayer;

import java.util.*;

public final class PlaceholderHandler {

  private CustomExpansion ex;

  private final Map<String, Placeholder> SERVER = new HashMap<>();
  private final Set<PlaceholderPlayer> PLAYER = new HashSet<>();

  public PlaceholderHandler(CustomExpansion ex) {
    this.ex = ex;
  }

  public void clear() {
    SERVER.clear();
    PLAYER.clear();
  }

  public PlaceholderPlayer getPlayer(OfflinePlayer pl) {
    for (PlaceholderPlayer playa : PLAYER) {
      if (pl.getUniqueId().toString().equals(playa.getUUID().toString())) {
        return playa;
      }
    }
    return null;
  }

  public Map<String, Placeholder> getServerPlaceholders() {
    return SERVER;
  }

  public Set<PlaceholderPlayer> getPlayerPlaceholders() {
    return PLAYER;
  }
}
