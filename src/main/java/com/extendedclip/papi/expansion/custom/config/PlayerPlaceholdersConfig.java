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
import com.extendedclip.papi.expansion.custom.placeholder.Placeholder;
import com.extendedclip.papi.expansion.custom.placeholder.PlaceholderPlayer;
import com.extendedclip.papi.expansion.custom.util.Utils;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.Map;
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

	public boolean deleteDefault(String identifier) {
		if (config == null) {
			return false;
		}
		FileConfiguration c = config.getConfig();
		if (c == null) {
			return false;
		}
		c.set("placeholders." + identifier, null);
		return config.save();
	}

	public boolean delete(PlaceholderPlayer player, String identifier) {
		if (config == null) {
			return false;
		}
		FileConfiguration c = config.getConfig();
		if (c == null) {
			return false;
		}
		String key = "player_data." + player.getUUID().toString();
		c.set(key + ".placeholders." + identifier, null);
		Set<String> keys = c.getConfigurationSection(key + ".placeholders").getKeys(false);
		if (keys == null || keys.isEmpty()) {
			c.set(key, null);
		}
		return config.save();
	}

	public boolean saveDefault(String id, Placeholder p) {
		if (config == null) {
			return false;
		}
		FileConfiguration c = config.getConfig();
		if (c == null) {
			return false;
		}
		if (p == null) {
			c.set("placeholders." + id, p);
		} else {
			c.set("placeholders." + p.getKey() + ".type", p.getClassType().getSimpleName());
			c.set("placeholders." + p.getKey() + ".value", p.getValue());
		}

		return config.save();
	}

	public boolean save(PlaceholderPlayer player, String id, Placeholder p) {
		if (config == null) {
			return false;
		}
		FileConfiguration c = config.getConfig();
		if (c == null) {
			return false;
		}

		if (!ex.getPlaceholderHandler().getPlayerPlaceholderDefaults().containsKey(id)) {
			return false;
		}

		String key = "player_data." + player.getUUID().toString();
		c.set(key + ".name", player.getName());

		if (p == null) {
			c.set(key + ".placeholders." + id, p);
		} else {
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
		Map<String, Placeholder> defaults = ex.getPlaceholderHandler().getPlayerPlaceholderDefaults();
		defaults.clear();

		for (String id : c.getConfigurationSection("placeholders").getKeys(false)) {
			String path = "placeholders." + id;
			Class<?> type = Utils.getSupportedClassType(c.getString(path+".type"));
			if (type == null) {
				ex.log("Player placeholder: " + id + " has an invalid type specified");
				continue;
			}
			Object value = c.get(path+".value");

			if (value == null) {
				ex.log("Player placeholder: " + id + " has an invalid value specified");
				continue;
			}

			if (!type.isAssignableFrom(value.getClass())) {
				ex.log("Player placeholder: " + id + " has a value set that does not match the specified type");
				continue;
			}
			Placeholder p = new Placeholder(id, type, value);
			defaults.put(id, p);
		}

		for (String uuid : c.getConfigurationSection("player_data").getKeys(false)) {
			if (!c.isConfigurationSection("player_data." + uuid + ".placeholders")) {
				continue;
			}

			PlaceholderPlayer pl = new PlaceholderPlayer(UUID.fromString(uuid), c.getString("player_data." + uuid + ".name"));
			for (String id : c.getConfigurationSection("player_data." + uuid + ".placeholders").getKeys(false)) {
				if (!defaults.containsKey(id)) {
					ex.log("Custom player placeholder: " + id + " does not exist as a default for player:" + uuid);
					continue;
				}
				Placeholder def = defaults.get(id);
				String path = "player_data." + uuid + ".placeholders." + id;
				Class<?> type = def.getClassType();
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
