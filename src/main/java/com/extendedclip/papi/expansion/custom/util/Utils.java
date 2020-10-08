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
package com.extendedclip.papi.expansion.custom.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class Utils {

    public static void msg(CommandSender s, String... message) {
        Arrays.stream(message).forEach(m -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', m)));
    }

    public static Object getObject(Class<?> type, String str) {
        switch (type.getSimpleName().toLowerCase()) {
            case "string":
                return str;
            case "integer":
                try {
                    return Integer.parseInt(str);
                } catch (Exception e) {
                    return null;
                }
            case "long":
                try {
                    return Long.parseLong(str);
                } catch (Exception e) {
                    return null;
                }
            case "double":
                try {
                    return Double.parseDouble(str);
                } catch (Exception e) {
                    return null;
                }
            case "float":
                try {
                    return Float.parseFloat(str);
                } catch (Exception e) {
                    return null;
                }
            case "boolean":
                if (str.equalsIgnoreCase("true")) {
                    return true;
                } else if (str.equalsIgnoreCase("false")) {
                    return false;
                }
        }
        return null;
    }

    public static Object doMath(Class<?> type, Object origin, String toAdd, String operator) {
        if (operator.equals("+")) {
            switch (type.getSimpleName().toLowerCase()) {
                case "string":
                case "boolean":
                    return null;
                case "integer":
                    try {
                        return (int) origin + Integer.parseInt(toAdd);
                    } catch (Exception e) {
                        return null;
                    }
                case "long":
                    try {
                        return (Long) origin + Long.parseLong(toAdd);
                    } catch (Exception e) {
                        return null;
                    }
                case "double":
                    try {
                        return (Double) origin + Double.parseDouble(toAdd);
                    } catch (Exception e) {
                        return null;
                    }
                case "float":
                    try {
                        return (Float) origin + Float.parseFloat(toAdd);
                    } catch (Exception e) {
                        return null;
                    }
            }
        }
        if (operator.equals("-")) {
            switch (type.getSimpleName().toLowerCase()) {
                case "string":
                case "boolean":
                    return null;
                case "integer":
                    try {
                        return (int) origin - Integer.parseInt(toAdd);
                    } catch (Exception e) {
                        return null;
                    }
                case "long":
                    try {
                        return (Long) origin - Long.parseLong(toAdd);
                    } catch (Exception e) {
                        return null;
                    }
                case "double":
                    try {
                        return (Double) origin - Double.parseDouble(toAdd);
                    } catch (Exception e) {
                        return null;
                    }
                case "float":
                    try {
                        return (Float) origin - Float.parseFloat(toAdd);
                    } catch (Exception e) {
                        return null;
                    }
            }
        }
        if (operator.equals("*")) {
            switch (type.getSimpleName().toLowerCase()) {
                case "string":
                case "boolean":
                    return null;
                case "integer":
                    try {
                        return (int) origin * Integer.parseInt(toAdd);
                    } catch (Exception e) {
                        return null;
                    }
                case "long":
                    try {
                        return (Long) origin * Long.parseLong(toAdd);
                    } catch (Exception e) {
                        return null;
                    }
                case "double":
                    try {
                        return (Double) origin * Double.parseDouble(toAdd);
                    } catch (Exception e) {
                        return null;
                    }
                case "float":
                    try {
                        return (Float) origin * Float.parseFloat(toAdd);
                    } catch (Exception e) {
                        return null;
                    }
            }
        }
        if (operator.equals("/")) {
            switch (type.getSimpleName().toLowerCase()) {
                case "string":
                case "boolean":
                    return null;
                case "integer":
                    try {
                        return (int) origin / Integer.parseInt(toAdd);
                    } catch (Exception e) {
                        return null;
                    }
                case "long":
                    try {
                        return (Long) origin / Long.parseLong(toAdd);
                    } catch (Exception e) {
                        return null;
                    }
                case "double":
                    try {
                        return (Double) origin / Double.parseDouble(toAdd);
                    } catch (Exception e) {
                        return null;
                    }
                case "float":
                    try {
                        return (Float) origin / Float.parseFloat(toAdd);
                    } catch (Exception e) {
                        return null;
                    }
            }
        }
        return null;
    }

    public static Class<?> getSupportedClassType(String type) {
        switch (type.toLowerCase()) {
            case "string":
                return String.class;
            case "integer":
                return Integer.class;
            case "long":
                return Long.class;
            case "double":
                return Double.class;
            case "float":
                return Float.class;
            case "boolean":
                return Boolean.class;
        }
        return null;
    }
}
