package io.github.prepayments.app.token;

/**
 * Generates tags for tag capable objects
 *
 * @param <T> Type of tag
 */
public interface TagProvider<T> {

    /**
     * Creates a Tag for a given tag-capable object
     *
     * @param tagCapable
     * @return
     */
    Tag<T> tag(TagCapable<T> tagCapable);
}
