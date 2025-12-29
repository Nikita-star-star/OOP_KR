package com.example.jkhspring.searchable;

import java.util.HashSet;
import java.util.Set;

/**
 * Интерфейс для поиска по "совпадениям" (как в курсовой друга):
 * используется триграммный скоринг, чтобы находить неточные совпадения.
 */
public interface Searchable {

    final class TrigramSearch {
        private TrigramSearch() {}

        public static Set<String> trigrams(String text) {
            Set<String> result = new HashSet<>();
            if (text == null) return result;

            text = text.toLowerCase();
            for (int i = 0; i <= text.length() - 3; i++) {
                result.add(text.substring(i, i + 3));
            }
            return result;
        }

        public static double containmentScore(String small, String big) {
            Set<String> smallTrigrams = trigrams(small);
            Set<String> bigTrigrams = trigrams(big);

            if (smallTrigrams.isEmpty()) return 1.0;

            int matches = 0;
            for (String tri : smallTrigrams) {
                if (bigTrigrams.contains(tri)) {
                    matches++;
                }
            }
            return (double) matches / smallTrigrams.size();
        }
    }

    default boolean matches(String keyword, double identity) {
        String small = (keyword == null ? "" : keyword)
                .toLowerCase()
                .replaceAll("\\s+", " ")
                .trim();

        String big = this.toString()
                .toLowerCase()
                .replaceAll("\\s+", " ")
                .trim();

        return TrigramSearch.containmentScore(small, big) >= identity;
    }

    default boolean matches(String keyword) {
        return matches(keyword, 0.6);
    }
}
