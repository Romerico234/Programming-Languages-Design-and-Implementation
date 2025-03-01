/*
COURSE: COSC455-002
Assignment: Program 0
Name: David, Romerico
*/

//  ************** REQUIRES Java 21 OR ABOVE! (https://adoptium.net/) ************** //
package compiler;

import java.util.Arrays;
import java.util.List;

/*
 * Remember this is part of a "fake" tokenizer, that when handed a string, it simply resolves to a
 * TOKEN object matching that string. All the Tokens/Terminals Used by the parser. The purpose of
 * the enum type here is to eliminate the need for direct character comparisons.
 *
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! IMPORTANT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * ----------------------------------------------------------------------------- IN *MOST* REAL
 * CASES, THERE WILL BE ONLY *ONE* LEXEME PER TOKEN! More are allowed here because this is a simple
 * English sentence parser and there are words than can be NOUNS, VERBS, etc.
 *
 * The fact that several lexemes exist per token in this example is because this is to parse simple
 * In English sentences, most of the token types have many words (lexemes) that could fit. *** This
 * is generally NOT the case in most programming languages!!! ***
 */

public enum TokenSet {
    // Adjusted to reflect the new grammar (includes "an", punctuation, conjunction, etc.)

    ARTICLE("a", "an", "the"),
    NOUN("dog", "cat", "rat", "fox", "tree", "house"),
    VERB("jumps", "chases", "climbs"),
    ADJECTIVE("fast", "slow", "lazy", "tall"),
    ADVERB("quickly", "slowly"),   // changed to match new grammar
    PREPOSITION("around", "up", "over", "under"),
    COMMA(","),

    // New tokens for conjunction and punctuation:
    CONJUNCTION("and", "or"),
    PUNCTUATION(".", "!"),

    // The "end of input" marker.
    $$, // End of file

    // Misc. placeholders that remain:
    UNIDENTIFIED_TOKEN, // Possibly an ID, in a real language scenario
    NUMBER; // A sequence of digits.

    /**
     * A list of all lexemes for each token.
     */
    private final List<String> lexemeList;

    /**
     * Constructor for the TokenSet enum.
     * Accepts a variable number of lexemes for each token. However,
     * in MOST programming languages, there is only one lexeme per token.
     *
     * @param tokenStrings The lexemes for the token.
     */
    TokenSet(final String... tokenStrings) {
        // lowercase all lexemes and collect them into an ArrayList.
        this.lexemeList = Arrays
                .stream(tokenStrings)
                .map(String::toLowerCase)
                .toList();
    }

    /*
     * Get a TokenSet object from the Lexeme string.
     *
     * @param string The String (lexeme) to convert to a compiler.TokenSet
     * @return A compiler.TokenSet object based on the input String (lexeme)
     */
    static TokenSet getTokenFromLexeme(final String string) {
        // Just to be safe…
        final var lexeme = string.trim().toLowerCase();

        // An empty string/lexeme should mean no more tokens to process.
        // Return the "end of input" marker if the string is empty.
        if (lexeme.isEmpty()) {
            return $$;
        }

        // Return the NUMBER token if the string represents a number.
        if (lexeme.matches(LexicalAnalyzer.NUMBER_REGEX)) {
            return NUMBER;
        }

        // Search through ALL lexemes looking for a match with early bailout.
        for (var token : TokenSet.values()) {
            if (token.lexemeList.contains(lexeme)) {
                return token;
            }
        }

        // If no match was found, treat as an unidentified token (like an ID).
        return UNIDENTIFIED_TOKEN;
    }
}
