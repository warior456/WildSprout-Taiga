package net.ugi.wildsprout.config;

public class ConfigHandler {
    public int config_version = 1;

    @Comment("-----------Enable and disable biomes-----------\n")
    public boolean PlainsEnabled = true;
    public boolean SunFlowerPlainsEnabled = true;
    public boolean SnowyPlainsEnabled = true;

    @Comment("-----------Disable features-----------\n\nEnables or disables Snow being able to generate on ice\nIt is not recommended to disable this after world generation\n")
    public boolean SnowOnIceEnabled = true;
    @Comment("Enables or disables snow generating in layers in the snowy plains")
    public boolean LayeredSnowEnabled = true;
}
