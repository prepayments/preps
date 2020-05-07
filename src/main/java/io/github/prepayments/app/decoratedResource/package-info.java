/**
 * This package contains code copied over from the main stream resources and is intended to
 * <p>
 * carry over much of the typical behaviour of the origin, though exceptions are made for
 * <p>
 * mutative activities which mostly include introspection into uploaded files and asynchronous
 * <p>
 * processing through kafka pipelines.
 * <p>
 * To reduce independence from the original code, the resources are made to implement an interface
 * <p>
 * whose actual implementation is delegated to the original resource initially autowired into the decorator.
 * <p>
 * All the while the original code is to remain completely untouched by changes made here.
 *
 * The resources implemented in this package are doing the same thing as the originals except that the workflows
 *
 * are asynchronous in one way or the other, due to use of messaging programming pattern and also batch processing
 */
package io.github.prepayments.app.decoratedResource;
