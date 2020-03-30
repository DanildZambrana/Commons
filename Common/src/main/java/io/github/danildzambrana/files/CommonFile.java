package io.github.danildzambrana.files;

import org.jetbrains.annotations.NotNull;

public abstract class CommonFile<C, P>{

    protected P plugin;
    protected C configuration;

    protected CommonFile(P plugin){
        this.plugin = plugin;
    }

    /**
     * Get the file {@link C}
     *
     * @return the file object.
     */
    public abstract C getFile();

    /**
     * Reload the config file.
     */
    public abstract void reloadFile();

    /**
     * Save changes in the file.
     */
    public abstract void saveFile();

    /**
     * Create file in the directory if not exist.
     * @param silent set false if you want to show the message.
     * @param archive the nickname of file.
     */
    public abstract void load(boolean silent, String archive);

    /**
     * Create the file without nickname if not exist in the directory.
     *
     * @param silent set false if you want to show the message.
     */
    public abstract void load(boolean silent);

    /**
     * Strip all colors in the provided String
     * @param path the path of the String in the config to strip colors
     * @return the value of path without colors
     */
    @NotNull
    public abstract String getStripedString(String path);

    /**
     * Add color to the string provided.
     * @param path the path of the String in the config to add colors
     * @return the value of the path with colors
     */
    @NotNull
    public abstract String getColouredString(String path);

    /**
     * Get the value of the path provided
     * @param path the path to obtain the String
     * @return the value of path in the config
     */
    @NotNull
    public abstract String getString(String path);
}
