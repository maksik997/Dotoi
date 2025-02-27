package pl.magzik.dotoi.util;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import org.jetbrains.annotations.NotNull;

/**
 * A utility class for converting Markdown-formatted text into HTML.
 * <p>
 * This class utilizes the <a href="https://github.com/vsch/flexmark-java">Flexmark</a> library to parse
 * and render Markdown content into valid HTML format. It provides a single static method to facilitate
 * this conversion.
 * </p>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 *     String markdown = "# Hello World\nThis is **bold** text.";
 *     String html = MarkdownUtils.convertToHtml(markdown);
 *     System.out.println(html);
 * }</pre>
 *
 * @see Parser
 * @see HtmlRenderer
 * @see Node
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
public class MarkdownUtils {

    /**
     * Converts a given Markdown-formatted {@link String} into an HTML-formatted {@link String}.
     * <p>
     * This method uses the flexmark parser to analyze the Markdown syntax and produce a corresponding
     * HTML representation. It ensures that all valid Markdown constructs are properly translated into
     * their HTML equivalents.
     * </p>
     *
     * @param markdown The Markdown document as a {@link String} to be converted to HTML.
     * @return A {@link String} containing the HTML representation of the input Markdown.
     */
    public static @NotNull String convertToHtml(@NotNull String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        return HtmlRenderer.builder().build().render(document);
    }
}
