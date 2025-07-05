///usr/bin/env java --class-path ${APP_DIR:-.}/lib/'*' "$0" "$@"; exit $?

import net.fellbaum.jemoji.Emoji;
import net.fellbaum.jemoji.EmojiManager;

import java.util.List;
import java.util.Optional;

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
        Optional<List<Emoji>> optionalEmoji = EmojiManager.getByAlias(alias);
        var emoji = optionalEmoji.map(Language::getFirstEmoji).orElse("");
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

    private static String getFirstEmoji(List<Emoji> emojis) {
        return emojis.isEmpty() ? "" : emojis.get(0).getEmoji();
    }
}