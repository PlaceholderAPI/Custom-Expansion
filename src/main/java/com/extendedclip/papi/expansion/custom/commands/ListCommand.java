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
import com.extendedclip.papi.expansion.custom.placeholder.Placeholder;
import com.extendedclip.papi.expansion.custom.util.Utils;
import org.bukkit.command.CommandSender;

public class ListCommand implements Cmd {

  @Override
  public String getUsage() {
    return "/cpe list <server/player> (player)";
  }

  @Override
  public boolean execute(CustomExpansion ex, CommandSender s, String[] args) {
    if (args.length < 1) {
      Utils.msg(s, getUsage());
      return true;
    }

    if (args[0].equalsIgnoreCase("server"))  {
      Utils.msg(s, "Custom placeholders loaded: " + ex.getPlaceholderHandler().getServerPlaceholders().size(), ex.getPlaceholderHandler().getServerPlaceholders().keySet().toString());
      return true;
    }
    return false;
  }
}