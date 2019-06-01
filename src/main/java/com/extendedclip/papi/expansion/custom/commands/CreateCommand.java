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
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.rmi.CORBA.Util;
import java.util.Arrays;

public class CreateCommand implements Cmd {

  @Override
  public String getUsage() {
    return "/cpe create <server/player> <id> <type> <value> (player)";
  }

  @Override
  public boolean execute(CustomExpansion ex, CommandSender s, String[] args) {
    if (args.length < 4) {
      Utils.msg(s, getUsage());
      return true;
    }

    if (args[0].equalsIgnoreCase("player")) {

      if (args.length < 5) {
        Utils.msg(s, getUsage());
        return true;
      }

      String player = args[args.length-1];

      Player p = Bukkit.getPlayer(player);

      if (p == null) {
        Utils.msg(s, player + " is not online!");
        return true;
      }

      PlaceholderPlayer pp = ex.getPlaceholderHandler().getPlayer(p);

      boolean newUser = false;
      if (pp == null) {
        pp = new PlaceholderPlayer(p.getUniqueId(), p.getName());
        newUser = true;
      }

      String id = args[1];

      if (pp.getPlaceholder(id) != null) {
        Utils.msg(s, player + " already has a value set for placeholder: " + id);
        return true;
      }

      Class<?> type = Utils.getSupportedClassType(args[2]);

      if (type == null) {
        // the type is invalid
        Utils.msg(s, "Type: " + args[2] + " is not a valid type!");
        return true;
      }

      String val = StringUtils.join(Arrays.copyOfRange(args, 3, args.length-1), " ");

      Object obj = Utils.getObject(type, val);

      if (obj == null) {
        // object isn't the correct type
        Utils.msg(s, "Value: " + val + " is not the correct type for type specified: " + type.getSimpleName());
        return true;
      }

      Placeholder placeholder = new Placeholder(id, type, obj);
      pp.setPlaceholder(id, placeholder);
      if (newUser) {
        ex.getPlaceholderHandler().getPlayerPlaceholders().add(pp);
      }
      ex.getPlayerPlaceholderStorage().add(pp, placeholder);

      // success
      Utils.msg(s, "Placeholder: " + id + " for player: " + p.getName() + " successfully created!");
      return true;
    }

    if (args[0].equalsIgnoreCase("server")) {

      String id = args[1];

      if (ex.getPlaceholderHandler().getServerPlaceholders().containsKey(id)) {
        // placeholder already exists
        Utils.msg(s, "placeholder identifier: " + id + " already exists!");
        return true;
      }

      Class<?> type = Utils.getSupportedClassType(args[2]);

      if (type == null) {
        // the type is invalid
        Utils.msg(s, "Type: " + args[2] + " is not a valid type!");
        return true;
      }

      String val = StringUtils.join(Arrays.copyOfRange(args, 3, args.length), " ");

      Object obj = Utils.getObject(type, val);

      if (obj == null) {
        // object isn't the correct type
        Utils.msg(s, "Value: " + val + " is not the correct type for type specified: " + type.getSimpleName());
        return true;
      }

      Placeholder p = new Placeholder(id, type, obj);
      ex.getPlaceholderHandler().getServerPlaceholders().put(id, p);
      ex.getServerPlaceholderStorage().save(id, p);

      // success
      Utils.msg(s, "Placeholder: " + id + " successfully created!");
      return true;
    }

    return false;
  }
}
