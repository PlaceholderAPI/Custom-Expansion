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
import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CustomExpansionCommands extends Command {

  private CustomExpansion expansion;

  public CustomExpansionCommands(CustomExpansion instance) {
    super("cpe",
        "manage custom placeholders",
        "/cpe <player/server> <add/subtract/set/remove> <identifier> <value> (player)",
        Lists.newArrayList("customplaceholderexpansion", "customplaceholders"));
    expansion = instance;
  }

  @Override
  public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    // to do
    return true;
  }
}
