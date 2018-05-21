package nanohttpd.protocols.http.threading;

/*
 * #%L
 * NanoHttpd-Core
 * %%
 * Copyright (C) 2012 - 2016 nanohttpd
 * %%

 * #L%
 */

import nanohttpd.protocols.http.ClientHandler;

/**
 * Pluggable strategy for asynchronously executing requests.
 */
public interface IAsyncRunner {

    void closeAll();

    void closed(ClientHandler clientHandler);

    void exec(ClientHandler code);
}
