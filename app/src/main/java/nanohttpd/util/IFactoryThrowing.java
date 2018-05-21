package nanohttpd.util;

/*
 * #%L
 * NanoHttpd-Core
 * %%
 * Copyright (C) 2012 - 2016 nanohttpd
 * %%

 * #L%
 */

/**
 * Represents a factory that can throw an exception instead of actually creating
 * an object
 * 
 * @author LordFokas
 * @param <T>
 *            The Type of object to create
 * @param <E>
 *            The base Type of exceptions that can be thrown
 */
public interface IFactoryThrowing<T, E extends Throwable> {

    T create() throws E;
}
