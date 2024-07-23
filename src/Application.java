///usr/bin/env java --class-path ${APP_DIR}/lib/'*' "$0" "$@"; exit $?

import net.fellbaum.jemoji.Emoji;
import net.fellbaum.jemoji.EmojiManager;

class Main {
    public static void main(String... args) {
        var language = Language.guess();
        System.out.println(language.sayHello());
    }
}

enum Language {
    French("fr"),
    English("en");

    private final String alias;
    private final String prefix;

    Language(String alias) {
        this.alias = alias;
        this.prefix = alias;
    }

    static Language guess() {
        var langEnvVar = System.getenv("LANG");
        var langPrefix = langEnvVar.substring(0, langEnvVar.indexOf('_'));
        return byPrefix(langPrefix);
    }

    String sayHello() {
        var optionalEmoji = EmojiManager.getByAlias(alias);
        var emoji = optionalEmoji.map(Emoji::getEmoji).orElse("");
        if (this == French) {
            return "Bonjour " + emoji;
        } else if (this == English) {
            return "Hello " + emoji;
        } else {
            throw new IllegalArgumentException("Unexpected Language: " + alias);
        }
    }

    private static Language byPrefix(String prefix) {
        if (French.prefix.equals(prefix)) {
            return French;
        } else if (English.prefix.equals(prefix)) {
            return English;
        } else {
            throw new IllegalArgumentException("Unexpected Language: " + prefix);
        }
    }
}
