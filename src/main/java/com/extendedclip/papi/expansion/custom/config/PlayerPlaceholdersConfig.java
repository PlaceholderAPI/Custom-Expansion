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
package com.extendedclip.papi.expansion.custom.config;

import com.extendedclip.papi.expansion.custom.CustomExpansion;
import com.extendedclip.papi.expansion.custom.storage.PlayerStorage;
import com.extendedclip.papi.expansion.custom.storage.ServerStorage;
import com.extendedclip.papi.expansion.custom.placeholder.Placeholder;
import com.extendedclip.papi.expansion.custom.placeholder.PlaceholderPlayer;
import com.extendedclip.papi.expansion.custom.util.Utils;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.Set;
import java.util.UUID;

public class PlayerPlaceholdersConfig implements PlayerStorage {

	private CustomExpansion ex;
	private PlaceholderAPIPlugin plugin;
	private ConfigWrapper config;

	public PlayerPlaceholdersConfig(CustomExpansion ex) {
		this.ex = ex;
		plugin = ex.getPlaceholderAPI();
		config = new ConfigWrapper(ex,
				plugin.getDataFolder() + File.separator + "expansions" + File.separator + "custom",
				"player_placeholders.yml");
	}

	public void init() {
		config.load();
		config.save();
	}

	public boolean remove(PlaceholderPlayer player, String identifier) {
		if (config == null) {
			return false;
		}
		FileConfiguration c = config.getConfig();
		if (c == null) {
			return false;
		}
		String key = player.getUUID().toString();
		c.set(key + ".placeholders." + identifier, null);
		Set<String> keys = c.getConfigurationSection(key + ".placeholders").getKeys(false);
		if (keys == null || keys.isEmpty()) {
			c.set(key, null);
		}
		return config.save();
	}

	public boolean add(PlaceholderPlayer player, Placeholder p) {
		if (config == null) {
			return false;
		}
		FileConfiguration c = config.getConfig();
		if (c == null) {
			return false;
		}

		String key = player.getUUID().toString();
		c.set(key + ".name", player.getName());

		if (p == null) {
			c.set(key + ".placeholders." + p.getKey(), p);
		} else {
			c.set(key + ".placeholders." + p.getKey() + ".type", p.getClassType().getSimpleName());
			c.set(key + ".placeholders." + p.getKey() + ".value", p.getValue());
		}

		return config.save();
	}

	public int load() {
		FileConfiguration c = config.getConfig();
		Set<PlaceholderPlayer> set = ex.getPlaceholderHandler().getPlayerPlaceholders();
		if (config == null || c == null || c.getKeys(false).isEmpty()) {
			return 0;
		}

		set.clear();

		for (String uuid : c.getKeys(false)) {
			if (!c.isConfigurationSection(uuid + ".placeholders")) {
				continue;
			}

			PlaceholderPlayer pl = new PlaceholderPlayer(UUID.fromString(uuid), c.getString(uuid + ".name"));
			for (String id : c.getConfigurationSection(uuid + ".placeholders").getKeys(false)) {
				String path = uuid + ".placeholders." + id;
				Class<?> type = Utils.getSupportedClassType(c.getString(path+".type"));
				if (type == null) {
					ex.log("Custom player placeholder: " + id + " has an invalid type specified for player:" + uuid);
					continue;
				}
				Object value = c.get(path+".value");

				if (value == null) {
					ex.log("Custom player placeholder: " + id + " has an invalid value specified for player:" + uuid);
					continue;
				}

				if (!type.isAssignableFrom(value.getClass())) {
					ex.log("Custom player placeholder: " + id + " has a value set that does not match the specified type for player: " + uuid);
					continue;
				}
				Placeholder p = new Placeholder(id, type, value);
				pl.setPlaceholder(id, p);
			}
			set.add(pl);
		}

		return set.size();
	}

}
