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
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SetCommand implements Cmd {

  @Override
  public String getUsage() {
    return "/cpe set <server/player> <id> <value> (player)";
  }

  @Override
  public boolean execute(CustomExpansion ex, CommandSender s, String[] args) {
    if (args.length < 3) {
      Utils.msg(s, getUsage());
      return true;
    }

    if (args[0].equalsIgnoreCase("player")){
      // work in progress
    }


    if (args[0].equalsIgnoreCase("server")) {

      String id = args[1];

      Placeholder p = ex.getPlaceholderHandler().getServerPlaceholders().get(id);

      String val = args[2];

      Object obj = Utils.getObject(p.getClassType(), val);

      if (obj == null) {
        Utils.msg(s, "The value specified is not supported for this placeholder!");
        return true;
      }

      p.setValue(obj);

      ex.getServerPlaceholderStorage().save(p.getKey(), p);

      Utils.msg(s, "Placeholder: " + p.getKey() + " set to: " + p.getValue());
      return true;
    }
    return true;
  }
  }