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
package com.extendedclip.papi.expansion.custom;

import com.extendedclip.papi.expansion.custom.commands.CustomExpansionCommands;
import com.extendedclip.papi.expansion.custom.config.PlayerPlaceholdersConfig;
import com.extendedclip.papi.expansion.custom.config.ServerPlaceholdersConfig;
import com.extendedclip.papi.expansion.custom.placeholder.Placeholder;
import com.extendedclip.papi.expansion.custom.placeholder.PlaceholderHandler;
import com.extendedclip.papi.expansion.custom.placeholder.PlaceholderPlayer;
import com.extendedclip.papi.expansion.custom.storage.PlayerStorage;
import com.extendedclip.papi.expansion.custom.storage.ServerStorage;
import com.google.common.collect.Maps;
import me.clip.placeholderapi.expansion.Cacheable;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

public final class CustomExpansion extends PlaceholderExpansion implements Cacheable, Configurable {

    private final String VERSION = getClass().getPackage().getImplementationVersion();
    private CommandMap map = null;
    private Map<String, Command> known;
    private PlaceholderHandler handler;
    private ServerStorage serverPlaceholderStorage;
    private PlayerStorage playerPlaceholderStorage;
    private CustomExpansionCommands cmd;

    @Override
    public String getIdentifier() {
        return "custom";
    }

    @Override
    public String getAuthor() {
        return "clip";
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public boolean register() {
        //init handler
        handler = new PlaceholderHandler(this);
        // register command
        try {
            cmd = new CustomExpansionCommands(this);
            final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            map = (CommandMap) f.get(Bukkit.getServer());
            Field k = SimpleCommandMap.class.getDeclaredField("knownCommands");
            k.setAccessible(true);
            known = ((Map<String, Command>) k.get(map));
            map.register("customplaceholders", cmd);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        this.playerPlaceholderStorage = new PlayerPlaceholdersConfig(this);
        this.serverPlaceholderStorage = new ServerPlaceholdersConfig(this);

        this.playerPlaceholderStorage.init();
        this.serverPlaceholderStorage.init();
        // register
        return super.register();
    }

    @Override
    public void clear() {
        known.remove("cpe");
        cmd.unregister(map);
        known = null;
        map = null;
        handler.clear();
    }

    @Override
    public Map<String, Object> getDefaults() {
        Map<String, Object> defaults = Maps.newHashMap();
        defaults.put("storage_type", "yml");
        return defaults;
    }

    @Override
    public String onRequest(OfflinePlayer p, String id) {
        if (id.startsWith("server_")) {
            id = id.replace("server_", "");
            return Optional.ofNullable(handler.getServerPlaceholders().get(id).getValue().toString()).orElse(null);
        }
        if (id.startsWith("player_")) {
            id = id.replace("player_", "");
            PlaceholderPlayer pl = handler.getPlayer(p);
            if (pl == null) {
                return null;
            }
            Placeholder ph = pl.getPlaceholder(id);
            if (ph == null) {
                return null;
            }
            return Optional.ofNullable(ph.getValue().toString()).orElse(null);
        }
        return null;
    }

    public void log(String message) {
        System.out.println("[custom placeholders] " + message);
    }

    public PlaceholderHandler getPlaceholderHandler() {
        return handler;
    }

    public ServerStorage getServerPlaceholderStorage() {
        return serverPlaceholderStorage;
    }

    public PlayerStorage getPlayerPlaceholderStorage() {
        return playerPlaceholderStorage;
    }
}
