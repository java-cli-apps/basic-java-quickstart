///usr/bin/env java --class-path ${APP_DIR:-.}/lib/'*' "$0" "$@"; exit $?

import net.fellbaum.jemoji.EmojiManager;

import java.util.Optional;

class Main {
    public static void main(String[] args) {
        System.out.println(Language.guess().sayHello());
        System.exit(0);
    }
}

enum Language {
    French("fr"), English("en");

    private final String alias;
    private final String prefix;

    Language(String alias) {
        this.alias = alias;
        this.prefix = alias;
    }

    static Language guess() {
        var langEnvVar = System.getenv("LANG");
        var langPrefix = langEnvVar.substring(0, langEnvVar.indexOf('_'));
        return byPrefix(langPrefix).orElseThrow(() -> new IllegalArgumentException("Unexpected Language: " + langPrefix));
    }

    String sayHello() {
        String emoji = getEmojiByAlias(alias);
        return switch (this) {
            case French -> "Bonjour " + emoji;
            case English -> "Hello " + emoji;
        };
    }

    private static Optional<Language> byPrefix(String prefix) {
        if (French.prefix.equals(prefix)) {
            return Optional.of(French);
        } else if (English.prefix.equals(prefix)) {
            return Optional.of(English);
        } else {
            return Optional.empty();
        }
    }

    private static String getEmojiByAlias(String alias) {
        return EmojiManager.getByAlias(alias).map(value -> " " + value.getEmoji()).orElse("");
    }
}
