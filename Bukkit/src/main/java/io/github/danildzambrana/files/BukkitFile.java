/*
 * Copyright (c) 2020. This file is subject to the terms and conditions defined in file 'LICENSE.md', which is part of this source code package.
 */

package io.github.danildzambrana.files;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class BukkitFile extends CommonFile<FileConfiguration, Plugin> {
    private File file;
    private final String nameOfInFile;
    private final String nameOfOutFile;

    /**
     * Create new file manager to bukkit with blank file.
     *
     * @param nameOfOutFile the out file.
     * @param plugin the plugin main.
     */
    public BukkitFile(Plugin plugin, String nameOfOutFile){
        super(plugin);
        this.nameOfOutFile = nameOfOutFile;
        this.nameOfInFile = "";
    }

    /**
     * Create new file manager to bukkit with base file.
     * @param nameOfOutFile the out file.
     * @param plugin the plugin main.
     * @param nameOfInFile the base file.
     */
    public BukkitFile(String nameOfOutFile, Plugin plugin, String nameOfInFile){
        super(plugin);
        this.nameOfOutFile = nameOfOutFile;
        this.nameOfInFile = nameOfInFile;
    }

    @Override
    public FileConfiguration getFile() {
        if (configuration == null){
            reloadFile();
        }

        return configuration;
    }

    @Override
    public void reloadFile() {
        if (configuration == null){
            file = new File(plugin.getDataFolder(), nameOfOutFile);
        }

        configuration = YamlConfiguration.loadConfiguration(file);

        if (nameOfInFile != null) {
            Reader defConfigStream = new InputStreamReader(Objects.requireNonNull(plugin.getResource(nameOfInFile)),
                    StandardCharsets.UTF_8);
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            configuration.setDefaults(defConfig);
        }

    }


    @Override
    public void saveFile(){
        try {
            configuration.save(file);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void load(boolean silent, String nick){
        file = new File(plugin.getDataFolder(), nameOfOutFile);

        if (!file.exists()){
            if (!silent){
                plugin.getLogger().info(ChatColor.RED + nick + ChatColor.GRAY + " is not founded, creating archive...");
            }
            this.configuration.options().copyDefaults(true);
            saveFile();
        }
    }

    @Override
    public void load(boolean silent){
        load(true, "");
    }

    @Override
    public @NotNull String getStripedString(String path) {
        return ChatColor.stripColor(getColouredString(path));
    }

    @Override
    public @NotNull String getColouredString(String path) {
        return ChatColor.translateAlternateColorCodes('&', getString(path));
    }

    @Override
    public @NotNull String getString(String path) {
        return getFile().contains(path) ? getFile().getString(path) : "";
    }


}

