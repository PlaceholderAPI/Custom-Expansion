package com.extendedclip.papi.expansion.custom.commands;

import com.extendedclip.papi.expansion.custom.CustomExpansion;
import org.bukkit.command.CommandSender;

public interface Cmd {
  boolean execute(CustomExpansion ex, CommandSender s, String[] args);
  default String getUsage() { return "/cpe"; }
}
