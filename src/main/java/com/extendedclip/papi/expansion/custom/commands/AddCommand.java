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
import com.extendedclip.papi.expansion.custom.placeholder.PlaceholderPlayer;
import com.extendedclip.papi.expansion.custom.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddCommand implements Cmd {

  @Override
  public String getUsage() {
    return "/cpe add <server/player> <id> <value> (player)";
  }

  @Override
  public boolean execute(CustomExpansion ex, CommandSender s, String[] args) {
    if (args.length < 3) {
      Utils.msg(s, getUsage());
      return true;
    }

    if (args[0].equalsIgnoreCase("player")) {

    }


    if (args[0].equalsIgnoreCase("server")) {
      String id = args[1];
      Placeholder p = ex.getPlaceholderHandler().getServerPlaceholders().get(id);
      if (p == null) {
        Utils.msg(s, "Placeholder id specified doesn't exist!");
        return true;
      }
      Object obj = Utils.doMath(p.getClassType(), p.getValue(), args[2], "+");
      if (obj == null) {
        Utils.msg(s, "The value specified is not the correct type for this placeholder!");
        return true;
      }
      p.setValue(obj);
      ex.getServerPlaceholderStorage().save(p.getKey(), p);
      Utils.msg(s, "Added: " + args[2] + " to placeholder: " + id, "New value:" + obj);
      return true;
    }
    return false;
  }
}
