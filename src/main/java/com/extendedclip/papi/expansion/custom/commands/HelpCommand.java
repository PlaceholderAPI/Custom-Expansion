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
package com.extendedclip.papi.expansion.custom.commands;


import com.extendedclip.papi.expansion.custom.CustomExpansion;
import com.extendedclip.papi.expansion.custom.util.Utils;
import org.bukkit.command.CommandSender;

public class HelpCommand implements Cmd {

  @Override
  public boolean execute(CustomExpansion ex, CommandSender s, String[] args) {

    // temp hardcoded for now to test
    Utils.msg(s, "&aCustomExpansion Help",
        "/cpe create server <identifier> <type> <value>",
        "/cpe delete server <identifier>",
        "/cpe list server",
        "/cpe set server <identifier> <value>",
        "/cpe add server <identifier> <value>",
        "/cpe subtract server <identifier> <value>",
        "/cpe multiply server <identifier> <value>",
        "/cpe divide server <identifier> <value>",
        "/cpe create player <identifier> <type> <value> <player>",
        "/cpe delete player <identifier> <player>",
        "/cpe list player <player>",
        "/cpe set player <identifier> <value> <player>",
        "/cpe add player <identifier> <value> <player>",
        "/cpe subtract player <identifier> <value> <player>",
        "/cpe multiply player <identifier> <value> <player>",
        "/cpe divide player <identifier> <value> <player>");


    return false;
  }
}
