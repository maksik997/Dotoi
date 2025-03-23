package pl.magzik.dotoi.manager;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Manager class responsible for handling translations.
 * This class provides two main features:
 * <ul>
 *     <li>Translates a given key ({@link TranslationManager#translate(String)}),</li>
 *     <li>Finds the corresponding key for a given translation ({@link TranslationManager#findKey(String)}).</li>
 * </ul>
 * An instance of this class is stored as a Singleton using the InstanceHolder pattern.
 * To obtain the instance, use the {@link TranslationManager#getInstance()} method.
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 * */
public class TranslationManager {

    private static final Logger log = LoggerFactory.getLogger(TranslationManager.class);

    private static final class InstanceHolder {
        private static final TranslationManager instance = new TranslationManager();
    }

    /**
     * Returns the singleton instance of {@link TranslationManager}.
     * This instance is managed using the InstanceHolder pattern.
     *
     * @return The singleton {@link TranslationManager} instance.
     * */
    public static TranslationManager getInstance() {
        return InstanceHolder.instance;
    }

    private static final String BUNDLE_NAME = "i18n.localization";

    private ResourceBundle bundle;

    private TranslationManager() {
        log.debug("Initializing translation service...");
        setLocale(Locale.getDefault()); ///< Default initialization.
    }

    /**
     * Sets a new {@link ResourceBundle} to be used for future translations.
     *
     * @param locale The {@link Locale} used to retrieve the appropriate resource bundle.
     * */
    public void setLocale(@NotNull Locale locale) {
        log.info("Changing locale to: {}", locale.getDisplayLanguage());
        bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }

    /**
     * @return The {@link ResourceBundle} associated with this class
     */
    public @NotNull ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * Translates the given {@link String} key into its corresponding value from the {@link ResourceBundle}.
     * <p>
     * If the key is not found in the bundle, the method returns the key itself enclosed in double square brackets
     * and logs a warning message.
     *
     * @param key The {@link String} key to translate.
     * @return The translated {@link String}, or the key itself if not found.
     * */
    public @NotNull String translate(@NotNull String key) {
        if (bundle.containsKey(key)) return bundle.getString(key);

        log.warn("Missing translation for key: {}", key);
        return String.format("[[%s]]", key);
    }

    /**
     * Finds the key corresponding to the given {@link String} value in the {@link ResourceBundle}.
     * <p>
     * If no key is found for the specified value, the method returns the value itself enclosed in double square brackets
     * and logs a warning message.
     *
     * @param value The {@link String} value for which to find the corresponding key.
     * @return The {@link String} key for the given value, or the value itself if not found.
     * */
    public @NotNull String findKey(@NotNull String value) {
        Optional<String> key = bundle.keySet()
                                     .stream()
                                     .map(bundle::getString)
                                     .filter(v -> v.equals(value))
                                     .findAny();
        if (key.isPresent()) return key.get();

        log.warn("Missing key for translation: {}", value);
        return String.format("[[%s]]", value);
    }
}
