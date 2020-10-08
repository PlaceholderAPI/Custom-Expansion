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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigWrapper {

    private final String folderName, fileName;
    private final CustomExpansion expansion;
    private FileConfiguration config;
    private File configFile;

    public ConfigWrapper(final CustomExpansion ex, final String folderName, final String fileName) {
        this.expansion = ex;
        this.folderName = folderName;
        this.fileName = fileName;
    }

    public FileConfiguration getConfig() {
        if (config == null) {
            load();
        }

        return config;
    }

    public void load() {
        if (configFile == null) {
            configFile = new File(folderName, fileName);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public boolean save() {
        if (config == null || configFile == null) {
            return false;
        }

        try {
            getConfig().save(configFile);
            return true;
        } catch (final IOException ex) {
            expansion.log("[WARNING] Could not save config to " + configFile);
        }

        return false;
    }
}
