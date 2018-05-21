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
 * Represents a simple factory
 * 
 * @author LordFokas
 * @param <T>
 *            The Type of object to create
 */
public interface IFactory<T> {

    T create();
}
