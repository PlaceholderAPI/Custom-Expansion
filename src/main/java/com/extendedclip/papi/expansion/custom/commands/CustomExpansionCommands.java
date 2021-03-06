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
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CustomExpansionCommands extends Command {

    private final CustomExpansion expansion;

    private final HelpCommand help;
    private final CreateCommand create;
    private final DeleteCommand delete;
    private final ListCommand list;
    private final SetCommand set;
    private final AddCommand add;
    private final SubtractCommand subtract;
    private final MultiplyCommand multiply;
    private final DivideCommand divide;


    public CustomExpansionCommands(CustomExpansion instance) {
        super("cpe",
                "manage custom placeholders",
                "/cpe <player/server> <create/delete/list/set/add/subtract> <identifier> <value> (player)",
                Lists.newArrayList("customplaceholderexpansion", "customplaceholders"));

        // subcommands
        help = new HelpCommand();
        create = new CreateCommand();
        delete = new DeleteCommand();
        list = new ListCommand();
        set = new SetCommand();
        add = new AddCommand();
        subtract = new SubtractCommand();
        multiply = new MultiplyCommand();
        divide = new DivideCommand();

        expansion = instance;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if ((sender instanceof Player) && !sender.hasPermission("customexpansion.admin")) {
            // no perms
            return true;
        }

        if (args.length == 0) {
            // info
            return true;
        }

        String[] subs = (String[]) ArrayUtils.remove(args, 0);

        switch (args[0]) {
            case "help":
                return help.execute(expansion, sender, subs);
            case "create":
                return create.execute(expansion, sender, subs);
            case "delete":
                return delete.execute(expansion, sender, subs);
            case "list":
                return list.execute(expansion, sender, subs);
            case "set":
                return set.execute(expansion, sender, subs);
            case "add":
                return add.execute(expansion, sender, subs);
            case "subtract":
                return subtract.execute(expansion, sender, subs);
            case "multiply":
                return multiply.execute(expansion, sender, subs);
            case "divide":
                return divide.execute(expansion, sender, subs);
        }

        return true;
    }
}
