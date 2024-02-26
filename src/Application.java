///usr/bin/env java --class-path ${APP_DIR:-.}/lib/'*' "$0" "$@"; exit $?

import net.fellbaum.jemoji.Emoji;
import net.fellbaum.jemoji.EmojiManager;

import java.util.Optional;

import static java.util.Arrays.stream;

class Main {
    public static void main(String[] args) {
        System.out.println(Language.guess().sayHello());
        System.exit(0);
    }

    private enum Language {
        French("fr"), English("en");

        private final String alias;
        private final String langPrefix;

        Language(String alias) {
            this.alias = alias;
            this.langPrefix = alias;
        }

        public static Language guess() {
            var langEnvVar = System.getenv("LANG");
            var langPrefix = langEnvVar.substring(0, langEnvVar.indexOf('_'));
            return byLangPrefix(langPrefix).orElseThrow(() -> new IllegalArgumentException("Unexpected Language: " + langPrefix));
        }

        public String sayHello() {
            Optional<Emoji> optionalEmoji = EmojiManager.getByAlias(alias);
            String emoji = optionalEmoji.isPresent() ? " " + optionalEmoji.get().getEmoji() : "";
            return switch (this) {
                case French -> "Bonjour " + emoji;
                case English -> "Hello " + emoji;
            };
        }

        private static Optional<Language> byLangPrefix(String langPrefix) {
            return stream(Language.values())
                    .filter(language -> language.langPrefix.equals(langPrefix))
                    .findAny();
        }
    }
}
