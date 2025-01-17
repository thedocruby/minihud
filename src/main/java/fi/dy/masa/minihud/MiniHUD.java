package fi.dy.masa.minihud;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import fi.dy.masa.minihud.config.Configs;

public class MiniHUD
{
    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

    public static void logInfo(String msg, Object... args)
    {
        if (Configs.Generic.DEBUG_MESSAGES.getBooleanValue())
        {
            LOGGER.info(msg, args);
        }
    }
}
