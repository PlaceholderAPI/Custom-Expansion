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
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class DeleteCommand implements Cmd {

  @Override
  public String getUsage() {
    return "/cpe delete <server/player> <key> (player)";
  }

  @Override
  public boolean execute(CustomExpansion ex, CommandSender s, String[] args) {
    if (args.length < 2) {
      Utils.msg(s, getUsage());
      return true;
    }

    if (args[0].equalsIgnoreCase("server")) {

      String id = args[1];

      if (!ex.getPlaceholderHandler().getServerPlaceholders().containsKey(id)) {
        // placeholder already exists
        Utils.msg(s, "placeholder identifier: " + id + " does not exist!");
        return true;
      }

      Placeholder p = ex.getPlaceholderHandler().getServerPlaceholders().get(id);
      ex.getPlaceholderHandler().getServerPlaceholders().remove(id);
      ex.getServerPlaceholderStorage().save(id, null);
      Utils.msg(s, "Custom placeholder: " + id + " successfully deleted.");
    }

    return false;
  }
}
