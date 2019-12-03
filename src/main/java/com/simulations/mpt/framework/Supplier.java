package com.simulations.mpt.framework;


/***
 * The clsses of this type supplies a value of type T using the get() method
 * Imp
 *
 * @param <T>
 */
public interface Supplier<T> {
    /***
     * Returns one instance of type T
     *
     * @return
     */
    T get();

    /***
     * Use this if an initialization is required before starting to produce values
     */
    void initialize();

    /***
     * Use this if there is a finalization steps before discarding the supplier instance
     */
    void close();

    /***
     * Returns the size of number of values which are safely returned from this supplier
     * Use this method to limit the number of retrievals via get() method.
     *
     * @return
     */
    int size();
}
