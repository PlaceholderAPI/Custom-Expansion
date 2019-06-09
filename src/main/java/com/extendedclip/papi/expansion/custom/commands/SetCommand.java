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
    return "/cpe set <server/player/pdefault> <id> <value> (player)";
  }

  @Override
  public boolean execute(CustomExpansion ex, CommandSender s, String[] args) {
    if (args.length < 3) {
      Utils.msg(s, getUsage());
      return true;
    }

    if (args[0].equalsIgnoreCase("pdefault")) {
      String id = args[1];
      Placeholder def = ex.getPlaceholderHandler().getPlayerPlaceholderDefaults().get(id);
      if (def == null) {
        Utils.msg(s, "The placeholder specified does not exist!");
        return true;
      }

      String val = StringUtils.join(Arrays.copyOfRange(args, 3, args.length), " ");
      Object obj = Utils.getObject(def.getClassType(), val);
      if (obj == null) {
        Utils.msg(s, "The value specified is not supported for this placeholder!");
        return true;
      }
      def.setValue(obj);
      ex.getPlayerPlaceholderStorage().saveDefault(def.getKey(), def);
      Utils.msg(s, "Player placeholder default: " + def.getKey() + " set to: " + def.getValue());
      return true;
    }

    if (args[0].equalsIgnoreCase("player")) {
      String id = args[1];
      Placeholder def = ex.getPlaceholderHandler().getPlayerPlaceholderDefaults().get(id);
      if (def == null) {
        Utils.msg(s, "The placeholder specified does not exist!");
        return true;
      }
      if (args.length < 4) {
        Utils.msg(s, "Incorrect usage! /cpe set player <id> <value> <player>");
        return true;
      }
      String val = StringUtils.join(Arrays.copyOfRange(args, 3, args.length - 2), " ");
      Object obj = Utils.getObject(def.getClassType(), val);
      if (obj == null) {
        Utils.msg(s, "The value specified is not supported for this placeholder!");
        return true;
      }

      Player p = Bukkit.getPlayer(args[args.length - 1]);

      if (p == null) {
        Utils.msg(s, args[args.length - 1] + " is not online!");
        return true;
      }

      PlaceholderPlayer pl = ex.getPlaceholderHandler().getPlayer(p);

      if (pl == null) {
        pl = new PlaceholderPlayer(p.getUniqueId(), p.getName());
        ex.getPlaceholderHandler().getPlayerPlaceholders().add(pl);
      }
      Placeholder placeholder = new Placeholder(id, def.getClassType(), val);
      pl.setPlaceholder(id, placeholder);
      ex.getPlayerPlaceholderStorage().save(pl, id, placeholder);
      Utils.msg(s, "Player placeholder: " + id + " set to value: " + val + " for player: " + p.getName());
      return true;
    }

    if (args[0].equalsIgnoreCase("server")) {

      String id = args[1];
      Placeholder p = ex.getPlaceholderHandler().getServerPlaceholders().get(id);
      if (p == null) {
        Utils.msg(s, "The placeholder specified does not exist!");
        return true;
      }
      String val = StringUtils.join(Arrays.copyOfRange(args, 3, args.length - 1), " ");
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
